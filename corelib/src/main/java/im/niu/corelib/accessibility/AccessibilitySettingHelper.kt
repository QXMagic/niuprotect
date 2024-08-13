package im.niu.corelib.accessibility

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils.SimpleStringSplitter
import android.util.Log
import android.view.accessibility.AccessibilityManager
import im.niu.corelib.Constants
import im.niu.corelib.R
import im.niu.corelib.utils.ILog
import im.niu.corelib.utils.NiuUtil
import im.niu.corelib.utils.RomUtil

class AccessibilitySettingHelper {


    companion object {
        private const val TAG = "open access"


        @JvmStatic
        fun openAccessibility(context: Context) {
            val accessibilityManager =
                context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            if (!accessibilityManager.isEnabled) {
                if(RomUtil.isMiui){
                     try {
                         openAccessXiaoMi(context)
                         return
                     }catch (e: Throwable){

                     }
                }
                try {
                    context.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                    return
                } catch (e: Throwable) { //若出现异常，则说明该手机设置被厂商篡改了,需要适配

                }
                try {
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (e2: Throwable) {
                    Log.e(TAG, "jumpToSetting: " + e2.message)
                }
            }
        }

        private fun openAccessXiaoMi(context: Context) {
            val intent = Intent("android.intent.action.MAIN")
            intent.component =
                ComponentName("com.android.settings", "com.android.settings.Settings")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val bundle = Bundle()
            val componentName =
                ComponentName(context.packageName, NiuAccessibilityService::class.java.name)
            val preferenceKey = context.packageName + "/" + NiuAccessibilityService::class.java.name
            bundle.putString("preference_key", preferenceKey)
            ILog.d("Tools", preferenceKey)
            bundle.putBoolean("checked", false)
            bundle.putString("title", Constants.APP_NAME)
            bundle.putString("summary", context.getString(R.string.accessibility_desc))
            bundle.putParcelable("component_name", componentName)
            intent.putExtra(
                ":android:show_fragment",
                "com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment"
            )
            intent.putExtra(":android:show_fragment_args", bundle)
            intent.putExtra(":android:no_headers", true)
            intent.putExtra("setting:ui_options", 1)
            context.startActivity(intent)
        }




        @JvmStatic
        fun isAccessibilitySettingsOn(context: Context): Boolean {
            return NiuUtil.isServiceRunning(context,NiuAccessibilityService::class.java.name)
        }

        @JvmStatic
        fun isAccessibilitySettingsOnByService(mContext: Context): Boolean {
            var isOn = isAccessibilitySettingsOn(mContext)
            var accessibilityEnabled = 0
            val service =
                mContext.packageName + "/" + NiuAccessibilityService::class.java.canonicalName
            Log.i(TAG, "service:$service is On?$isOn")

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