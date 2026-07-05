package com.niu.protect.mm

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.os.Process
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.niu.protect.accessibility.OpenAccessibilitySettingHelper

/**
 * 管控自检：检测家长管控生效所需的关键权限/开关。
 *
 * 这些在国产 ROM 上都需用户手动授予，缺一项对应功能就静默失效：
 *  - 无障碍服务：应用拦截的核心（关掉=管控失效，头号绕过手段）
 *  - 使用情况访问：使用时长统计
 *  - 读取应用列表：家长端能看到孩子已装应用
 *  - 电池不优化：后台/息屏保活，避免被系统冻结
 *  - 自启动：重启后自动恢复管控（无标准检测 API，仅提供入口）
 */
object MmSetupCheck {

    private const val ACCESSIBILITY_SERVICE =
        "com.niu.protect.accessibility.StatusUseAccessibilityService"

    /**
     * @param checkable 能否自动检测是否已开启（自启动无法检测，checkable=false）
     * @param granted   已开启（checkable=false 时恒为 false，仅作引导）
     */
    data class Item(
        val key: String, val title: String, val desc: String,
        val intent: Intent, val granted: Boolean, val checkable: Boolean = true
    )

    fun isAccessibilityOn(context: Context): Boolean =
        OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(context, ACCESSIBILITY_SERVICE)

    fun hasUsageAccess(context: Context): Boolean = try {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        appOps.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName
        ) == AppOpsManager.MODE_ALLOWED
    } catch (e: Exception) {
        false
    }

    fun hasAppListPerm(context: Context): Boolean = ContextCompat.checkSelfPermission(
        context, "com.android.permission.GET_INSTALLED_APPS"
    ) == PackageManager.PERMISSION_GRANTED

    fun isBatteryUnrestricted(context: Context): Boolean = try {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        pm.isIgnoringBatteryOptimizations(context.packageName)
    } catch (e: Exception) {
        true
    }

    /** 全部检查项（含已开启状态），供引导页展示 */
    fun items(context: Context): List<Item> {
        val pkg = "package:${context.packageName}"
        return listOf(
            Item("acc", "无障碍服务", "应用拦截的核心，关闭后管控立即失效",
                Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), isAccessibilityOn(context)),
            Item("battery", "后台运行（关闭电池优化）", "避免息屏/后台被系统冻结，管控才能一直生效",
                batteryIntent(context, pkg), isBatteryUnrestricted(context)),
            Item("autostart", "自启动", "重启手机后自动恢复管控（请在设置中允许本应用自启动）",
                autostartIntent(context, pkg), false, checkable = false),
            Item("usage", "使用情况访问", "统计孩子每个应用的使用时长",
                Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), hasUsageAccess(context)),
            Item("applist", "读取应用列表", "家长端才能看到并管控已装应用",
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(pkg)), hasAppListPerm(context))
        )
    }

    /** 当前缺失的、可检测的关键项（供 B1 通知使用） */
    fun missing(context: Context): List<Item> =
        items(context).filter { it.checkable && !it.granted }

    private fun batteryIntent(context: Context, pkg: String): Intent =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse(pkg))
        else Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(pkg))

    /** 自启动入口：按机型试已知深链，都不可用则回退到应用详情页 */
    private fun autostartIntent(context: Context, pkg: String): Intent {
        val candidates = listOf(
            // ColorOS / OPPO
            "com.coloros.safecenter" to "com.coloros.safecenter.permission.startup.StartupAppListActivity",
            "com.coloros.safecenter" to "com.coloros.safecenter.startupapp.StartupAppListActivity",
            "com.oppo.safe" to "com.oppo.safe.permission.startup.StartupAppListActivity",
            // vivo
            "com.vivo.permissionmanager" to "com.vivo.permissionmanager.activity.BgStartUpManagerActivity",
            "com.iqoo.secure" to "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity",
            // 小米
            "com.miui.securitycenter" to "com.miui.permcenter.autostart.AutoStartManagementActivity",
            // 华为
            "com.huawei.systemmanager" to "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity",
            "com.huawei.systemmanager" to "com.huawei.systemmanager.optimize.process.ProtectActivity"
        )
        val pm = context.packageManager
        for ((p, c) in candidates) {
            val i = Intent().setClassName(p, c)
            if (i.resolveActivity(pm) != null) return i
        }
        return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(pkg))
    }
}
