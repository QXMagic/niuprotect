package com.niu.protect.homework.service

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.niu.protect.homework.AlarmAlertActivity
import com.niu.protect.homework.HomeworkActivity
import com.niu.protect.homework.model.AppStage
import com.niu.protect.homework.receiver.TimerAlarmReceiver

class TimerService : Service() {

    companion object {
        const val CHANNEL_ID = "homework_timer_channel"
        const val ALARM_CHANNEL_ID = "homework_timer_alarm_channel"
        const val NOTIFICATION_ID = 10301
        const val ALARM_NOTIFICATION_ID = 10302
        const val ALARM_PENDING_INTENT_REQUEST_CODE = 100

        const val ACTION_TIMER_FINISHED = "com.niu.protect.homework.TIMER_FINISHED"

        const val ACTION_START = "com.niu.protect.homework.START_TIMER"
        const val ACTION_PAUSE = "com.niu.protect.homework.PAUSE_TIMER"
        const val ACTION_RESUME = "com.niu.protect.homework.RESUME_TIMER"
        const val ACTION_STOP = "com.niu.protect.homework.STOP_TIMER"
        const val ACTION_UPDATE_STAGE = "com.niu.protect.homework.UPDATE_STAGE"

        const val EXTRA_STAGE = "stage"
        const val EXTRA_REMAINING_SECONDS = "remaining_seconds"
        const val EXTRA_TOTAL_SECONDS = "total_seconds"
        const val EXTRA_EARNED_SECONDS = "earned_seconds"
        const val EXTRA_TIME_RATIO = "time_ratio"
        const val EXTRA_IS_EARN_MODE = "is_earn_mode"
        const val EXTRA_IS_TEST_MODE = "is_test_mode"
    }

    private val binder = LocalBinder()
    private val handler = Handler(Looper.getMainLooper())
    private var tickRunnable: Runnable? = null

    private var totalSeconds = 0
    private var timeRatio = 1f
    private var isEarnMode = false
    private var earnedSeconds = 0
    private var baseEarnedSeconds = 0
    private var isTestMode = false

    private var _remainingSeconds = 0
    private var _isRunning = false
    private var _currentStage = AppStage.INITIAL_TIMER.name

    // 使用 elapsedRealtime 作为计时基准（包含设备睡眠时间）
    private var timerStartElapsed: Long = 0L
    private var remainingAtStart: Int = 0
    private var elapsedAtPause: Int = 0

    private var alarmManager: AlarmManager? = null
    private var alarmPendingIntent: PendingIntent? = null

    private var wakeLock: PowerManager.WakeLock? = null

