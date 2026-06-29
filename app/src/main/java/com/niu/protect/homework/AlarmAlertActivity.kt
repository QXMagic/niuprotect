package com.niu.protect.homework

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niu.protect.homework.service.TimerService
import com.niu.protect.homework.ui.theme.HomeworkTheme
import java.util.Timer
import java.util.TimerTask

/**
 * 全屏闹钟提醒 Activity：计时结束时以系统闹钟形式弹窗，支持锁屏显示、响铃、震动。
 */
class AlarmAlertActivity : ComponentActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var vibratorTimer: Timer? = null

    companion object {
        const val EXTRA_STAGE = "stage"
        const val EXTRA_MESSAGE = "message"

        fun createIntent(context: Context, stage: String, message: String): Intent {
            return Intent(context, AlarmAlertActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                putExtra(EXTRA_STAGE, stage)
                putExtra(EXTRA_MESSAGE, message)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            )
        }

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        @Suppress("DEPRECATION")
        wakeLock = powerManager.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or
                    PowerManager.ON_AFTER_RELEASE,
            "NiuProtect::HomeworkAlarmWakeLock"
        )
        wakeLock?.acquire(60 * 1000L)

        playAlarmSound()
        startVibration()

        val stage = intent.getStringExtra(EXTRA_STAGE) ?: "计时结束"
        val message = intent.getStringExtra(EXTRA_MESSAGE) ?: "时间到了！"

        setContent {
            HomeworkTheme {
                AlarmScreen(stage = stage, message = message, onDismiss = { dismissAlarm() })
            }
        }
    }

    @Composable
    private fun AlarmScreen(stage: String, message: String, onDismiss: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.error.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "⏰", fontSize = 80.sp)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stage,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(48.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(72.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "我知道了", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    private fun playAlarmSound() {
        try {
            val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(this@AlarmAlertActivity, alarmUri)
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                isLooping = true
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "无法播放提醒声音", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startVibration() {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }

            val pattern = longArrayOf(0, 500, 300, 500, 300, 500, 300, 1000)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(pattern, 0)
            }

            vibratorTimer?.cancel()
            vibratorTimer = Timer()
            vibratorTimer?.schedule(object : TimerTask() {
                override fun run() {
                    vibrator.cancel()
                }
            }, 30 * 1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopVibration() {
        try {
            vibratorTimer?.cancel()
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
            vibrator.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dismissAlarm() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        stopVibration()
        wakeLock?.let { if (it.isHeld) it.release() }
        sendBroadcast(Intent(TimerService.ACTION_TIMER_FINISHED).setPackage(packageName))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        stopVibration()
        wakeLock?.let { if (it.isHeld) it.release() }
    }

    @Deprecated("Block back key on alarm screen")
    override fun onBackPressed() {
        // 禁止返回键关闭，必须点击按钮
    }
}
