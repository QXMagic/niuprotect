package com.niu.protect.map;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.LatestPointRequest;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.BaseRequest;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.baidu.trace.model.ProcessOption;
import com.niu.protect.BabyApplication;
import com.niu.protect.R;
import com.niu.protect.core.Constants;
import com.niu.protect.map.maputil.CommonUtil;
import com.niu.protect.model.ItemInfo;
import com.niu.protect.network.NetCheckUtil;
import com.niu.protect.tools.ILog;
import com.niu.protect.ui.map.TracingActivity;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
public class TrackManager {
    private Context mContext;
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    private LocRequest locRequest = null;
    private Notification notification = null;
    public List<ItemInfo> itemInfos = new ArrayList();
    public SharedPreferences trackConf = null;
    public LBSTraceClient mClient = null;
    public Trace mTrace = null;
    public long serviceId = 232360;
    public String entityName = CommonUtil.ENTITY_NAME;
    public boolean isRegisterReceiver = false;
    public boolean isTraceStarted = false;
    public boolean isGatherStarted = false;

    private void initTrack(Context context) {
        this.mContext = context;
        LBSTraceClient.setAgreePrivacy(context, true);
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
        this.trackConf = this.mContext.getSharedPreferences("track_conf", 0);
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
    }

    private void clearTraceStatus() {
        if (this.trackConf.contains("is_trace_started") || this.trackConf.contains("is_gather_started")) {
            SharedPreferences.Editor editor = this.trackConf.edit();
            editor.remove("is_trace_started");
            editor.remove("is_gather_started");
            editor.apply();
        }
    }

    private void initNotification() {
        Notification.Builder builder = new Notification.Builder(BabyApplication.getInstance());
        Intent notificationIntent = new Intent(BabyApplication.getInstance(), TracingActivity.class);
        Bitmap icon = BitmapFactory.decodeResource(BabyApplication.getInstance().getResources(), R.mipmap.icon_tracing);
        NotificationManager notificationManager = (NotificationManager) BabyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setContentIntent(PendingIntent.getActivity(BabyApplication.getInstance(), 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)).setLargeIcon(icon).setContentTitle("百度鹰眼").setSmallIcon(R.mipmap.icon_tracing).setContentText("服务正在运行...").setWhen(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= 26 && notificationManager != null) {
            NotificationChannel notificationChannel = new NotificationChannel(Constants.MESSAGE_TRACE, "trace_channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId(Constants.MESSAGE_TRACE);
        }
        Notification build = builder.build();
        this.notification = build;
        build.defaults = 1;
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
}
