package com.niuniu.babyprotect.accessibility;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import com.niuniu.babyprotect.tools.ILog;
import com.umeng.message.MsgConstant;
import java.util.List;
public class OpenAccessibilitySettingHelper {
    private static final String TAG = "OpenAccessibilitySettingHelper";

    public static void jumpToSettingPage(Context context) {
        Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
        context.startActivity(intent);
    }

    public static boolean isAccessibilitySettingsOn(Context context, String className) {
        if (context == null) {
            ILog.d("context--", "context is null ");
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
        if (runningServices.size() < 0) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            ComponentName service = runningServices.get(i).service;
            if (service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAccessibilitySettingsOnByService(Context mContext) {
        int accessibilityEnabled = 0;
        String service = mContext.getPackageName() + "/" + StatusUseAccessibilityService.class.getCanonicalName();
        Log.i(TAG, "service:" + service);
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(), "accessibility_enabled");
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(), "enabled_accessibility_services");
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        return false;
    }

    private void openAccessibility(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (!accessibilityManager.isEnabled()) {
            context.startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
        }
    }
}
