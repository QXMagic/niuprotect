package com.niu.protect.accessibility.auto.app

import android.app.Activity
import android.app.ActivityManager
import android.app.PendingIntent
import android.app.PendingIntent.CanceledException
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.util.Log
import androidx.fragment.app.Fragment
import com.niu.protect.BuildConfig
import com.niu.protect.accessibility.OpenAccessibilitySettingHelper.Companion.isAccessibilitySettingsOn
import com.niu.protect.accessibility.StatusUseAccessibilityService
import com.niu.protect.broadcastReceiver.DeviceReceiver
import com.niu.protect.tools.ILog
import com.niu.protect.tools.Tools
import com.niu.protect.ui.main.DesktopActivity
import com.niu.protect.ui.main.MainActivity
import com.niu.protect.ui.setting.OpenQxActivity
import com.niu.protect.ui.setting.SysSetActivity
import java.io.IOException

class AppActivityTool {
    fun showDesActivy(context: Context?) {
        val secondIntent = Intent(context, DesktopActivity::class.java)
        secondIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP) //872480768
        val pendingIntent =
            PendingIntent.getActivity(context, 0, secondIntent, PendingIntent.FLAG_IMMUTABLE)
        try {
            pendingIntent.send()
        } catch (e: CanceledException) {
            e.printStackTrace()
        }
    }

    companion object {
        private var dpm: DevicePolicyManager? = null
        private var mDeviceAdminSample: ComponentName? = null
        fun openApp(context: Context, packagename: String?) {
            var packageinfo: PackageInfo? = null
            try {
                packageinfo = context.packageManager.getPackageInfo(packagename!!, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            if (packageinfo == null) {
                return
            }
            val resolveIntent = Intent("android.intent.action.MAIN", null as Uri?)
            resolveIntent.addCategory("android.intent.category.LAUNCHER")
            resolveIntent.setPackage(packageinfo.packageName)
            val resolveinfoList = context.packageManager.queryIntentActivities(resolveIntent, 0)
            if (resolveinfoList != null && resolveinfoList.size > 0) {
                val resolveinfo = resolveinfoList.iterator().next()
                if (resolveinfo != null) {
                    val packageName = resolveinfo.activityInfo.packageName
                    val className = resolveinfo.activityInfo.name
                    val intent = Intent("android.intent.action.MAIN")
                    intent.addCategory("android.intent.category.LAUNCHER")
                    val cn2 = ComponentName(packageName, className)
                    intent.component = cn2
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    return
                }
                return
            }
            ILog.d("AppActivityTool", "没有找到")
        }

        fun openOtherApp(context: Context, packageName: String?, className: String?) {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addCategory("android.intent.category.DEFAULT")
            intent.setClassName(packageName!!, className!!)
            context.startActivity(intent)
        }

        fun openAppPage(context: Context, packagename: String?) {
            var packageinfo: PackageInfo? = null
            try {
                packageinfo = context.packageManager.getPackageInfo(packagename!!, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            if (packageinfo == null) {
                return
            }
            val resolveIntent = Intent("android.intent.action.MAIN", null as Uri?)
            resolveIntent.addCategory("android.intent.category.LAUNCHER")
            resolveIntent.setPackage(packageinfo.packageName)
            val resolveinfoList = context.packageManager.queryIntentActivities(resolveIntent, 0)
            val resolveinfo = resolveinfoList.iterator().next()
            if (resolveinfo != null) {
                val packageName = resolveinfo.activityInfo.packageName
                val className = resolveinfo.activityInfo.name
                val intent = Intent("android.intent.action.MAIN")
                intent.addCategory("android.intent.category.LAUNCHER")
                val cn2 = ComponentName(packageName, className)
                intent.component = cn2
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }

        fun openOppoSystemSetting(context: Context) {
            if (Build.MANUFACTURER == "OPPO") {
                val AUTO_START_OPPO = arrayOf(
                    Intent().setComponent(
                        ComponentName(
                            "com.coloros.safe",
                            "com.coloros.safe.permission.startup.StartupAppListActivity"
                        )
                    ),
                    Intent().setComponent(
                        ComponentName(
                            "com.coloros.safe",
                            "com.coloros.safe.permission.startupapp.StartupAppListActivity"
                        )
                    ),
                    Intent().setComponent(
                        ComponentName(
                            "com.coloros.safecenter",
                            "com.coloros.safecenter.startupapp.StartupAppListActivity"
                        )
                    ),
                    Intent().setComponent(
                        ComponentName(
                            "com.coloros.safecenter",
                            "com.coloros.safecenter.startup.StartupAppListActivity"
                        )
                    )
                )
                for (intent in AUTO_START_OPPO) {
                    if (context.packageManager.resolveActivity(
                            intent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        ) != null
                    ) {
                        try {
                            context.startActivity(intent)
                            return
                        } catch (e: Exception) {
                            Log.d("TAG", "OPPO - Exception: $e")
                        }
                    }
                }
            }
        }

        fun openLock(context: Fragment) {
            dpm =
                context.requireContext().getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            mDeviceAdminSample = ComponentName(context.requireContext(), DeviceReceiver::class.java)
            val intent = Intent("android.app.action.ADD_DEVICE_ADMIN")
            intent.putExtra("android.app.extra.DEVICE_ADMIN", mDeviceAdminSample)
            intent.putExtra("android.app.extra.ADD_EXPLANATION", "开启后就可以使用锁屏功能了...")
            context.startActivityForResult(intent, 0)
        }

        fun openNotification(fragment: Fragment) {
            val localIntent = Intent()
            if (Build.VERSION.SDK_INT >= 26) {
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                localIntent.data = Uri.fromParts("package", fragment.requireContext().packageName, null)
            } else if (Build.VERSION.SDK_INT >= 21) {
                localIntent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                localIntent.putExtra("app_package", fragment.requireContext().packageName)
                localIntent.putExtra("app_uid", fragment.requireContext().applicationInfo.uid)
            }
            fragment.startActivity(localIntent)
        }

        fun openPowerSetting(context: Context) {
            val intent = Intent("android.intent.action.MAIN")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addCategory("android.intent.category.LAUNCHER")
            val cn2 =
                ComponentName.unflattenFromString("com.android.settings/.Settings\$HighPowerApplicationsActivity")
            intent.component = cn2
            context.startActivity(intent)
        }

        fun ignoreBatteryOptimization(activity: Activity) {
            val powerManager = activity.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (Build.VERSION.SDK_INT >= 23) {
                val hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.packageName)
                ILog.d("is power", "" + hasIgnored)
                if (!hasIgnored) {
                    val intent = Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS")
                    intent.data = Uri.parse("package:" + activity.packageName)
                    activity.startActivity(intent)
                }
            }
        }

        fun power(context: Activity) {
            val powerUsageIntent = Intent("android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS")
            val resolveInfo = context.packageManager.resolveActivity(powerUsageIntent, 0)
            if (resolveInfo != null) {
                context.startActivity(powerUsageIntent)
            }
        }

        fun showNewSetActivy(context: Context?) {
            val secondIntent = Intent(context, SysSetActivity::class.java)
            secondIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent =
                PendingIntent.getActivity(context, 0, secondIntent, PendingIntent.FLAG_IMMUTABLE)
            try {
                pendingIntent.send()
            } catch (e: CanceledException) {
                e.printStackTrace()
            }
        }

        fun showHomeActivy(context: Context?) {
            val secondIntent = Intent(context, MainActivity::class.java)
            secondIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent =
                PendingIntent.getActivity(context, 0, secondIntent, PendingIntent.FLAG_IMMUTABLE)
            try {
                pendingIntent.send()
            } catch (e: CanceledException) {
                e.printStackTrace()
            }
        }

        fun showSetActivy(context: Context?) {
            val intent = Intent()
            val cn2 = ComponentName(
                BuildConfig.APPLICATION_ID,
                "com.niu.protect.ui.setting.SysSetActivity"
            )
            intent.component = cn2
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val uri = Uri.parse("com.niu.protect.ui.setting.SysSetActivity")
            intent.data = uri
            val pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            try {
                pendingIntent.send()
            } catch (e: CanceledException) {
                e.printStackTrace()
            }
        }

        fun backApp(context: Context?) {
            try {
                Thread.sleep(1000L)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val secondIntent = Intent(context, OpenQxActivity::class.java)
            secondIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent =
                PendingIntent.getActivity(context, 0, secondIntent, PendingIntent.FLAG_IMMUTABLE)
            try {
                pendingIntent.send()
            } catch (e2: CanceledException) {
                e2.printStackTrace()
            }
        }

        fun checkNeedOpenSettingPerm(context: Context) {
            if (!isAccessibilitySettingsOn(
                    context,
                    StatusUseAccessibilityService::class.java.name
                )
            ) {
                Tools.getQxSet(context)
                val intent = Intent()
                intent.setClass(context, OpenQxActivity::class.java)
                context.startActivity(intent)
            }
        }

        fun killService(context: Context) {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = manager.runningAppProcesses
            for (i in list.indices) {
                val process = list[i].processName
                ILog.d("APPtools", "kill -$process")
                if (!process.contains(BuildConfig.APPLICATION_ID)) {
                    try {
                        manager.killBackgroundProcesses(process)
                        val runtime = Runtime.getRuntime()
                        runtime.exec("kill " + list[i].pid)
                        ILog.d("APP tools", "kill -------process----$process")
                        return
                    } catch (e: IOException) {
                        e.printStackTrace()
                        ILog.d("APP tools", "kill--" + e.message)
                        return
                    }
                }
            }
        }

        fun killServiceByPackage(context: Context, packageName: String?) {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            manager.killBackgroundProcesses(packageName)
        }
    }
}