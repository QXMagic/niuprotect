package com.niuniu.babyprotect;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.LatestPointRequest;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.BaseRequest;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.baidu.trace.model.ProcessOption;
import com.niuniu.babyprotect.accessibility.auto.bean.PageInfoModel;
import com.niuniu.babyprotect.accessibility.auto.device.info.DeviceAccessiFactory;
import com.niuniu.babyprotect.manager.KeepAliveManger;
import com.niuniu.babyprotect.map.maputil.CommonUtil;
import com.niuniu.babyprotect.map.model.ItemInfo;
import com.niuniu.babyprotect.network.NetCheckUtil;
import com.niuniu.babyprotect.third.bugly.BuglyTools;
import com.niuniu.babyprotect.third.umeng.UMengManager;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.ui.map.TracingActivity;
import com.tencent.mmkv.MMKV;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import im.niu.protect.R;

public class BabyApplication extends Application {
    private static BabyApplication instance;
    public static int width;
    List<PageInfoModel> autoSettingSteps;
    private static final String TAG = BabyApplication.class.getName();
    public static int height = 1000;
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    private LocRequest locRequest = null;
    private Notification notification = null;
    public Context mContext = null;
    public List<ItemInfo> itemInfos = new ArrayList();
    public SharedPreferences trackConf = null;
    public LBSTraceClient mClient = null;
    public Trace mTrace = null;
    public long serviceId = 232360;
    public String entityName = CommonUtil.ENTITY_NAME;
    public boolean isRegisterReceiver = false;
    public boolean isTraceStarted = false;
    public boolean isGatherStarted = false;
    String appChannel = "main";

    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
        instance = this;
        this.mContext = this;
        getAppChannel();
        KeepAliveManger.getInstance().keepAliveByTowService(this);
        init();
        this.autoSettingSteps = DeviceAccessiFactory.createDeviceInfo(getInstance());
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
        initTrack();
    }

    public void initTrack() {
        LBSTraceClient.setAgreePrivacy(this.mContext, true);
        if ("com.baidu.track:remote".equals(CommonUtil.getCurProcessName(this.mContext))) {
            return;
        }
        initView();
        initNotification();
        try {
            this.mClient = new LBSTraceClient(this.mContext);
        } catch (Exception e) {
            e.getMessage();
            ILog.d("LBSTraceClient", e.getMessage());
        }
        Trace trace = new Trace(this.serviceId, this.entityName);
        this.mTrace = trace;
        trace.setNotification(this.notification);
        StringBuilder sb = new StringBuilder();
        sb.append(this.mClient != null);
        sb.append("");
        ILog.d("LBSTraceClient", sb.toString());
        this.trackConf = getSharedPreferences("track_conf", 0);
        this.locRequest = new LocRequest(this.serviceId);
        LBSTraceClient lBSTraceClient = this.mClient;
        if (lBSTraceClient != null) {
            lBSTraceClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
                @Override
                public Map<String, String> onTrackAttributeCallback() {
                    Map<String, String> map = new HashMap<>();
                    map.put("key1", "value1");
                    map.put("key2", "value2");
                    return map;
                }

                @Override
                public Map<String, String> onTrackAttributeCallback(long locTime) {
                    PrintStream printStream = System.out;
                    printStream.println("onTrackAttributeCallback, locTime : " + locTime);
                    Map<String, String> map = new HashMap<>();
                    map.put("key1", "value1");
                    map.put("key2", "value2");
                    return map;
                }
            });
        }
        clearTraceStatus();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public static BabyApplication getInstance() {
        return instance;
    }

    private void init() {
        SDKInitializer.setAgreePrivacy(this, true);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        ILog.d("channel", this.appChannel);
        UMengManager.preInit(this, this.appChannel);
        BuglyTools.initBugly(getApplicationContext());
        getAndroiodScreenProperty();
    }

    public List<PageInfoModel> getAutoSettingSteps() {
        return this.autoSettingSteps;
    }

    public void setAutoSettingSteps(List<PageInfoModel> mAutoSettingSteps) {
        this.autoSettingSteps = mAutoSettingSteps;
    }

    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        float f = dm.density;
        int i = dm.densityDpi;
    }

    public void getCurrentLocation(OnEntityListener entityListener, OnTrackListener trackListener) {
        if (this.mClient == null) {
            return;
        }
        if (NetCheckUtil.isNetworkAvailable(this.mContext) && this.trackConf.contains("is_trace_started") && this.trackConf.contains("is_gather_started") && this.trackConf.getBoolean("is_trace_started", false) && this.trackConf.getBoolean("is_gather_started", false)) {
            LatestPointRequest request = new LatestPointRequest(getTag(), this.serviceId, this.entityName);
            ProcessOption processOption = new ProcessOption();
            processOption.setNeedDenoise(true);
            processOption.setRadiusThreshold(100);
            request.setProcessOption(processOption);
            this.mClient.queryLatestPoint(request, trackListener);
            return;
        }
        this.mClient.queryRealTimeLoc(this.locRequest, entityListener);
    }

    private void initView() {
        getScreenSize();
    }

    private void initNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent notificationIntent = new Intent(this, TracingActivity.class);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)).setLargeIcon(icon).setContentTitle("3985学生端").setSmallIcon(R.mipmap.ic_launcher).setContentText("服务正在运行...").setWhen(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= 26 && notificationManager != null) {
            NotificationChannel notificationChannel = new NotificationChannel("trace", "trace_channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId("trace");
        }
        Notification build = builder.build();
        this.notification = build;
        build.defaults = 1;
    }

    private void getScreenSize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }

    private void clearTraceStatus() {
        if (this.trackConf.contains("is_trace_started") || this.trackConf.contains("is_gather_started")) {
            SharedPreferences.Editor editor = this.trackConf.edit();
            editor.remove("is_trace_started");
            editor.remove("is_gather_started");
            editor.apply();
        }
    }

    public void initRequest(BaseRequest request) {
        request.setTag(getTag());
        request.setServiceId(this.serviceId);
    }

    public int getTag() {
        return this.mSequenceGenerator.incrementAndGet();
    }

    public void clear() {
        this.itemInfos.clear();
    }

    private void getAppChannel() {
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                String valueOf = String.valueOf(applicationInfo.metaData.get("channel"));
                this.appChannel = valueOf;
                ILog.d("channel get", valueOf);
            }
        } catch (PackageManager.NameNotFoundException e) {
            ILog.d("channel get", "NameNotFoundException");
            e.printStackTrace();
        }
    }
}
