package com.niu.protect.homework.ui

import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.niu.protect.homework.HomeworkLockManager
import com.niu.protect.homework.model.AppStage
import com.niu.protect.homework.viewmodel.MainViewModel

@Composable
fun MainScreen(vm: MainViewModel) {
    val stage by vm.stage.collectAsStateWithLifecycle()
    val settings by vm.settings.collectAsStateWithLifecycle()
    val timerState by vm.timerState.collectAsStateWithLifecycle()
    val activityList by vm.activityList.collectAsStateWithLifecycle()
    val selectedActivity by vm.selectedActivity.collectAsStateWithLifecycle()
    val earnedSeconds by vm.earnedSeconds.collectAsStateWithLifecycle()
    val showTimeUpDialog by vm.showTimeUpDialog.collectAsStateWithLifecycle()

    var showSettings by remember { mutableStateOf(false) }
    var showPinDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    LaunchedEffect(Unit) {
        vm.onTimerFinished = {
            try {
                val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer.create(context, notificationUri)
                mediaPlayer?.isLooping = true
                mediaPlayer?.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    if (showTimeUpDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("时间到！") },
            text = {
                when (stage) {
                    AppStage.INITIAL_TIMER -> Text("初始作业时间已结束，请检查作业是否完成。")
                    AppStage.HOMEWORK_TIMER -> Text("作业时间已结束，请检查作业是否完成。")
                    else -> Text("计时结束")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                    vm.dismissTimeUpDialog()
                    vm.timeUp()
                }) {
                    Text("确定")
                }
            }
        )
    }

    when (stage) {
        AppStage.INITIAL_TIMER -> {
            if (timerState.isRunning || timerState.totalSeconds > 0) {
                TimerRunningScreen(
                    remainingSeconds = timerState.remainingSeconds,
                    totalSeconds = timerState.totalSeconds,
                    isRunning = timerState.isRunning,
                    onPause = { vm.pauseTimer() },
                    onResume = { vm.resumeTimer() },
                    stageText = "初始作业时间"
                )
            } else {
                InitialTimerScreen(
                    settings = settings,
                    onStartTimer = { vm.startInitialTimer() },
                    onOpenSettings = { showPinDialog = true }
                )
            }
        }

        AppStage.CHECK_HOMEWORK -> {
            CheckHomeworkScreen(
                onComplete = { vm.homeworkCompleted() },
                onNotComplete = { vm.homeworkNotCompleted() }
            )
        }

        AppStage.EARN_TIME -> {
            EarnTimeScreen(
                activityList = activityList,
                earnedSeconds = earnedSeconds,
                targetSeconds = settings.targetEarnMinutes * 60,
                selectedActivity = selectedActivity,
                isRunning = timerState.isRunning,
                elapsedSeconds = timerState.remainingSeconds,
                onSelectActivity = { },
                onStartEarning = { vm.startEarningTimer(it) },
                onStopEarning = { vm.stopEarning() },
                onComplete = { vm.startHomeworkWithEarnedTime() },
                canComplete = vm.canStartHomework()
            )
        }

        AppStage.HOMEWORK_TIMER -> {
            if (timerState.isRunning) {
                TimerRunningScreen(
                    remainingSeconds = timerState.remainingSeconds,
                    totalSeconds = timerState.totalSeconds,
                    isRunning = true,
                    onPause = { },
                    onResume = { },
                    stageText = "写作业时间",
                    canPause = false
                )
            } else if (timerState.remainingSeconds > 0) {
                LaunchedEffect(Unit) { vm.startHomeworkTimer() }
            } else {
                TimerRunningScreen(
                    remainingSeconds = timerState.remainingSeconds,
                    totalSeconds = timerState.totalSeconds,
                    isRunning = false,
                    onPause = { },
                    onResume = { },
                    stageText = "写作业时间",
                    canPause = false
                )
            }
        }

        AppStage.COMPLETED -> {
            CompletionScreen(onRestart = { vm.restart() })
        }
    }

    if (showSettings) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            SettingsScreen(
                settings = settings,
                activityList = activityList,
                onSettingsChange = { vm.updateSettings(it) },
                onAddActivity = { name, ratio, desc -> vm.addActivity(name, ratio, desc) },
                onUpdateActivity = { vm.updateActivity(it) },
                onDeleteActivity = { vm.deleteActivity(it) },
                onBack = { showSettings = false }
            )
        }
    }

    if (showPinDialog) {
        var pin by remember { mutableStateOf("") }
        var error by remember { mutableStateOf(false) }
        AlertDialog(
            onDismissRequest = { showPinDialog = false },
            title = { Text("请输入家长密码") },
            text = {
                Column {
                    OutlinedTextField(
                        value = pin,
                        onValueChange = { pin = it.filter { c -> c.isDigit() }; error = false },
                        label = { Text("家长密码") },
                        isError = error,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (error) {
                        Text(
                            text = "密码错误",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (HomeworkLockManager.checkPin(context, pin)) {
                        showPinDialog = false
                        showSettings = true
                    } else {
                        error = true
                    }
                }) { Text("确定") }
            },
            dismissButton = {
                TextButton(onClick = { showPinDialog = false }) { Text("取消") }
            }
        )
    }
}
