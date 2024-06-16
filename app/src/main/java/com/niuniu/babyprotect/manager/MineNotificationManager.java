package com.niuniu.babyprotect.manager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import com.niuniu.babyprotect.BabyApplication;
import im.niu.protect.R;
import com.niuniu.babyprotect.service.FloatingService;
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
        Intent floatWinIntent = new Intent(BabyApplication.getInstance(), FloatingService.class);
        BabyApplication.getInstance().startService(floatWinIntent);
    }

    private void showNotification() {
        registerNotificationChannel();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.context, "com.github.103style.SampleService");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("3985学生端");
        mBuilder.setContentText("正在守护");
        if (Build.VERSION.SDK_INT < 24) {
            mBuilder.setContentTitle(this.context.getResources().getString(R.string.app_name));
        }
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
