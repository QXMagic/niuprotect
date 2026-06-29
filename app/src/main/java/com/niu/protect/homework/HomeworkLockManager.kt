package com.niu.protect.homework

import android.content.Context
import android.content.Intent
import com.niu.protect.homework.model.AppStage
import java.util.Locale

/**
 * 作业时间锁 —— “手机控制 + 作业时间控制” 的桥接器。
 *
 * 作业模块（[HomeworkActivity] / MainViewModel）在阶段变化时调用 [updateStage]，
 * 无障碍服务 StatusUseAccessibilityService 在每个窗口事件里调用 [shouldBlock] 做强制拦截。
 *
 * 为保证无障碍服务能“同步、低成本”读取状态：
 *  - 用 volatile 内存缓存（同进程共享，无障碍服务与作业模块都在主进程）；
 *  - 同时镜像到 SharedPreferences，进程重启后仍可恢复锁定状态。
 */
object HomeworkLockManager {

    private const val PREF = "homework_lock"
    private const val KEY_ENABLED = "hard_lock_enabled"
    private const val KEY_STAGE = "current_stage"
    private const val KEY_PIN = "parent_pin"
    private const val DEFAULT_PIN = "1234"

    /** 作业“欠着/进行中”的阶段——这些阶段下手机只允许使用作业App及必要应用 */
    private val LOCKED_STAGES = setOf(
        AppStage.CHECK_HOMEWORK.name,
        AppStage.EARN_TIME.name,
        AppStage.HOMEWORK_TIMER.name
    )

    @Volatile
    private var cachedEnabled: Boolean? = null

    @Volatile
    private var cachedStage: String? = null

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    // ---------------- 主开关（家长配置，受密码保护） ----------------

    fun isEnabled(context: Context): Boolean {
        cachedEnabled?.let { return it }
        val value = prefs(context).getBoolean(KEY_ENABLED, false)
        cachedEnabled = value
        return value
    }

    fun setEnabled(context: Context, enabled: Boolean) {
        cachedEnabled = enabled
        prefs(context).edit().putBoolean(KEY_ENABLED, enabled).apply()
    }

    // ---------------- 阶段同步 ----------------

    fun updateStage(context: Context, stageName: String) {
        if (stageName == cachedStage) return
        cachedStage = stageName
        prefs(context).edit().putString(KEY_STAGE, stageName).apply()
    }

    fun currentStage(context: Context): String {
        cachedStage?.let { return it }
        val value = prefs(context).getString(KEY_STAGE, AppStage.INITIAL_TIMER.name)
            ?: AppStage.INITIAL_TIMER.name
        cachedStage = value
        return value
    }

    // ---------------- 家长密码 ----------------

    fun getPin(context: Context): String =
        prefs(context).getString(KEY_PIN, DEFAULT_PIN) ?: DEFAULT_PIN

    fun checkPin(context: Context, input: String): Boolean = getPin(context) == input

    fun setPin(context: Context, pin: String) {
        prefs(context).edit().putString(KEY_PIN, pin).apply()
    }

    // ---------------- 锁定判定 ----------------

    /** 当前是否处于“硬锁定”状态：开关打开 且 作业未完成 */
    fun isLockActive(context: Context): Boolean {
        if (!isEnabled(context)) return false
        return LOCKED_STAGES.contains(currentStage(context))
    }

    /**
     * 指定包名是否应当被拦截（踢出）。
     * 锁定期间，仅允许：本App（作业模块）、桌面launcher、输入法、电话/联系人、系统UI。
     */
    fun shouldBlock(context: Context, packageName: String?): Boolean {
        if (packageName == null) return false
        if (!isLockActive(context)) return false
        return !isAllowed(context, packageName)
    }

    private fun isAllowed(context: Context, packageNameRaw: String): Boolean {
        val packageName = packageNameRaw.lowercase(Locale.getDefault())
        // 本App（作业模块与管控App同包）
        if (packageNameRaw == context.packageName) return true
        // 桌面
        if (packageName.contains("launcher")) return true
        // 输入法
        if (packageName.contains("input") ||
            packageName.contains("ime") ||
            packageName.contains("sogou") ||
            packageName.contains("keyboard")
        ) return true
        // 电话 / 拨号 / 联系人（保证紧急通讯）
        if (packageName.contains("dialer") ||
            packageName.contains("incallui") ||
            packageName.contains("contacts") ||
            packageName == "com.android.phone"
        ) return true
        // 系统UI / 系统进程
        if (packageName.contains("systemui") || packageName == "android") return true
        return false
    }

    /** 把作业模块拉到前台（供通知/拦截后引导使用） */
    fun openHomework(context: Context) {
        try {
            val intent = Intent(context, HomeworkActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