    inner class LocalBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "NiuProtect::HomeworkTimerWakeLock"
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val stage = intent.getStringExtra(EXTRA_STAGE) ?: AppStage.INITIAL_TIMER.name
                val remaining = intent.getIntExtra(EXTRA_REMAINING_SECONDS, 0)
                totalSeconds = intent.getIntExtra(EXTRA_TOTAL_SECONDS, 0)
                timeRatio = intent.getFloatExtra(EXTRA_TIME_RATIO, 1f)
                isEarnMode = intent.getBooleanExtra(EXTRA_IS_EARN_MODE, false)
                earnedSeconds = intent.getIntExtra(EXTRA_EARNED_SECONDS, 0)
                baseEarnedSeconds = earnedSeconds
                isTestMode = intent.getBooleanExtra(EXTRA_IS_TEST_MODE, false)
                startTimer(stage, remaining)
            }
            ACTION_PAUSE -> pauseTimer()
            ACTION_RESUME -> resumeTimer()
            ACTION_STOP -> stopTimer()
            ACTION_UPDATE_STAGE -> {
                val stage = intent.getStringExtra(EXTRA_STAGE) ?: return START_STICKY
                _currentStage = stage
                updateNotification()
            }
        }
        return START_STICKY
    }

    private fun startTimer(stage: String, remaining: Int) {
        _currentStage = stage
        _remainingSeconds = remaining
        remainingAtStart = remaining
        elapsedAtPause = 0
        _isRunning = true

        timerStartElapsed = SystemClock.elapsedRealtime()

        acquireWakeLock()
        startForeground(NOTIFICATION_ID, createNotification())

        if (!isEarnMode && remaining > 0) {
            scheduleExactAlarm(remaining)
        }

        startTickLoop()
    }

    private fun scheduleExactAlarm(remainingSeconds: Int) {
        cancelExactAlarm()

        val triggerTime = SystemClock.elapsedRealtime() + remainingSeconds * 1000L

        val intent = TimerAlarmReceiver.createIntent(this, getStageDisplayText(), "时间到了！")
        alarmPendingIntent = PendingIntent.getBroadcast(
            this,
            ALARM_PENDING_INTENT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmPendingIntent?.let { pendingIntent ->
            val canScheduleExact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                alarmManager?.canScheduleExactAlarms() == true
            } else {
                true
            }

            try {
                if (canScheduleExact && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager?.setExactAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent
                    )
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager?.setAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent
                    )
                } else {
                    alarmManager?.setExact(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent
                    )
                }
            } catch (e: Exception) {
                Log.e("HwTimerService", "Failed to schedule alarm", e)
            }
        }
    }

    private fun cancelExactAlarm() {
        alarmPendingIntent?.let { pendingIntent ->
            try {
                alarmManager?.cancel(pendingIntent)
                pendingIntent.cancel()
            } catch (e: Exception) {
                Log.e("HwTimerService", "Error cancelling alarm", e)
            }
        }
        alarmPendingIntent = null
    }

    private fun startTickLoop() {
        tickRunnable?.let { handler.removeCallbacks(it) }

        val interval = if (isTestMode) 100L else 1000L

        tickRunnable = object : Runnable {
            override fun run() {
                if (!_isRunning) return

                if (isEarnMode) {
                    val elapsedMs = SystemClock.elapsedRealtime() - timerStartElapsed
                    val elapsedSec = (elapsedMs / 1000).toInt()
                    _remainingSeconds = elapsedSec
                    earnedSeconds = baseEarnedSeconds + (elapsedSec * timeRatio).toInt()
                    updateNotification()
                    handler.postDelayed(this, interval)
                } else {
                    val elapsedMs = SystemClock.elapsedRealtime() - timerStartElapsed
                    val elapsedSec = (elapsedMs / 1000).toInt()
                    _remainingSeconds = (remainingAtStart - elapsedSec).coerceAtLeast(0)
                    updateNotification()

                    if (_remainingSeconds <= 0) {
                        _isRunning = false
                        onTimerFinished()
                    } else {
                        handler.postDelayed(this, interval)
                    }
                }
            }
        }

        handler.post(tickRunnable!!)
    }

    private fun pauseTimer() {
        _isRunning = false
        tickRunnable?.let { handler.removeCallbacks(it) }
        tickRunnable = null

        cancelExactAlarm()

        val elapsedMs = SystemClock.elapsedRealtime() - timerStartElapsed
        elapsedAtPause = (elapsedMs / 1000).toInt()
        remainingAtStart = (remainingAtStart - elapsedAtPause).coerceAtLeast(0)

        _remainingSeconds = if (isEarnMode) elapsedAtPause else remainingAtStart

        releaseWakeLock()
        updateNotification()
    }

    private fun resumeTimer() {
        if (remainingAtStart > 0 || isEarnMode) {
            _isRunning = true
            timerStartElapsed = SystemClock.elapsedRealtime()
            if (isEarnMode) {
                remainingAtStart = elapsedAtPause
                timerStartElapsed -= (elapsedAtPause * 1000L)
            }
            if (!isEarnMode && remainingAtStart > 0) {
                scheduleExactAlarm(remainingAtStart)
            }
            acquireWakeLock()
            startTickLoop()
        }
        updateNotification()
    }

    private fun stopTimer() {
        _isRunning = false
        tickRunnable?.let { handler.removeCallbacks(it) }
        tickRunnable = null
        cancelExactAlarm()
        releaseWakeLock()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun onTimerFinished() {
        tickRunnable?.let { handler.removeCallbacks(it) }
        tickRunnable = null
        cancelExactAlarm()
        releaseWakeLock()

        showAlarmNotification()
        startAlarmActivity()

        sendBroadcast(Intent(ACTION_TIMER_FINISHED).setPackage(packageName))
        handler.postDelayed({
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }, 500)
    }

    private fun showAlarmNotification() {
        val fullScreenIntent = AlarmAlertActivity.createIntent(this, getStageDisplayText(), "时间到了！")
        val fullScreenPendingIntent = PendingIntent.getActivity(
            this, 1, fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmNotification = NotificationCompat.Builder(this, ALARM_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(getStageDisplayText())
            .setContentText("时间到了！")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setAutoCancel(true)
            .setOngoing(false)
            .setVibrate(longArrayOf(0, 500, 300, 500, 300, 500))
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(ALARM_NOTIFICATION_ID, alarmNotification)
    }

    private fun startAlarmActivity() {
        try {
            startActivity(AlarmAlertActivity.createIntent(this, getStageDisplayText(), "时间到了！"))
        } catch (e: Exception) {
            Log.e("HwTimerService", "Failed to start AlarmAlertActivity", e)
        }
    }

    private fun getStageDisplayText(): String {
        return when (_currentStage) {
            AppStage.INITIAL_TIMER.name -> "初始作业时间"
            AppStage.HOMEWORK_TIMER.name -> "写作业时间"
            AppStage.EARN_TIME.name -> "赚取时间中"
            else -> "计时器"
        }
    }

    private fun acquireWakeLock() {
        try {
            wakeLock?.let { if (!it.isHeld) it.acquire(10 * 60 * 60 * 1000L) }
        } catch (e: Exception) {
            Log.e("HwTimerService", "Error acquiring WakeLock", e)
        }
    }

    private fun releaseWakeLock() {
        try {
            wakeLock?.let { if (it.isHeld) it.release() }
        } catch (e: Exception) {
            Log.e("HwTimerService", "Error releasing WakeLock", e)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)

            val channel = NotificationChannel(
                CHANNEL_ID, "作业计时器", NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "作业计时器后台运行通知"
                setShowBadge(false)
            }
            manager.createNotificationChannel(channel)

            val alarmChannel = NotificationChannel(
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
            manager.createNotificationChannel(alarmChannel)
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, HomeworkActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stageText = getStageDisplayText()
        val mins = _remainingSeconds / 60
        val secs = _remainingSeconds % 60
        val timeText = if (isEarnMode) {
            String.format("已进行 %02d:%02d", mins, secs)
        } else {
            String.format("剩余 %02d:%02d", mins, secs)
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(stageText)
            .setContentText(timeText)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentIntent(pendingIntent)
            .setOngoing(_isRunning)
            .setOnlyAlertOnce(true)
            .build()
    }

    private fun updateNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, createNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        tickRunnable?.let { handler.removeCallbacks(it) }
        tickRunnable = null
        cancelExactAlarm()
        releaseWakeLock()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if (_isRunning) {
            val restartIntent = Intent(applicationContext, TimerService::class.java).apply {
                action = if (isEarnMode) ACTION_RESUME else ACTION_START
                putExtra(EXTRA_STAGE, _currentStage)
                putExtra(EXTRA_REMAINING_SECONDS, _remainingSeconds)
                putExtra(EXTRA_TOTAL_SECONDS, totalSeconds)
                putExtra(EXTRA_IS_EARN_MODE, isEarnMode)
                putExtra(EXTRA_TIME_RATIO, timeRatio)
                putExtra(EXTRA_EARNED_SECONDS, earnedSeconds)
                putExtra(EXTRA_IS_TEST_MODE, isTestMode)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(restartIntent)
            } else {
                startService(restartIntent)
            }
        } else {
            super.onTaskRemoved(rootIntent)
        }
    }

    fun getEarnedSeconds(): Int = earnedSeconds
    fun getTotalSeconds(): Int = totalSeconds
    fun getTimeRatio(): Float = timeRatio
    fun isEarnMode(): Boolean = isEarnMode
    fun getBaseEarnedSeconds(): Int = baseEarnedSeconds
    fun getRemainingSeconds(): Int = _remainingSeconds
    fun isTimerRunning(): Boolean = _isRunning
}
