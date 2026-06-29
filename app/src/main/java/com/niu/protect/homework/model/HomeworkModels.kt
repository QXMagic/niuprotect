package com.niu.protect.homework.model

import java.util.UUID

/**
 * 赚取时间的活动项目
 * @param name 活动名称（如：罚站、平板撑、蹲起）
 * @param timeRatio 时间比例（如：0.5 表示做1分钟获得0.5分钟作业时间）
 */
data class ActivityItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val timeRatio: Float,
    val description: String = ""
)

/**
 * 应用设置
 * @param initialHomeworkMinutes 初始（自由安排）时间，分钟
 * @param targetEarnMinutes 目标赚取时间，分钟
 * @param isTestMode 测试模式，倒计时加速10倍
 */
data class AppSettings(
    val initialHomeworkMinutes: Int = 30,
    val targetEarnMinutes: Int = 30,
    val isTestMode: Boolean = false
)

/**
 * 应用阶段
 */
enum class AppStage {
    /** 初始计时阶段 - 家长开始计时，有自由安排时间 */
    INITIAL_TIMER,
    /** 检查作业阶段 - 时间到了，检查是否完成作业 */
    CHECK_HOMEWORK,
    /** 赚取时间阶段 - 孩子选择活动赚取作业时间 */
    EARN_TIME,
    /** 写作业计时阶段 - 孩子用赚取的时间写作业 */
    HOMEWORK_TIMER,
    /** 完成 - 作业已完成 */
    COMPLETED
}

/**
 * 计时器状态
 */
data class TimerState(
    val isRunning: Boolean = false,
    val remainingSeconds: Int = 0,
    val totalSeconds: Int = 0
) {
    val progress: Float
        get() = if (totalSeconds > 0) remainingSeconds.toFloat() / totalSeconds else 0f

    val formattedTime: String
        get() {
            val minutes = remainingSeconds / 60
            val seconds = remainingSeconds % 60
            return String.format("%02d:%02d", minutes, seconds)
        }
}
