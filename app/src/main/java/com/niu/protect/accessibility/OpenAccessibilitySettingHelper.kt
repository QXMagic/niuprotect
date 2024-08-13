package com.niu.protect.accessibility

import android.R
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils.SimpleStringSplitter
import android.util.Log
import android.view.accessibility.AccessibilityManager
import com.niu.protect.Constant
import com.niu.protect.tools.ILog


class OpenAccessibilitySettingHelper {
    private fun openAccessibility(context: Context) {
        val accessibilityManager =
            context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        if (!accessibilityManager.isEnabled) {
            context.startActivity(Intent("android.settings.ACCESSIBILITY_SETTINGS"))
        }
    }

    companion object {
        private const val TAG = "OpenAccessibilitySettingHelper"
        @JvmStatic
        fun jumpToSettingPage(context: Context) {
            val intent = Intent("android.settings.ACCESSIBILITY_SETTINGS")
            context.startActivity(intent)
        }


        @JvmStatic
        fun isAccessibilitySettingsOn(context: Context?, className: String): Boolean {
            if (context == null) {
                ILog.d(Constant.TAG_DEBUG_1, "context is null ")
                return false
            }
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    ?: return false
            val runningServices = activityManager.getRunningServices(100)
            if (runningServices.size < 1) {
                ILog.d(Constant.TAG_DEBUG_1, "running service is empty")
                return false
            }
            for (i in runningServices.indices) {
                val service = runningServices[i].service
                if (service.className == className) {
                    return true
                }
            }
            ILog.d(Constant.TAG_DEBUG_1, "no service running named: $className")
            return false
        }

        @JvmStatic
        fun isAccessibilitySettingsOnByService(mContext: Context): Boolean {
            var accessibilityEnabled = 0
            val service =
                mContext.packageName + "/" + StatusUseAccessibilityService::class.java.canonicalName
            Log.i(TAG, "service:$service")
            try {
                accessibilityEnabled = Settings.Secure.getInt(
                    mContext.applicationContext.contentResolver,
                    "accessibility_enabled"
                )
                val accessibilityManager =
                    mContext.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
                val isEnabled = accessibilityManager.isEnabled
                Log.i(TAG, "accessibilityManager isEnabled:$isEnabled")
            } catch (e: SettingNotFoundException) {
                Log.e(
                    TAG,
                    "Error finding setting, default accessibility to not found: " + e.message
                )
            }
            val mStringColonSplitter = SimpleStringSplitter(':')
            if (accessibilityEnabled == 1) {
                val settingValue = Settings.Secure.getString(
                    mContext.applicationContext.contentResolver,
                    "enabled_accessibility_services"
                )
                if (settingValue != null) {
                    mStringColonSplitter.setString(settingValue)
                    while (mStringColonSplitter.hasNext()) {
                        val accessibilityService = mStringColonSplitter.next()
                        if (accessibilityService.equals(service, ignoreCase = true)) {
                            return true
                        }
                    }
                    return false
                }
                return false
            }
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***")
            return false
        }
    }
}