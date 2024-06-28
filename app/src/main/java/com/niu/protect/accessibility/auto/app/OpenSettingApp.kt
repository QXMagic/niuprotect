package com.niu.protect.accessibility.auto.app

import android.app.Activity
import android.app.PendingIntent
import android.app.PendingIntent.CanceledException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import com.niu.protect.BabyApplication.Companion.instance
import com.niu.protect.accessibility.auto.device.CustomOSUtils
import com.niu.protect.ui.login.SplashActivity
import com.niu.protect.ui.setting.PermissionCollectActivity
import java.util.Locale

object OpenSettingApp {
    @JvmStatic
    fun gotoSetting(context: Activity, type: Int) {
        when (type) {
            1 -> {
                gotoSystemSetting(context)
                return
            }

            2 -> {
                getAppDetailSettingIntent(context)
                return
            }

            3 -> {
                gotoAppUseAccessSetting(context)
                return
            }

            4 -> {
                openNotification(context)
                return
            }

            5 -> {
                requestSettingCanDrawOverlays(context)
                return
            }

            6 -> {
                openOtherApp(context, "com.iqoo.secure", "com.iqoo.secure.MainActivity")
                return
            }

            7 -> {
                hwAutoSetting(context)
                return
            }

            8 -> {
                openOtherApp(
                    context,
                    "com.coloros.phonemanager",
                    "com.coloros.phonemanager.FakeActivity"
                )
                return
            }

            9 -> {
                vivoGotoAutoStart(context)
                return
            }

            10 -> {
                hwAutoSetting(context)
                return
            }

            else -> return
        }
    }

    private fun hwAutoSetting(context: Activity) {
        val systemOs = CustomOSUtils.getCustomOS(Build.BRAND)
        val brand = Build.BRAND
        if (systemOs.uppercase(Locale.getDefault()) == "EMUI") {
            openOtherApp(
                context,
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.mainscreen.MainScreenActivity"
            )
        } else if (brand.uppercase(Locale.getDefault()) == "HUAWEI") {
            openHONGMeng(context)
        } else {
            openHONGMeng(context)
        }
    }

    fun openHONGMeng(context: Context) {
        val AUTO_START_HUAWEI = arrayOf(
            Intent().setComponent(
                ComponentName(
                    "com.huawei.systemmanager",
                    "com.huawei.systemmanager.mainscreen.MainScreenActivity"
                )
            ),
            Intent().setComponent(
                ComponentName(
                    "com.hihonor.systemmanager",
                    "com.huawei.systemmanager.mainscreen.MainScreenActivity"
                )
            ),
            Intent().setComponent(
                ComponentName(
                    "com.hihonor.systemmanager",
                    "com.hihonor.systemmanager.mainscreen.MainScreenActivity"
                )
            )
        )
        for (intent in AUTO_START_HUAWEI) {
            if (context.packageManager.resolveActivity(intent, 65536) != null) {
                try {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addCategory("android.intent.category.DEFAULT")
                    context.startActivity(intent)
                    return
                } catch (e: Exception) {
                    Log.d("TAG", "OPPO - Exception: $e")
                }
            }
        }
    }

    fun openOtherApp(context: Activity, packageName: String?, className: String?) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addCategory("android.intent.category.DEFAULT")
        intent.setClassName(packageName!!, className!!)
        context.startActivityForResult(intent, 6)
    }

    fun openAppPage(context: Activity, packagename: String?) {
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

    fun gotoSystemSetting(context: Activity) {
        val intent = Intent("android.settings.SETTINGS")
        context.startActivity(intent)
    }

    fun getAppDetailSettingIntent(context: Activity) {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts("package", context.packageName, null)
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.action = "android.intent.action.VIEW"
            localIntent.setClassName(
                "com.android.settings",
                "com.android.settings.InstalledAppDetails"
            )
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.packageName)
        }
        context.startActivity(localIntent)
    }

    fun gotoAppUseAccessSetting(context: Activity) {
        val intent = Intent("android.settings.USAGE_ACCESS_SETTINGS")
        context.startActivityForResult(intent, 3)
    }

    fun openNotification(context: Activity) {
        try {
            val localIntent = Intent()
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            localIntent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            if (Build.VERSION.SDK_INT >= 26) {
                localIntent.putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
                localIntent.putExtra(
                    "android.provider.extra.CHANNEL_ID",
                    context.applicationInfo.uid
                )
            } else if (Build.VERSION.SDK_INT >= 21) {
                localIntent.putExtra("app_package", context.packageName)
                localIntent.putExtra("app_uid", context.applicationInfo.uid)
            }
            context.startActivityForResult(localIntent, 4)
        } catch (e: Exception) {
            getAppDetailSettingIntent(context)
        }
    }

    @JvmStatic
    fun backMineApp(context: Context?) {
        try {
            Thread.sleep(300L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val secondIntent = Intent(context, PermissionCollectActivity::class.java)
        secondIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val pendingIntent = PendingIntent.getActivity(context, 0, secondIntent,
            PendingIntent.FLAG_IMMUTABLE)
        try {
            pendingIntent.send()
        } catch (e2: CanceledException) {
            e2.printStackTrace()
        }
    }

    fun requestSettingCanDrawOverlays(context: Activity) {
        val sdkInt = Build.VERSION.SDK_INT
        if (sdkInt >= 26) {
            context.startActivityForResult(
                Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION"),
                0
            )
        } else if (sdkInt >= 23) {
            val intent = Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION")
            intent.data = Uri.parse("package:" + context.packageName)
            context.startActivityForResult(intent, 0)
        }
    }

    @JvmStatic
    fun hideIcon(context: Context) {
        val packageManager = instance!!.packageManager
        context.packageName
        val componentName = ComponentName(instance!!, SplashActivity::class.java)
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
    }

    @JvmStatic
    fun showIcon(context: Context?) {
        val packageManager = instance!!.packageManager
        val componentName = ComponentName(instance!!, SplashActivity::class.java)
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
    }

    @JvmStatic
    fun checkShowIcon(context: Context, showStatus: Int) {
        if (showStatus == 1) {
            showIcon(context)
        } else if (showStatus == 2) {
            hideIcon(context)
        }
    }

    fun vivoGotoAutoStart(context: Context) {
        val intent = Intent()
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val componentName =
                ComponentName.unflattenFromString("com.vivo.permissionmanager/.activity.BgStartUpManagerActivity")
            intent.component = componentName
            context.startActivity(intent)
        } catch (e: Exception) {
            context.startActivity(Intent("android.settings.SETTINGS"))
        }
    }
}