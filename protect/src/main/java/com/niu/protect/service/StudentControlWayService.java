package com.niu.protect.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.niu.protect.core.Constants;
import com.niu.protect.manager.StudentMainController;
import com.niu.protect.tools.ILog;


public class StudentControlWayService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, getClass());
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long triggerAtTime = SystemClock.elapsedRealtime();
        manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, Constants.DEFAULT_TIMEOUT, pendingIntent);
        Intent floatWinIntent = new Intent(this, FloatingService.class);
        startService(floatWinIntent);
        ILog.d("----------", "--StudentControlWayService---------");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(180000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    StudentMainController.getInstance().requestMainControl();
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
