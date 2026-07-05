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
 * 管控自检：检测家长管控生效所需的关键权限/开关，缺失项引导去开启。
 *
 * 这些在国产 ROM 上都需用户手动授予，缺一项对应功能就静默失效：
 *  - 无障碍服务：应用拦截的核心（关掉=管控失效，头号绕过手段）
 *  - 使用情况访问：使用时长统计
 *  - 读取应用列表：家长端能看到孩子已装应用
 *  - 后台保活：App 被杀后管控中断
 */
object MmSetupCheck {

    private const val ACCESSIBILITY_SERVICE =
        "com.niu.protect.accessibility.StatusUseAccessibilityService"

    data class Item(val key: String, val title: String, val desc: String, val intent: Intent)

    /** 无障碍服务是否在运行 */
    fun isAccessibilityOn(context: Context): Boolean =
        OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(context, ACCESSIBILITY_SERVICE)

    /** 使用情况访问是否授予 */
    fun hasUsageAccess(context: Context): Boolean = try {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        appOps.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName
        ) == AppOpsManager.MODE_ALLOWED
    } catch (e: Exception) {
        false
    }

    /** 读取应用列表权限是否授予 */
    fun hasAppListPerm(context: Context): Boolean = ContextCompat.checkSelfPermission(
        context, "com.android.permission.GET_INSTALLED_APPS"
    ) == PackageManager.PERMISSION_GRANTED

    /** 是否已加入电池优化白名单（后台保活） */
    fun isBatteryUnrestricted(context: Context): Boolean = try {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        pm.isIgnoringBatteryOptimizations(context.packageName)
    } catch (e: Exception) {
        true
    }

    /** 返回当前缺失的关键项（按重要度排序） */
    fun missing(context: Context): List<Item> {
        val pkg = "package:${context.packageName}"
        val list = mutableListOf<Item>()
        if (!isAccessibilityOn(context)) {
            list.add(Item("acc", "无障碍服务", "应用拦截的核心，关闭后管控立即失效",
                Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)))
        }
        if (!hasUsageAccess(context)) {
            list.add(Item("usage", "使用情况访问", "统计孩子每个应用的使用时长",
                Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)))
        }
        if (!hasAppListPerm(context)) {
            list.add(Item("applist", "读取应用列表", "家长端才能看到并管控已装应用",
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(pkg))))
        }
        if (!isBatteryUnrestricted(context)) {
            val bi = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse(pkg))
            else Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(pkg))
            list.add(Item("battery", "后台运行/关闭电池优化", "避免被系统杀死导致管控中断", bi))
        }
        return list
    }
}
