package com.niu.protect.homework.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.niu.protect.homework.AlarmAlertActivity
import com.niu.protect.homework.service.TimerService

/**
 * AlarmManager 触发的广播接收器：即使在 Doze 模式 / 应用后台也能被唤醒，执行闹钟提醒。
 */
class TimerAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_TIMER_ALARM = "com.niu.protect.homework.TIMER_ALARM"
        const val ALARM_CHANNEL_ID = "homework_timer_alarm_channel"
        const val ALARM_NOTIFICATION_ID = 10399

        fun createIntent(context: Context, stage: String, message: String): Intent {
            return Intent(context, TimerAlarmReceiver::class.java).apply {
                action = ACTION_TIMER_ALARM
                setPackage(context.packageName)
                putExtra(AlarmAlertActivity.EXTRA_STAGE, stage)
                putExtra(AlarmAlertActivity.EXTRA_MESSAGE, message)
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_TIMER_ALARM) return

        val stage = intent.getStringExtra(AlarmAlertActivity.EXTRA_STAGE) ?: "计时结束"
        val message = intent.getStringExtra(AlarmAlertActivity.EXTRA_MESSAGE) ?: "时间到了！"

        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        @Suppress("DEPRECATION")
        val wakeLock = powerManager.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or
                    PowerManager.ON_AFTER_RELEASE,
            "NiuProtect::HomeworkAlarmReceiverWakeLock"
        )
        wakeLock.acquire(60 * 1000L)

        showAlarmNotification(context, stage, message)

        try {
            context.startActivity(AlarmAlertActivity.createIntent(context, stage, message))
        } catch (e: Exception) {
            Log.e("HwAlarmReceiver", "Failed to start AlarmAlertActivity", e)
        }

        context.sendBroadcast(
            Intent(TimerService.ACTION_TIMER_FINISHED).setPackage(context.packageName)
        )

        try {
            val stopIntent = Intent(context, TimerService::class.java).apply {
                action = TimerService.ACTION_STOP
            }
            context.startService(stopIntent)
        } catch (e: Exception) {
            Log.e("HwAlarmReceiver", "Failed to stop TimerService", e)
        }

        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            if (wakeLock.isHeld) wakeLock.release()
        }, 3000)
    }

    private fun showAlarmNotification(context: Context, stage: String, message: String) {
        createAlarmChannel(context)

        val fullScreenIntent = AlarmAlertActivity.createIntent(context, stage, message)
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context, 2, fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmNotification = NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(stage)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setAutoCancel(true)
            .setOngoing(false)
            .setVibrate(longArrayOf(0, 500, 300, 500, 300, 500))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(ALARM_NOTIFICATION_ID, alarmNotification)
    }

    private fun createAlarmChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ALARM_CHANNEL_ID, "计时结束提醒", NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "计时结束时以闹钟形式提醒"
                setShowBadge(false)
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 300, 500, 300, 500)
                val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                    ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                setSound(
                    alarmUri,
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
