package com.niu.protect.ui.map;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.fence.FenceAlarmPushInfo;
import com.baidu.trace.api.fence.MonitoredAction;
import com.baidu.trace.api.track.LatestPoint;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.LocationMode;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.StatusCodes;
import com.baidu.trace.model.TraceLocation;
import com.hjq.permissions.Permission;
import com.niu.protect.BabyApplication;
import com.niu.protect.map.maputil.BitmapUtil;
import com.niu.protect.map.maputil.CommonUtil;
import com.niu.protect.map.maputil.MapUtil;
import com.niu.protect.map.maputil.ViewUtil;
import com.niu.protect.model.CurrentLocation;
import com.niu.protect.map.receiver.TrackReceiver;
import com.niu.protect.tools.ILog;
import com.niu.protect.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import com.niu.protect.R;
public class TracingActivity extends BaseActivity implements View.OnClickListener {
    private BabyApplication trackApp = null;
    private ViewUtil viewUtil = null;
    private Button traceBtn = null;
    private Button gatherBtn = null;
    private NotificationManager notificationManager = null;
    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock = null;
    private TrackReceiver trackReceiver = null;
    private MapUtil mapUtil = null;
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
            if (action.equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
                String reason = intent.getStringExtra("reason");
                if (TextUtils.equals(reason, "homekey")) {
                    if (trackApp.isGatherStarted) {
                        viewUtil.showToast(TracingActivity.this, " 根据相关法律法规规定，切换到后台后，若无必要可不必收集用户信息。");
                    }
                } else if (reason.equals("recentapps") && trackApp.isGatherStarted) {
                    viewUtil.showToast(TracingActivity.this, " 根据相关法律法规规定，切换到后台后，若无必要可不必收集用户信息。");
                }
            }
        }
    };

    static int Fun$1208(TracingActivity x0) {
        int i = x0.notifyId;
        x0.notifyId = i + 1;
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        setTitle("轨迹");
        BitmapUtil.init();
        init();
        setOnClickListener(this);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_top);
        LinearLayout optionsButton = (LinearLayout) layout.findViewById(R.id.btn_activity_options);
        optionsButton.setOnClickListener(listener);
    }

    private void init() {
        initListener();
        this.trackApp = (BabyApplication) getApplicationContext();
        this.viewUtil = new ViewUtil();
        MapUtil mapUtil = MapUtil.getInstance();
        this.mapUtil = mapUtil;
        mapUtil.init((MapView) findViewById(R.id.tracing_mapView));
        this.mapUtil.setCenter(this.trackApp);
        startRealTimeLoc(10);
        this.powerManager = (PowerManager) this.trackApp.getSystemService(Context.POWER_SERVICE);
        this.traceBtn = (Button) findViewById(R.id.btn_trace);
        this.gatherBtn = (Button) findViewById(R.id.btn_gather);
        Button guiji = (Button) findViewById(R.id.btn_guiji);
        this.traceBtn.setOnClickListener(this);
        this.gatherBtn.setOnClickListener(this);
        guiji.setOnClickListener(this);
        setTraceBtnStyle();
        setGatherBtnStyle();
        this.notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        registerReceiver(this.mHomeAndLockReceiver, intentFilter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_gather) {
            if (this.trackApp.mClient == null) {
                Toast.makeText(this, getResources().getString(R.string.privacy_policy_desc), Toast.LENGTH_LONG).show();
            } else if (this.trackApp.isGatherStarted) {
                this.trackApp.mClient.stopGather(this.traceListener);
            } else {
                this.trackApp.mClient.startGather(this.traceListener);
            }
        } else if (id == R.id.btn_guiji) {
            Intent intent = new Intent(this, TrackQueryActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_trace) {
            if (1 == 0) {
                Toast.makeText(this, "需要同意隐私条款后才可以采集", Toast.LENGTH_LONG).show();
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.trackApp.mClient == null);
            sb.append("");
            ILog.d("mClient", sb.toString());
            if (this.trackApp.mClient == null) {
                Toast.makeText(this, getResources().getString(R.string.privacy_policy_desc), Toast.LENGTH_LONG).show();
            } else if (this.trackApp.isTraceStarted) {
                this.trackApp.mClient.stopTrace(this.trackApp.mTrace, this.traceListener);
                stopRealTimeLoc();
            } else {
                this.trackApp.mClient.startTrace(this.trackApp.mTrace, this.traceListener);
                if (30 != this.packInterval) {
                    stopRealTimeLoc();
                    startRealTimeLoc(this.packInterval);
                }
            }
        }
    }

    public void setTraceBtnStyle() {
        boolean isTraceStarted = this.trackApp.trackConf.getBoolean("is_trace_started", false);
        if (isTraceStarted) {
            this.traceBtn.setText(R.string.stop_trace);
            this.traceBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            if (Build.VERSION.SDK_INT >= 16) {
                this.traceBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.bg_btn_sure, null));
                return;
            } else {
                this.traceBtn.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.mipmap.bg_btn_sure, null));
                return;
            }
        }
        this.traceBtn.setText(R.string.start_trace);
        this.traceBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.red, null));
        if (Build.VERSION.SDK_INT >= 16) {
            this.traceBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.bg_btn_cancel, null));
        } else {
            this.traceBtn.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.mipmap.bg_btn_cancel, null));
        }
    }

    public void setGatherBtnStyle() {
        boolean isGatherStarted = this.trackApp.trackConf.getBoolean("is_gather_started", false);
        if (isGatherStarted) {
            this.gatherBtn.setText(R.string.stop_gather);
            this.gatherBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            if (Build.VERSION.SDK_INT >= 16) {
                this.gatherBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.bg_btn_sure, null));
                return;
            } else {
                this.gatherBtn.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.mipmap.bg_btn_sure, null));
                return;
            }
        }
        this.gatherBtn.setText(R.string.start_gather);
        this.gatherBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.red, null));
        if (Build.VERSION.SDK_INT >= 16) {
            this.gatherBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.bg_btn_cancel, null));
        } else {
            this.gatherBtn.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.mipmap.bg_btn_cancel, null));
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || this.trackApp.mClient == null) {
            return;
        }
        if (data.hasExtra("locationMode")) {
            LocationMode locationMode = LocationMode.valueOf(data.getStringExtra("locationMode"));
            this.trackApp.mClient.setLocationMode(locationMode);
        }
        if (data.hasExtra("isNeedObjectStorage")) {
            boolean isNeedObjectStorage = data.getBooleanExtra("isNeedObjectStorage", false);
            this.trackApp.mTrace.setNeedObjectStorage(isNeedObjectStorage);
        }
        if (data.hasExtra("gatherInterval") || data.hasExtra("packInterval")) {
            int gatherInterval = data.getIntExtra("gatherInterval", 5);
            int packInterval = data.getIntExtra("packInterval", 30);
            this.packInterval = packInterval;
            this.trackApp.mClient.setInterval(gatherInterval, packInterval);
        }
    }

    private void initListener() {
        this.trackListener = new OnTrackListener() {
            @Override
            public void onLatestPointCallback(LatestPointResponse response) {
                LatestPoint point;
                if (response.getStatus() == 0 && (point = response.getLatestPoint()) != null && !CommonUtil.isZeroPoint(point.getLocation().getLatitude(), point.getLocation().getLongitude())) {
                    MapUtil unused = mapUtil;
                    LatLng currentLatLng = MapUtil.convertTrace2Map(point.getLocation());
                    if (currentLatLng != null) {
                        realTrackList.add(currentLatLng);
                        if (realTrackList != null && realTrackList.size() > 1) {
                            mapUtil.drawPolyline(realTrackList);
                        }
                        CurrentLocation.locTime = point.getLocTime();
                        CurrentLocation.latitude = currentLatLng.latitude;
                        CurrentLocation.longitude = currentLatLng.longitude;
                        if (mapUtil != null) {
                            mapUtil.updateStatus(currentLatLng, true);
                        }
                    }
                }
            }
        };
        this.entityListener = new OnEntityListener() {
            @Override
            public void onReceiveLocation(TraceLocation location) {
                if (location.getStatus() == 0 && !CommonUtil.isZeroPoint(location.getLatitude(), location.getLongitude())) {
                    MapUtil unused = mapUtil;
                    LatLng currentLatLng = MapUtil.convertTraceLocation2Map(location);
                    if (currentLatLng == null) {
                        return;
                    }
                    CurrentLocation.locTime = CommonUtil.toTimeStamp(location.getTime());
                    CurrentLocation.latitude = currentLatLng.latitude;
                    CurrentLocation.longitude = currentLatLng.longitude;
                    if (mapUtil != null) {
                        mapUtil.updateStatus(currentLatLng, true);
                    }
                    viewUtil.showToast(TracingActivity.this, String.format("%s ", location.toString()));
                }
            }
        };
        this.traceListener = new OnTraceListener() {
            @Override
            public void onBindServiceCallback(int errorNo, String message) {
                viewUtil.showToast(TracingActivity.this, String.format("onBindServiceCallback, errorNo:%d, message:%s ", Integer.valueOf(errorNo), message));
            }

            @Override
            public void onStartTraceCallback(int errorNo, String message) {
                if (errorNo == 0 || 10003 <= errorNo) {
                    trackApp.isTraceStarted = true;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.putBoolean("is_trace_started", true);
                    editor.apply();
                    setTraceBtnStyle();
                    registerReceiver();
                }
                viewUtil.showToast(TracingActivity.this, String.format("onStartTraceCallback, errorNo:%d, message:%s ", Integer.valueOf(errorNo), message));
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
                    setTraceBtnStyle();
                    setGatherBtnStyle();
                    unregisterPowerReceiver();
                }
                viewUtil.showToast(TracingActivity.this, String.format("onStopTraceCallback, errorNo:%d, message:%s ", Integer.valueOf(errorNo), message));
            }

            @Override
            public void onStartGatherCallback(int errorNo, String message) {
                if (errorNo == 0 || 12003 == errorNo) {
                    trackApp.isGatherStarted = true;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.putBoolean("is_gather_started", true);
                    editor.apply();
                    setGatherBtnStyle();
                }
                viewUtil.showToast(TracingActivity.this, String.format("onStartGatherCallback, errorNo:%d, message:%s ", Integer.valueOf(errorNo), message));
            }

            @Override
            public void onStopGatherCallback(int errorNo, String message) {
                if (errorNo == 0 || 13003 == errorNo) {
                    trackApp.isGatherStarted = false;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.remove("is_gather_started");
                    editor.apply();
                    setGatherBtnStyle();
                }
                viewUtil.showToast(TracingActivity.this, String.format("onStopGatherCallback, errorNo:%d, message:%s ", Integer.valueOf(errorNo), message));
            }

            @Override
            public void onPushCallback(byte messageType, PushMessage pushMessage) {
                if (messageType < 3 || messageType > 4) {
                    viewUtil.showToast(TracingActivity.this, pushMessage.getMessage());
                    return;
                }
                FenceAlarmPushInfo alarmPushInfo = pushMessage.getFenceAlarmPushInfo();
                if (alarmPushInfo == null) {
                    viewUtil.showToast(TracingActivity.this, String.format("onPushCallback, messageType:%d, messageContent:%s ", Byte.valueOf(messageType), pushMessage));
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
                    Notification notification = new Notification.Builder(trackApp).setContentTitle(getResources().getString(R.string.alarm_push_title)).setContentText(alarmInfo.toString()).setSmallIcon(R.mipmap.ic_launcher).setWhen(System.currentTimeMillis()).build();
                    notificationManager.notify(TracingActivity.Fun$1208(TracingActivity.this), notification);
                }
            }

            @Override
            public void onInitBOSCallback(int errorNo, String message) {
                viewUtil.showToast(TracingActivity.this, String.format("onInitBOSCallback, errorNo:%d, message:%s ", Integer.valueOf(errorNo), message));
            }

            @Override
            public void onTraceDataUploadCallBack(int i, String s, int i1, int i2) {
                viewUtil.showToast(TracingActivity.this,String.format("on tracedata update: %s %d,%d",s,i1,i2));
            }
        };
    }

    public static class RealTimeHandler extends Handler {
        RealTimeHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
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

    @Override
    public void onStart() {
        super.onStart();
        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23 && isNeedRequestPermissions(permissions)) {
            requestPermissions((String[]) permissions.toArray(new String[permissions.size()]), 0);
        }
        startRealTimeLoc(this.packInterval);
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
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mapUtil.onResume();
        requestBackgroundLocationPermission();
        if (Build.VERSION.SDK_INT >= 23) {
            String packageName = this.trackApp.getPackageName();
            boolean isIgnoring = this.powerManager.isIgnoringBatteryOptimizations(packageName);
            if (!isIgnoring) {
                Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
                intent.setData(Uri.parse("package:" + packageName));
                try {
                    startActivity(intent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void requestBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= 29 && ContextCompat.checkSelfPermission(this, Permission.ACCESS_BACKGROUND_LOCATION) != 0 && !ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.ACCESS_BACKGROUND_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.ACCESS_BACKGROUND_LOCATION}, 0);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.trackApp.isGatherStarted) {
            showDialog(getResources().getString(R.string.background_privacy_desc));
        } else {
            super.onBackPressed();
        }
    }

    private void showDialog(String message) {
        if (this.trackApp.mClient == null) {
            Toast.makeText(this, getResources().getString(R.string.privacy_policy_desc), Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示: ");
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                TracingActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (trackApp.isGatherStarted) {
                    trackApp.mClient.stopGather(traceListener);
                    trackApp.mClient.stopTrace(trackApp.mTrace, traceListener);
                    stopRealTimeLoc();
                    dialog.cancel();
                    TracingActivity.super.onBackPressed();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mapUtil.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopRealTimeLoc();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mapUtil.clear();
        this.realTrackList.clear();
        this.realTrackList = null;
        unregisterReceiver(this.mHomeAndLockReceiver);
        stopRealTimeLoc();
    }

    protected int getContentViewId() {
        return R.layout.activity_tracing;
    }
}
