package com.niu.protect.homework.viewmodel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.niu.protect.homework.HomeworkLockManager
import com.niu.protect.homework.data.TimerDataStore
import com.niu.protect.homework.data.TimerStateData
import com.niu.protect.homework.model.ActivityItem
import com.niu.protect.homework.model.AppSettings
import com.niu.protect.homework.model.AppStage
import com.niu.protect.homework.model.TimerState
import com.niu.protect.homework.service.TimerService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application
    private val dataStore = TimerDataStore(context)

    private val _stage = MutableStateFlow(AppStage.INITIAL_TIMER)
    val stage: StateFlow<AppStage> = _stage.asStateFlow()

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private val _activityList = MutableStateFlow<List<ActivityItem>>(emptyList())
    val activityList: StateFlow<List<ActivityItem>> = _activityList.asStateFlow()

    private val _selectedActivity = MutableStateFlow<ActivityItem?>(null)
    val selectedActivity: StateFlow<ActivityItem?> = _selectedActivity.asStateFlow()

    private val _earnedSeconds = MutableStateFlow(0)
    val earnedSeconds: StateFlow<Int> = _earnedSeconds.asStateFlow()

    private val _showTimeUpDialog = MutableStateFlow(false)
    val showTimeUpDialog: StateFlow<Boolean> = _showTimeUpDialog.asStateFlow()

    private val _isRestoring = MutableStateFlow(true)
    val isRestoring: StateFlow<Boolean> = _isRestoring.asStateFlow()

    private var timerJob: Job? = null
    private var baseEarnedSeconds: Int = 0

    var onTimerFinished: (() -> Unit)? = null
    var onActivityChanged: ((String) -> Unit)? = null

    private var textToSpeech: TextToSpeech? = null
    private var ttsInitialized = false

    private var timerStartTime: Long = 0

    private var timerService: TimerService? = null
    private var isServiceBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TimerService.LocalBinder
            timerService = binder.getService()
            isServiceBound = true
            syncFromService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            if (_timerState.value.isRunning && _stage.value != AppStage.EARN_TIME) {
                onTimerComplete()
            }
            timerService = null
            isServiceBound = false
        }
    }

    private var syncJob: Job? = null

    val earnedMinutes: Int
        get() = _earnedSeconds.value / 60

    private val timerFinishedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == TimerService.ACTION_TIMER_FINISHED) {
                onTimerComplete()
            }
        }
    }

    init {
        initTextToSpeech()

        val filter = IntentFilter(TimerService.ACTION_TIMER_FINISHED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(timerFinishedReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("UnspecifiedRegisterReceiverFlag")
            context.registerReceiver(timerFinishedReceiver, filter)
        }

        val serviceIntent = Intent(context, TimerService::class.java)
        context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        // 把作业阶段同步给“作业时间锁”——无障碍服务据此决定是否锁机
        viewModelScope.launch {
            stage.collect { HomeworkLockManager.updateStage(context, it.name) }
        }

        viewModelScope.launch {
            loadSavedData()
        }
    }

    private fun initTextToSpeech() {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech?.setLanguage(Locale.CHINESE)
                ttsInitialized =
                    result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
            } else {
                Log.e("HwMainViewModel", "TTS initialization failed")
            }
        }
    }

    private fun speak(message: String) {
        if (ttsInitialized && textToSpeech != null) {
            textToSpeech?.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            onActivityChanged?.invoke(message)
        }
    }

    private suspend fun loadSavedData() {
        dataStore.getSettings().first().let { _settings.value = it }
        dataStore.getActivities().first().let { _activityList.value = it }
        dataStore.getTimerState().first().let { restoreTimerState(it) }
        _isRestoring.value = false
    }

    private fun restoreTimerState(savedState: TimerStateData) {
        try {
            _stage.value = AppStage.valueOf(savedState.stage)

            if (savedState.stage == AppStage.INITIAL_TIMER.name && savedState.timerStartTime == 0L) {
                return
            }

            if (savedState.isPaused) {
                _timerState.value = TimerState(
                    isRunning = false,
                    remainingSeconds = savedState.pausedRemainingSeconds,
                    totalSeconds = if (savedState.stage == AppStage.HOMEWORK_TIMER.name) {
                        savedState.earnedSeconds
                    } else {
                        savedState.totalSeconds
                    }
                )
                _earnedSeconds.value = savedState.earnedSeconds
                baseEarnedSeconds = savedState.baseEarnedSeconds
                if (savedState.selectedActivityId != null) {
                    _selectedActivity.value =
                        _activityList.value.find { it.id == savedState.selectedActivityId }
                }
            } else if (savedState.timerStartTime > 0) {
                val elapsedTime = ((System.currentTimeMillis() - savedState.timerStartTime) / 1000).toInt()

                if (savedState.stage == AppStage.EARN_TIME.name) {
                    val activity = _activityList.value.find { it.id == savedState.selectedActivityId }
                    _selectedActivity.value = activity
                    baseEarnedSeconds = savedState.baseEarnedSeconds
                    val currentActivityElapsed = savedState.remainingSeconds + elapsedTime
                    _timerState.value = TimerState(
                        isRunning = false,
                        remainingSeconds = currentActivityElapsed,
                        totalSeconds = 0
                    )
                    _earnedSeconds.value =
                        baseEarnedSeconds + (currentActivityElapsed * (activity?.timeRatio ?: 1f)).toInt()
                } else if (savedState.stage == AppStage.HOMEWORK_TIMER.name) {
                    val remaining = savedState.remainingSeconds - elapsedTime
                    _earnedSeconds.value = savedState.earnedSeconds
                    if (remaining > 0) {
                        _timerState.value = TimerState(
                            isRunning = false,
                            remainingSeconds = remaining,
                            totalSeconds = if (savedState.earnedSeconds > 0) savedState.earnedSeconds else savedState.totalSeconds
                        )
                    } else {
                        _stage.value = AppStage.CHECK_HOMEWORK
                        _showTimeUpDialog.value = true
                    }
                } else {
                    val remaining = savedState.remainingSeconds - elapsedTime
                    if (remaining > 0) {
                        _timerState.value = TimerState(
                            isRunning = false,
                            remainingSeconds = remaining,
                            totalSeconds = savedState.totalSeconds
                        )
                        _earnedSeconds.value = savedState.earnedSeconds
                    } else {
                        _stage.value = AppStage.CHECK_HOMEWORK
                        _showTimeUpDialog.value = true
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("HwMainViewModel", "Error restoring timer state", e)
            _stage.value = AppStage.INITIAL_TIMER
            _timerState.value = TimerState()
        }
    }

    fun startInitialTimer() {
        val totalSeconds = _settings.value.initialHomeworkMinutes * 60
        timerStartTime = System.currentTimeMillis()
        _timerState.value = TimerState(
            isRunning = true,
            remainingSeconds = totalSeconds,
            totalSeconds = totalSeconds
        )
        startForegroundTimer(AppStage.INITIAL_TIMER, totalSeconds, isEarnMode = false, timeRatio = 1f)
        saveTimerState()
    }

    fun startHomeworkTimer() {
        val totalSeconds = if (_timerState.value.totalSeconds > 0) {
            _timerState.value.totalSeconds
        } else {
            _earnedSeconds.value
        }
        if (totalSeconds <= 0) return
        timerStartTime = System.currentTimeMillis()
        _timerState.value = TimerState(
            isRunning = true,
            remainingSeconds = totalSeconds,
            totalSeconds = totalSeconds
        )
        startForegroundTimer(AppStage.HOMEWORK_TIMER, totalSeconds, isEarnMode = false, timeRatio = 1f)
        saveTimerState()
        _earnedSeconds.value = 0
    }

    fun startEarningTimer(activity: ActivityItem) {
        val previousActivity = _selectedActivity.value
        _selectedActivity.value = activity
        timerStartTime = System.currentTimeMillis()

        if (previousActivity == null || previousActivity.id != activity.id) {
            speak("开始${activity.name}")
        }

        baseEarnedSeconds = _earnedSeconds.value
        _timerState.value = TimerState(isRunning = true, remainingSeconds = 0, totalSeconds = 0)
        startForegroundTimer(AppStage.EARN_TIME, 0, isEarnMode = true, timeRatio = activity.timeRatio)
        saveTimerState()
    }

    private fun startForegroundTimer(
        stage: AppStage,
        remainingSeconds: Int,
        isEarnMode: Boolean,
        timeRatio: Float
    ) {
        timerJob?.cancel()

        val intent = Intent(context, TimerService::class.java).apply {
            action = TimerService.ACTION_START
            putExtra(TimerService.EXTRA_STAGE, stage.name)
            putExtra(TimerService.EXTRA_REMAINING_SECONDS, remainingSeconds)
            putExtra(TimerService.EXTRA_TOTAL_SECONDS, remainingSeconds)
            putExtra(TimerService.EXTRA_IS_EARN_MODE, isEarnMode)
            putExtra(TimerService.EXTRA_TIME_RATIO, timeRatio)
            putExtra(TimerService.EXTRA_EARNED_SECONDS, _earnedSeconds.value)
            putExtra(TimerService.EXTRA_IS_TEST_MODE, _settings.value.isTestMode)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }

        startSyncFromService()
    }

    private fun onTimerComplete() {
        _timerState.update { it.copy(isRunning = false, remainingSeconds = 0) }
        _showTimeUpDialog.value = true
        onTimerFinished?.invoke()
        saveTimerState()
        stopForegroundService()
    }

    fun pauseTimer() {
        syncJob?.cancel()
        _timerState.update { it.copy(isRunning = false) }
        val intent = Intent(context, TimerService::class.java).apply {
            action = TimerService.ACTION_PAUSE
        }
        context.startService(intent)
        saveTimerState()
    }

    fun resumeTimer() {
        if (_timerState.value.remainingSeconds > 0 || _stage.value == AppStage.EARN_TIME) {
            _timerState.update { it.copy(isRunning = true) }
            timerStartTime = System.currentTimeMillis()
            val intent = Intent(context, TimerService::class.java).apply {
                action = TimerService.ACTION_RESUME
            }
            context.startService(intent)
            startSyncFromService()
            saveTimerState()
        }
    }

    fun stopEarning() {
        timerJob?.cancel()
        _timerState.update { it.copy(isRunning = false) }
        _selectedActivity.value = null
        stopForegroundService()
        saveTimerState()
    }

    private fun stopForegroundService() {
        val intent = Intent(context, TimerService::class.java).apply {
            action = TimerService.ACTION_STOP
        }
        context.startService(intent)
    }

    fun dismissTimeUpDialog() {
        _showTimeUpDialog.value = false
    }

    fun homeworkNotCompleted() {
        _stage.value = AppStage.EARN_TIME
        _earnedSeconds.value = 0
        baseEarnedSeconds = 0
        _timerState.value = TimerState()
        saveTimerState()
    }

    fun homeworkCompleted() {
        _stage.value = AppStage.COMPLETED
        timerJob?.cancel()
        _timerState.update { it.copy(isRunning = false) }
        stopForegroundService()
        viewModelScope.launch { dataStore.clearTimerState() }
    }

    fun startHomeworkWithEarnedTime() {
        if (_earnedSeconds.value >= _settings.value.targetEarnMinutes * 60) {
            _stage.value = AppStage.HOMEWORK_TIMER
            timerJob?.cancel()
            stopForegroundService()
            val totalSeconds = _earnedSeconds.value
            _timerState.value = TimerState(
                isRunning = false,
                remainingSeconds = totalSeconds,
                totalSeconds = totalSeconds
            )
            saveTimerState()
        }
    }

    fun canStartHomework(): Boolean {
        return _earnedSeconds.value >= _settings.value.targetEarnMinutes * 60
    }

    fun timeUp() {
        _stage.value = AppStage.CHECK_HOMEWORK
        timerJob?.cancel()
        _timerState.update { it.copy(isRunning = false) }
        stopForegroundService()
        viewModelScope.launch { dataStore.clearTimerState() }
    }

    fun restart() {
        timerJob?.cancel()
        _stage.value = AppStage.INITIAL_TIMER
        _timerState.value = TimerState()
        _earnedSeconds.value = 0
        baseEarnedSeconds = 0
        _selectedActivity.value = null
        _showTimeUpDialog.value = false
        stopForegroundService()
        viewModelScope.launch { dataStore.clearTimerState() }
    }

    private fun saveTimerState() {
        viewModelScope.launch {
            val state = TimerStateData(
                stage = _stage.value.name,
                remainingSeconds = _timerState.value.remainingSeconds,
                totalSeconds = _timerState.value.totalSeconds,
                earnedSeconds = _earnedSeconds.value,
                baseEarnedSeconds = baseEarnedSeconds,
                timerStartTime = if (_timerState.value.isRunning) timerStartTime else 0,
                pausedRemainingSeconds = if (!_timerState.value.isRunning) _timerState.value.remainingSeconds else 0,
                isPaused = !_timerState.value.isRunning,
                selectedActivityId = _selectedActivity.value?.id
            )
            dataStore.saveTimerState(state)
        }
    }

    fun updateSettings(newSettings: AppSettings) {
        _settings.value = newSettings
        viewModelScope.launch { dataStore.saveSettings(newSettings) }
    }

    fun setInitialHomeworkMinutes(minutes: Int) {
        _settings.update { it.copy(initialHomeworkMinutes = minutes) }
        viewModelScope.launch { dataStore.saveSettings(_settings.value) }
    }

    fun setTargetEarnMinutes(minutes: Int) {
        _settings.update { it.copy(targetEarnMinutes = minutes) }
        viewModelScope.launch { dataStore.saveSettings(_settings.value) }
    }

    fun addActivity(name: String, timeRatio: Float, description: String = "") {
        val newActivity = ActivityItem(name = name, timeRatio = timeRatio, description = description)
        _activityList.update { it + newActivity }
        viewModelScope.launch { dataStore.saveActivities(_activityList.value) }
    }

    fun updateActivity(activity: ActivityItem) {
        _activityList.update { list -> list.map { if (it.id == activity.id) activity else it } }
        viewModelScope.launch { dataStore.saveActivities(_activityList.value) }
    }

    fun deleteActivity(id: String) {
        _activityList.update { it.filter { item -> item.id != id } }
        viewModelScope.launch { dataStore.saveActivities(_activityList.value) }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        syncJob?.cancel()
        textToSpeech?.shutdown()
        textToSpeech = null
        try {
            context.unregisterReceiver(timerFinishedReceiver)
        } catch (e: Exception) {
            Log.e("HwMainViewModel", "Error unregistering receiver", e)
        }
        try {
            if (isServiceBound) {
                context.unbindService(serviceConnection)
                isServiceBound = false
            }
        } catch (e: Exception) {
            Log.e("HwMainViewModel", "Error unbinding service", e)
        }
    }

    private fun syncFromService() {
        timerService?.let { service ->
            if (service.isTimerRunning()) {
                _timerState.update {
                    it.copy(
                        isRunning = true,
                        remainingSeconds = service.getRemainingSeconds(),
                        totalSeconds = if (service.getTotalSeconds() > 0) service.getTotalSeconds() else it.totalSeconds
                    )
                }
                if (service.isEarnMode()) {
                    _earnedSeconds.value = service.getEarnedSeconds()
                    baseEarnedSeconds = service.getBaseEarnedSeconds()
                }
                startSyncFromService()
            }
        }
    }

    private fun startSyncFromService() {
        syncJob?.cancel()
        syncJob = viewModelScope.launch {
            var lastRemainingSeconds = -1
            var finishDetected = false
            val syncInterval = if (_settings.value.isTestMode) 50L else 500L

            while (isActive) {
                if (!isServiceBound) {
                    delay(syncInterval); continue
                }
                val service = timerService
                if (service == null) {
                    delay(syncInterval); continue
                }

                val currentRemaining = service.getRemainingSeconds()
                val isServiceRunning = service.isTimerRunning()

                if (isServiceRunning) {
                    _timerState.update { it.copy(isRunning = true, remainingSeconds = currentRemaining) }
                    lastRemainingSeconds = currentRemaining
                    if (service.isEarnMode()) {
                        _earnedSeconds.value = service.getEarnedSeconds()
                    }
                    saveTimerState()
                } else if (!finishDetected && lastRemainingSeconds >= 0 && _stage.value != AppStage.EARN_TIME) {
                    finishDetected = true
                    _timerState.update { it.copy(isRunning = false, remainingSeconds = 0) }
                    onTimerComplete()
                    break
                }
                delay(syncInterval)
            }
        }
    }
}
