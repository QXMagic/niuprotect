package com.niu.protect.manager;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.niu.protect.BabyApplication;
import com.niu.protect.keepalive.KeepAliveJobService;
import com.niu.protect.keepalive.LocalForegroundService;
import com.niu.protect.keepalive.RemoteForegroundService;
import com.niu.protect.service.FloatingService;
public class KeepAliveManger {
    private static KeepAliveManger instance;

    public static KeepAliveManger getInstance() {
        if (instance == null) {
            synchronized (KeepAliveManger.class) {
                if (instance == null) {
                    instance = new KeepAliveManger();
                }
            }
        }
        return instance;
    }

    public void keepAliveByTowService(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            context.startForegroundService(new Intent(context, LocalForegroundService.class));
            context.startForegroundService(new Intent(context, RemoteForegroundService.class));
        } else {
            context.startService(new Intent(context, LocalForegroundService.class));
            context.startService(new Intent(context, RemoteForegroundService.class));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            KeepAliveJobService.startJob(context);
        }
    }

    public void showNotification(Service service) {
        MineNotificationManager.getInstance().onCreate(service);
    }

    private void showOnePix() {
        Intent floatWinIntent = new Intent(BabyApplication.getInstance(), FloatingService.class);
        BabyApplication.getInstance().startService(floatWinIntent);
    }

    private void keepalive(Application context) {
    }
}
