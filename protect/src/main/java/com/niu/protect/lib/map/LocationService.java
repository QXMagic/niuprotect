package com.niu.protect.lib.map;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import com.niu.protect.lib.R;
import com.niu.protect.tools.ILog;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationManagerOptions;
import com.tencent.map.geolocation.TencentLocationRequest;

public class LocationService extends Service implements TencentLocationListener {
    private static final String TAG = "location";
    private static final String NOTIFICATION_CHANNEL_NAME = "location_notification";
    private static final int LOC_NOTIFICATIONID = 1;
    private TencentLocationManager mLocationManager;
    private boolean isCreateChannel;
    public LocationService() {
    }

    private final Messenger mMessenger = new Messenger(new IncomingHandler());

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        TencentLocationManagerOptions.setKey("YVABZ-IFRWJ-XRNFB-XGSBW-VSM23-K2BSG");
        mLocationManager = TencentLocationManager.getInstance(this);
        mLocationManager.enableForegroundLocation(LOC_NOTIFICATIONID, buildNotification());
        TencentLocationRequest request = TencentLocationRequest.create();
        mLocationManager.requestLocationUpdates(request, this, getMainLooper());
        return mMessenger.getBinder();
    }

    private Notification buildNotification() {
        Notification.Builder builder = null;
        Notification notification = null;
        //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
        NotificationManager notificationManager = null;
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        String channelId = getPackageName();
        if (!isCreateChannel) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
            notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
            notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            notificationManager.createNotificationChannel(notificationChannel);
            isCreateChannel = true;
        }
        builder = new Notification.Builder(getApplicationContext(), channelId);
        builder.setSmallIcon(R.mipmap.ico)
                .setContentTitle("LocationDemo")
                .setContentText("正在后台运行")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ico))
                .setWhen(System.currentTimeMillis());

        notification = builder.build();
        return notification;
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        ILog.d(TAG,"on location change i="+i+",s="+s);
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        ILog.d(TAG,"on status update s="+s+", i="+i+",s1="+s);
    }
}