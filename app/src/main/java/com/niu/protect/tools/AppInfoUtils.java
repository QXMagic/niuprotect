package com.niu.protect.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
public class AppInfoUtils {
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionCode;
    }

    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
    }

    public static String getChannel(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String appChannel = "main";
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData == null) {
                return "main";
            }
            appChannel = String.valueOf(applicationInfo.metaData.get("channel"));
            ILog.d("channel get", appChannel);
            return appChannel;
        } catch (PackageManager.NameNotFoundException e) {
            ILog.d("channel get", "NameNotFoundException");
            e.printStackTrace();
            return appChannel;
        }
    }
}
