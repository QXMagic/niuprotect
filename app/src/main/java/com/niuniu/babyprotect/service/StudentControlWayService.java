package com.niuniu.babyprotect.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.CoroutineLiveDataKt;
import com.niuniu.babyprotect.manager.StudentMainController;
import com.niuniu.babyprotect.tools.ILog;
public class StudentControlWayService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AlarmManager manager = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intent intent = new Intent(this, getClass());
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 134217728);
        long triggerAtTime = SystemClock.elapsedRealtime();
        manager.setRepeating(0, triggerAtTime, CoroutineLiveDataKt.DEFAULT_TIMEOUT, pendingIntent);
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
        return 1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
