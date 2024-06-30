package com.niu.protect.accessibility.auto.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.blankj.utilcode.util.AppUtils;
import com.niu.protect.accessibility.auto.service.tmp.ConfigKey;

public class C7094 {
    /* renamed from: 刻槒唱镧詴 */
    public static void m27170(Context context, Intent intent) {
        try {
            LogHelper.m27985("startService");
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint({"WrongConstant"})
    /* renamed from: 肌緭 */
    public static void m16002(Service service) {
        try {
            Notification.Builder builder = new Notification.Builder(service);
            NotificationManager notificationManager = (NotificationManager) service.getSystemService("notification");
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel(service.getPackageName(), "运行提示", 2);
                notificationChannel.setLockscreenVisibility(-1);
                notificationChannel.setSound(null, null);
                notificationManager.createNotificationChannel(notificationChannel);
                builder.setChannelId(service.getPackageName());
            }
            String str = AppUtils.getAppName() + "运行中..";
            if (ConfigKey.m16029()) {
                str = "已解绑..";
            } else if (ConfigKey.m27247()) {
//                Intent m22785 = ModelManager.m22785("notification");
//                m22785.setFlags(270532608);
//                builder.setContentIntent(PendingIntent.getActivity(service, 0, m22785, PendingIntent.FLAG_IMMUTABLE));
            } else {
                str = "孩子端未运行";
            }
//            Notification build = builder.setSmallIcon(ModelManager.m22765().mo18078()).setContentTitle("运行提示").setContentText(str).setVisibility(-1).build();
//            build.flags = 32;
//            service.startForeground(1, build);
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.m27986().m16303("手机状态", "通知栏展示失败:" + e.getMessage());
        }
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public static void m27171(Service service) {
        service.stopSelf();
        service.stopForeground(true);
    }
}
