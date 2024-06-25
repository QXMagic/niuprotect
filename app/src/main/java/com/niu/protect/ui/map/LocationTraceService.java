package com.niu.protect.ui.map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.fence.FenceAlarmPushInfo;
import com.baidu.trace.api.fence.MonitoredAction;
import com.baidu.trace.api.track.LatestPoint;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.StatusCodes;
import com.baidu.trace.model.TraceLocation;
import com.niu.protect.BabyApplication;
import com.niu.protect.R;
import com.niu.protect.core.Constants;
import com.niu.protect.manager.UserInfoManager;
import com.niu.protect.map.maputil.CommonUtil;
import com.niu.protect.model.CurrentLocation;
import com.niu.protect.map.receiver.TrackReceiver;
import com.niu.protect.model.UploadLocationInfo;
import com.niu.protect.model.UserInfo;
import com.niu.protect.repository.LocationInfoRepository;
import com.niu.protect.tools.ILog;

import java.util.ArrayList;
import java.util.List;
public class LocationTraceService extends Service {
    private static final String TAG = "LocationTraceService";
    Context context;
    List<UploadLocationInfo> uploadLocationInfos;
    private BabyApplication trackApp = null;
    private NotificationManager notificationManager = null;
    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock = null;
    private TrackReceiver trackReceiver = null;
    private OnTraceListener traceListener = null;
    private OnTrackListener trackListener = null;
    private OnEntityListener entityListener = null;
    private RealTimeHandler realTimeHandler = new RealTimeHandler();
    private RealTimeLocRunnable realTimeLocRunnable = null;
    private boolean isRealTimeRunning = true;
    private int notifyId = 0;
    private List<LatLng> realTrackList = new ArrayList();
    public int packInterval = 30;
    private BroadcastReceiver mHomeAndLockReceiver = new BroadcastReceiver() {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        final String MESSAGE = " 根据相关法律法规规定，切换到后台后，若无必要可不必收集用户信息。";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
            }
        }
    };

    static int Fun$508(LocationTraceService x0) {
        int i = x0.notifyId;
        x0.notifyId = i + 1;
        return i;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ILog.d(TAG, "onCreate----");
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ILog.d(TAG, "onStartCommand----");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ILog.d(TAG, "onDestroy----");
        stopRealTimeLoc();
        unregisterReceiver(this.mHomeAndLockReceiver);
    }

    private void init() {
        this.context = BabyApplication.getInstance();
        this.uploadLocationInfos = new ArrayList();
        initListener();
        this.trackApp = (BabyApplication) this.context.getApplicationContext();
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this.context);
        if (userInfo != null) {
            this.trackApp.setEntityName(userInfo.getId());
        } else {
            BabyApplication babyApplication = this.trackApp;
            babyApplication.setEntityName(System.currentTimeMillis() + "");
        }
        this.powerManager = (PowerManager) this.trackApp.getSystemService(Context.POWER_SERVICE);
        this.notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        registerReceiver(this.mHomeAndLockReceiver, intentFilter);
        startLocation();
    }

    void startLocation() {
        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23) {
            isNeedRequestPermissions(permissions);
        }
        startTraceService();
    }

    void startTraceService() {
        if (1 == 0) {
            ILog.d(TAG, "需要权限");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.trackApp.mClient == null);
        sb.append("");
        ILog.d("mClient", sb.toString());
        if (this.trackApp.mClient == null) {
            ILog.d(TAG, "trackApp.mClient == null");
            return;
        }
        if (this.trackApp.isTraceStarted) {
            this.trackApp.mClient.stopTrace(this.trackApp.mTrace, this.traceListener);
            stopRealTimeLoc();
        }
        this.trackApp.mClient.startTrace(this.trackApp.mTrace, this.traceListener);
        if (30 == this.packInterval) {
            stopRealTimeLoc();
            ILog.d(TAG, "startRealTimeLoc------");
            startRealTimeLoc(this.packInterval);
        }
    }

    void stopTraceService() {
        if (this.trackApp.isTraceStarted) {
            this.trackApp.mClient.stopTrace(this.trackApp.mTrace, this.traceListener);
            stopRealTimeLoc();
        }
    }

    void startGather() {
        if (this.trackApp.mClient == null) {
            ILog.d(TAG, "trackApp.mClient == null");
        } else if (!this.trackApp.isGatherStarted) {
            this.trackApp.mClient.startGather(this.traceListener);
        }
    }

    void stopGather() {
        if (this.trackApp.mClient == null) {
            ILog.d(TAG, "trackApp.mClient == null");
        } else if (this.trackApp.isGatherStarted) {
            this.trackApp.mClient.stopGather(this.traceListener);
        }
    }

    private boolean isNeedRequestPermissions(List<String> permissions) {
        addPermission(permissions, "android.permission.ACCESS_FINE_LOCATION");
        addPermission(permissions, "android.permission.WRITE_EXTERNAL_STORAGE");
        if (Build.VERSION.SDK_INT >= 28) {
            addPermission(permissions, "android.permission.FOREGROUND_SERVICE");
        }
        return permissions.size() > 0;
    }

    private void addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }

    private void initListener() {
        this.trackListener = new OnTrackListener() {
            @Override
            public void onLatestPointCallback(LatestPointResponse response) {
                LatestPoint point;
                LatLng currentLatLng;
                String floor;
                if (response.getStatus() == 0 && (point = response.getLatestPoint()) != null && !CommonUtil.isZeroPoint(point.getLocation().getLatitude(), point.getLocation().getLongitude()) && (currentLatLng = MapTools.convertTrace2Map(point.getLocation())) != null) {
                    realTrackList.add(currentLatLng);
                    if (realTrackList != null) {
                        realTrackList.size();
                    }
                    CurrentLocation.locTime = point.getLocTime();
                    CurrentLocation.latitude = currentLatLng.latitude;
                    CurrentLocation.longitude = currentLatLng.longitude;
                    String locationMode = point.getLocateMode();
                    double speed = point.getSpeed();
                    String floor2 = point.getFloor();
                    ILog.d("--location----", "locationMode:" + locationMode);
                    ILog.d("--location----", "speed:" + speed);
                    ILog.d("--location----", "floor:" + floor2);
                    if (TextUtils.isEmpty(floor2)) {
                        floor = Constants.MSG_DB_READY_REPORT;
                    } else {
                        floor = "1";
                    }
                    uploadCurrentLocation(point.getLocTime(), currentLatLng, speed, floor, locationMode);
                }
            }
        };
        this.entityListener = new OnEntityListener() {
            @Override
            public void onReceiveLocation(TraceLocation location) {
                String floor;
                if (location == null || location.getStatus() != 0 || CommonUtil.isZeroPoint(location.getLatitude(), location.getLongitude())) {
                    return;
                }
                LatLng currentLatLng = MapTools.convertTraceLocation2Map(location);
                String locationMode = location.getLocType().toString();
                double speed = location.getSpeed();
                String floor2 = location.getFloor();
                ILog.d("--entityListener----", "locationMode:" + locationMode);
                ILog.d("--entityListener----", "speed:" + speed);
                ILog.d("--entityListener----", "floor:" + floor2);
                if (TextUtils.isEmpty(floor2)) {
                    floor = Constants.MSG_DB_READY_REPORT;
                } else {
                    floor = "1";
                }
                uploadCurrentLocation(CommonUtil.toTimeStamp(location.getTime()), currentLatLng, speed, floor, locationMode);
            }
        };
        this.traceListener = new OnTraceListener() {
            @Override
            public void onBindServiceCallback(int errorNo, String message) {
            }

            @Override
            public void onStartTraceCallback(int errorNo, String message) {
                if (errorNo == 0 || 10003 <= errorNo) {
                    trackApp.isTraceStarted = true;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.putBoolean("is_trace_started", true);
                    editor.apply();
                    registerReceiver();
                    ILog.d(LocationTraceService.TAG, "开启轨迹服务成功");
                    startGather();
                }
            }

            @Override
            public void onStopTraceCallback(int errorNo, String message) {
                if (errorNo == 0 || 11004 == errorNo) {
                    trackApp.isTraceStarted = false;
                    trackApp.isGatherStarted = false;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.remove("is_trace_started");
                    editor.remove("is_gather_started");
                    editor.apply();
                    unregisterPowerReceiver();
                }
            }

            @Override
            public void onStartGatherCallback(int errorNo, String message) {
                if (errorNo == 0 || 12003 == errorNo) {
                    trackApp.isGatherStarted = true;
                    ILog.d(LocationTraceService.TAG, "开启轨迹跟踪成功");
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.putBoolean("is_gather_started", true);
                    editor.apply();
                }
            }

            @Override
            public void onStopGatherCallback(int errorNo, String message) {
                if (errorNo == 0 || 13003 == errorNo) {
                    trackApp.isGatherStarted = false;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.remove("is_gather_started");
                    editor.apply();
                }
            }

            @Override
            public void onPushCallback(byte messageType, PushMessage pushMessage) {
                FenceAlarmPushInfo alarmPushInfo;
                if (messageType < 3 || messageType > 4 || (alarmPushInfo = pushMessage.getFenceAlarmPushInfo()) == null) {
                    return;
                }
                StringBuffer alarmInfo = new StringBuffer();
                alarmInfo.append("您于");
                alarmInfo.append(CommonUtil.getHMS(alarmPushInfo.getCurrentPoint().getLocTime() * 1000));
                alarmInfo.append(alarmPushInfo.getMonitoredAction() == MonitoredAction.enter ? "进入" : "离开");
                alarmInfo.append(messageType == 3 ? "云端" : "本地");
                alarmInfo.append("围栏：");
                alarmInfo.append(alarmPushInfo.getFenceName());
                if (Build.VERSION.SDK_INT > 16) {
                    Notification notification = new Notification.Builder(trackApp).setContentTitle("定位中..").setContentText(alarmInfo.toString()).setSmallIcon(R.mipmap.ic_launcher).setWhen(System.currentTimeMillis()).build();
                    notificationManager.notify(LocationTraceService.Fun$508(LocationTraceService.this), notification);
                }
            }

            @Override
            public void onInitBOSCallback(int errorNo, String message) {
            }

            @Override
            public void onTraceDataUploadCallBack(int i, String s, int i1, int i2) {

            }
        };
    }

    public void uploadCurrentLocation(long time, LatLng currentLatLng, double speed, String indoor, String locationModel) {
        if (currentLatLng == null) {
            return;
        }
        CurrentLocation.latitude = currentLatLng.latitude;
        CurrentLocation.longitude = currentLatLng.longitude;
        ILog.d("location---latitude-----", CurrentLocation.latitude + "");
        ILog.d("location--longitude------", CurrentLocation.longitude + "");
        if (speed < 100.0d) {
            UploadLocationInfo locationInfo = new UploadLocationInfo();
            locationInfo.setLongitude(currentLatLng.longitude + "");
            locationInfo.setLatitude(currentLatLng.latitude + "");
            locationInfo.setIndoor(indoor);
            locationInfo.setLocationModel(locationModel);
            locationInfo.setSpeed(speed);
            locationInfo.setTimeStamp(time);
            this.uploadLocationInfos.add(locationInfo);
        }
        if (this.uploadLocationInfos.size() >= 6) {
            stopTraceService();
            stopGather();
            LocationInfoRepository.getInstance().uploadLoacationInfo(this.uploadLocationInfos, new LocationInfoRepository.UploadCallBack() {
                @Override
                public void uploadFinish() {
                    uploadLocationInfos.clear();
                    stopSelf();
                }
            });
        }
    }

    public void registerReceiver() {
        if (this.trackApp.isRegisterReceiver) {
            return;
        }
        if (this.trackReceiver == null) {
            this.trackReceiver = new TrackReceiver(this.wakeLock);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.USER_PRESENT");
        filter.addAction(StatusCodes.GPS_STATUS_ACTION);
        this.trackApp.registerReceiver(this.trackReceiver, filter);
        this.trackApp.isRegisterReceiver = true;
    }

    public void unregisterPowerReceiver() {
        if (!this.trackApp.isRegisterReceiver) {
            return;
        }
        TrackReceiver trackReceiver = this.trackReceiver;
        if (trackReceiver != null) {
            this.trackApp.unregisterReceiver(trackReceiver);
        }
        this.trackApp.isRegisterReceiver = false;
    }

    public class RealTimeLocRunnable implements Runnable {
        private int interval;

        public RealTimeLocRunnable(int interval) {
            this.interval = 0;
            this.interval = interval;
        }

        @Override
        public void run() {
            if (isRealTimeRunning) {
                trackApp.getCurrentLocation(entityListener, trackListener);
                realTimeHandler.postDelayed(this, this.interval * 1000);
            }
        }
    }

    public void startRealTimeLoc(int interval) {
        this.isRealTimeRunning = true;
        RealTimeLocRunnable realTimeLocRunnable = new RealTimeLocRunnable(interval);
        this.realTimeLocRunnable = realTimeLocRunnable;
        this.realTimeHandler.post(realTimeLocRunnable);
    }

    public void stopRealTimeLoc() {
        RealTimeLocRunnable realTimeLocRunnable;
        if (this.trackApp.mClient == null) {
            return;
        }
        this.isRealTimeRunning = false;
        RealTimeHandler realTimeHandler = this.realTimeHandler;
        if (realTimeHandler != null && (realTimeLocRunnable = this.realTimeLocRunnable) != null) {
            realTimeHandler.removeCallbacks(realTimeLocRunnable);
        }
        this.trackApp.mClient.stopRealTimeLoc();
    }

    public static class RealTimeHandler extends Handler {
        RealTimeHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
