package com.niu.protect.homework.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.niu.protect.homework.model.ActivityItem
import com.niu.protect.homework.model.AppSettings
import com.niu.protect.homework.model.AppStage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "homework_timer")

/**
 * 计时器持久化状态（快照），用于进程被杀后恢复倒计时。
 */
data class TimerStateData(
    val stage: String = AppStage.INITIAL_TIMER.name,
    val remainingSeconds: Int = 0,
    val totalSeconds: Int = 0,
    val earnedSeconds: Int = 0,
    val baseEarnedSeconds: Int = 0,
    val timerStartTime: Long = 0,
    val pausedRemainingSeconds: Int = 0,
    val isPaused: Boolean = false,
    val selectedActivityId: String? = null
)

class TimerDataStore(private val context: Context) {

    private val gson = Gson()

    companion object {
        private val STAGE_KEY = stringPreferencesKey("stage")
        private val REMAINING_SECONDS_KEY = intPreferencesKey("remaining_seconds")
        private val TOTAL_SECONDS_KEY = intPreferencesKey("total_seconds")
        private val EARNED_SECONDS_KEY = intPreferencesKey("earned_seconds")
        private val BASE_EARNED_SECONDS_KEY = intPreferencesKey("base_earned_seconds")
        private val TIMER_START_TIME_KEY = longPreferencesKey("timer_start_time")
        private val PAUSED_REMAINING_KEY = intPreferencesKey("paused_remaining")
        private val IS_PAUSED_KEY = stringPreferencesKey("is_paused")
        private val SELECTED_ACTIVITY_ID_KEY = stringPreferencesKey("selected_activity_id")

        // 设置相关
        private val INITIAL_HOMEWORK_MINUTES_KEY = intPreferencesKey("initial_homework_minutes")
        private val TARGET_EARN_MINUTES_KEY = intPreferencesKey("target_earn_minutes")
        private val IS_TEST_MODE_KEY = stringPreferencesKey("is_test_mode")

        // 活动列表
        private val ACTIVITIES_KEY = stringPreferencesKey("activities")
    }

    suspend fun saveTimerState(state: TimerStateData) {
        context.dataStore.edit { prefs ->
            prefs[STAGE_KEY] = state.stage
            prefs[REMAINING_SECONDS_KEY] = state.remainingSeconds
            prefs[TOTAL_SECONDS_KEY] = state.totalSeconds
            prefs[EARNED_SECONDS_KEY] = state.earnedSeconds
            prefs[BASE_EARNED_SECONDS_KEY] = state.baseEarnedSeconds
            prefs[TIMER_START_TIME_KEY] = state.timerStartTime
            prefs[PAUSED_REMAINING_KEY] = state.pausedRemainingSeconds
            prefs[IS_PAUSED_KEY] = state.isPaused.toString()
            prefs[SELECTED_ACTIVITY_ID_KEY] = state.selectedActivityId ?: ""
        }
    }

    fun getTimerState(): Flow<TimerStateData> {
        return context.dataStore.data.map { prefs ->
            TimerStateData(
                stage = prefs[STAGE_KEY] ?: AppStage.INITIAL_TIMER.name,
                remainingSeconds = prefs[REMAINING_SECONDS_KEY] ?: 0,
                totalSeconds = prefs[TOTAL_SECONDS_KEY] ?: 0,
                earnedSeconds = prefs[EARNED_SECONDS_KEY] ?: 0,
                baseEarnedSeconds = prefs[BASE_EARNED_SECONDS_KEY] ?: 0,
                timerStartTime = prefs[TIMER_START_TIME_KEY] ?: 0,
                pausedRemainingSeconds = prefs[PAUSED_REMAINING_KEY] ?: 0,
                isPaused = prefs[IS_PAUSED_KEY] == "true",
                selectedActivityId = prefs[SELECTED_ACTIVITY_ID_KEY]?.takeIf { it.isNotEmpty() }
            )
        }
    }

    suspend fun saveSettings(settings: AppSettings) {
        context.dataStore.edit { prefs ->
            prefs[INITIAL_HOMEWORK_MINUTES_KEY] = settings.initialHomeworkMinutes
            prefs[TARGET_EARN_MINUTES_KEY] = settings.targetEarnMinutes
            prefs[IS_TEST_MODE_KEY] = settings.isTestMode.toString()
        }
    }

    fun getSettings(): Flow<AppSettings> {
        return context.dataStore.data.map { prefs ->
            AppSettings(
                initialHomeworkMinutes = prefs[INITIAL_HOMEWORK_MINUTES_KEY] ?: 30,
                targetEarnMinutes = prefs[TARGET_EARN_MINUTES_KEY] ?: 30,
                isTestMode = prefs[IS_TEST_MODE_KEY] == "true"
            )
        }
    }

    suspend fun saveActivities(activities: List<ActivityItem>) {
        val json = gson.toJson(activities)
        context.dataStore.edit { prefs ->
            prefs[ACTIVITIES_KEY] = json
        }
    }

    fun getActivities(): Flow<List<ActivityItem>> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[ACTIVITIES_KEY]
            if (json.isNullOrEmpty()) {
                getDefaultActivities()
            } else {
                try {
                    val type = object : TypeToken<List<ActivityItem>>() {}.type
                    gson.fromJson<List<ActivityItem>>(json, type) ?: getDefaultActivities()
                } catch (e: Exception) {
                    getDefaultActivities()
                }
            }
        }
    }

    private fun getDefaultActivities(): List<ActivityItem> {
        return listOf(
            ActivityItem(name = "罚站", timeRatio = 0.5f, description = "靠墙罚站，保持姿势"),
            ActivityItem(name = "平板撑", timeRatio = 2f, description = "做平板支撑运动"),
            ActivityItem(name = "蹲起", timeRatio = 1f, description = "做蹲起运动"),
            ActivityItem(name = "马步", timeRatio = 1f, description = "蹲马步"),
            ActivityItem(name = "跳绳", timeRatio = 1.5f, description = "跳绳运动"),
            ActivityItem(name = "俯卧撑", timeRatio = 2f, description = "做俯卧撑运动")
        )
    }

    suspend fun clearTimerState() {
        context.dataStore.edit { prefs ->
            prefs[STAGE_KEY] = AppStage.INITIAL_TIMER.name
            prefs[REMAINING_SECONDS_KEY] = 0
            prefs[TOTAL_SECONDS_KEY] = 0
            prefs[EARNED_SECONDS_KEY] = 0
            prefs[BASE_EARNED_SECONDS_KEY] = 0
            prefs[TIMER_START_TIME_KEY] = 0
            prefs[PAUSED_REMAINING_KEY] = 0
            prefs[IS_PAUSED_KEY] = "false"
            prefs[SELECTED_ACTIVITY_ID_KEY] = ""
        }
    }
}
