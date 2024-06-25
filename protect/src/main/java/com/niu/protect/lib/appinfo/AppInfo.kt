package com.niu.protect.lib.appinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.text.TextUtils
import com.niu.protect.core.Constants

object AppInfo {
    var appName = ""
    @JvmStatic
    @Synchronized
    fun getAppName(context: Context): String {
        var str: String
        synchronized(AppInfo::class.java) {
            try {
                val packageManager = context.packageManager
                if (TextUtils.isEmpty(appName)) {
                    appName = packageManager.getApplicationLabel(context.applicationInfo).toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                appName = Constants.APP_NAME
            }
            str = appName
        }
        return str
    }

    @Synchronized
    fun getVersionName(context: Context): String? {
        var str: String
        synchronized(AppInfo::class.java) {
            str = try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
        return str
    }

    @Synchronized
    fun getVersionCode(context: Context): Int {
        var i: Int
        synchronized(AppInfo::class.java) {
            i = try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionCode
            } catch (e: Exception) {
                e.printStackTrace()
                return 0
            }
        }
        return i
    }

    @Synchronized
    fun getPackageName(context: Context): String? {
        var str: String
        synchronized(AppInfo::class.java) {
            str = try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.packageName
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
        return str
    }

    @Synchronized
    fun getBitmap(context: Context): Bitmap {
        var applicationInfo: ApplicationInfo?
        var bm: Bitmap
        synchronized(AppInfo::class.java) {
            var packageManager: PackageManager? = null
            try {
                packageManager = context.applicationContext.packageManager
                applicationInfo = packageManager.getApplicationInfo(context.packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                applicationInfo = null
            }
            val d = packageManager!!.getApplicationIcon(applicationInfo!!)
            val bd = d as BitmapDrawable
            bm = bd.bitmap
        }
        return bm
    }
}