package com.niu.protect.manager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.niu.protect.R;
import com.niu.protect.core.Constants;
import com.niu.protect.service.FloatingService;
public class MineNotificationManager {
    public static final String CHANNEL_ID = "com.github.103style.SampleService";
    public static final String CHANNEL_NAME = "com.github.103style";
    private static MineNotificationManager instance = new MineNotificationManager();
    Service context;
    int notifyId = 1101;

    private MineNotificationManager() {
    }

    public static MineNotificationManager getInstance() {
        return instance;
    }

    public void onCreate(Service service) {
        this.context = service;
        showNotification();
    }

    public void runService() {
        Context context= Constants.MainInstance.getContext();
        Intent floatWinIntent = new Intent(context, FloatingService.class);
        context.startService(floatWinIntent);
    }

    private void showNotification() {
        registerNotificationChannel();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.context, "com.github.103style.SampleService");
        mBuilder.setSmallIcon(R.mipmap.ico);
        mBuilder.setContentTitle(Constants.APP_NAME);
        mBuilder.setContentText("正在守护");
        this.context.startForeground(this.notifyId, mBuilder.build());
    }

    private void registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager mNotificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = mNotificationManager.getNotificationChannel("com.github.103style.SampleService");
            if (notificationChannel == null) {
                NotificationChannel channel = new NotificationChannel("com.github.103style.SampleService", "com.github.103style", NotificationManager.IMPORTANCE_HIGH);
                channel.enableLights(false);
                channel.setLightColor(Color.GRAY);
                channel.setLockscreenVisibility(1);
                mNotificationManager.createNotificationChannel(channel);
            }
        }
    }
}
