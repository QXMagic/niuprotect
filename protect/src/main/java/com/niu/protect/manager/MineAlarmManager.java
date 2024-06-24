package com.niu.protect.manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
public class MineAlarmManager {
    public static final String ALARM_ACTION = "com.babyprotect.alarmAction";
    public static MineAlarmManager mineAlarmManager;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    private MineAlarmManager(Context context) {
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public static MineAlarmManager getInstance(Context context) {
        if (mineAlarmManager == null) {
            synchronized (MineAlarmManager.class) {
                mineAlarmManager = new MineAlarmManager(context);
            }
        }
        return mineAlarmManager;
    }

    public void startOnAlarm(Context context, long timesLater) {
        long triggerAtMillis = SystemClock.elapsedRealtime() + timesLater;
        Intent i = new Intent();
        i.setAction("com.babyprotect.alarmAction");
        this.pendingIntent = PendingIntent.getBroadcast(context, 0, i, 134217728);
        if (Build.VERSION.SDK_INT >= 23) {
            this.alarmManager.setExactAndAllowWhileIdle(2, triggerAtMillis, this.pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            this.alarmManager.setExact(2, triggerAtMillis, this.pendingIntent);
        } else {
            this.alarmManager.set(2, triggerAtMillis, this.pendingIntent);
        }
    }

    public void cancelAlarm() {
        PendingIntent pendingIntent;
        AlarmManager alarmManager = this.alarmManager;
        if (alarmManager != null && (pendingIntent = this.pendingIntent) != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
