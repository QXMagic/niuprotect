package com.niu.protect.accessibility.auto.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
public class AppInfo {
    static String appName = "";

    public static synchronized String getAppName(Context context) {
        String str;
        synchronized (AppInfo.class) {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (TextUtils.isEmpty(appName)) {
                    appName = String.valueOf(packageManager.getApplicationLabel(context.getApplicationInfo()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                appName = "3985学生端";
            }
            str = appName;
        }
        return str;
    }

    public static synchronized String getVersionName(Context context) {
        String str;
        synchronized (AppInfo.class) {
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                str = packageInfo.versionName;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return str;
    }

    public static synchronized int getVersionCode(Context context) {
        int i;
        synchronized (AppInfo.class) {
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                i = packageInfo.versionCode;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        return i;
    }

    public static synchronized String getPackageName(Context context) {
        String str;
        synchronized (AppInfo.class) {
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                str = packageInfo.packageName;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return str;
    }

    public static synchronized Bitmap getBitmap(Context context) {
        ApplicationInfo applicationInfo;
        Bitmap bm;
        synchronized (AppInfo.class) {
            PackageManager packageManager = null;
            try {
                packageManager = context.getApplicationContext().getPackageManager();
                applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                applicationInfo = null;
            }
            Drawable d = packageManager.getApplicationIcon(applicationInfo);
            BitmapDrawable bd = (BitmapDrawable) d;
            bm = bd.getBitmap();
        }
        return bm;
    }
}
