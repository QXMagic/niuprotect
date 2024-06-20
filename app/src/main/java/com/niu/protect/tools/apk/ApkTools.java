package com.niu.protect.tools.apk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.niu.protect.BabyApplication;
import com.niu.protect.model.AppInfo;
import com.niu.protect.tools.ILog;
import java.util.ArrayList;
import java.util.List;
public class ApkTools {
    public static List<String> getNullSystemPackageNames() {
        List<String> packageNames = new ArrayList<>();
        List<ResolveInfo> resolveInfos = loadApps();
        for (ResolveInfo resolveInfo : resolveInfos) {
            String packageName = resolveInfo.activityInfo.packageName;
            try {
                PackageManager pm = BabyApplication.getInstance().getPackageManager();
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                if ((pi.applicationInfo.flags & 1) <= 0) {
                    packageNames.add(packageName);
                } else {
                    ILog.d("packageName---systerm-", packageName);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return packageNames;
    }

    private static List<ResolveInfo> loadApps() {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> apps = BabyApplication.getInstance().getPackageManager().queryIntentActivities(intent, 0);
        return apps;
    }

    public static List<AppInfo> getAppList(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", (Uri) null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> activities = pm.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo info : activities) {
            String packName = info.activityInfo.packageName;
            if (!packName.equals(context.getPackageName())) {
                AppInfo mInfo = new AppInfo();
                mInfo.setIco(info.loadIcon(pm));
                ILog.d("packName==", packName);
                mInfo.setName(info.loadLabel(pm).toString());
                mInfo.setPackageName(packName);
                Intent launchIntent = new Intent();
                launchIntent.setComponent(new ComponentName(packName, info.activityInfo.name));
                mInfo.setIntent(launchIntent);
                list.add(mInfo);
            }
        }
        return list;
    }

    public static AppInfo getAPPInfoByPackageName(Context context, String packageName) {
        ApplicationInfo applicationInfo = null;
        PackageManager pkm = context.getPackageManager();
        AppInfo mAppInfo = new AppInfo();
        try {
            PackageInfo packageInfo = pkm.getPackageInfo(packageName, 0);
            ILog.d("packageInfo", "packageInfo:" + ((Object) packageInfo.applicationInfo.loadLabel(pkm)));
            applicationInfo = pkm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo == null) {
            return null;
        }
        mAppInfo.setAppName(applicationInfo.loadLabel(pkm).toString());
        mAppInfo.setPackageName(applicationInfo.packageName);
        mAppInfo.setIco(applicationInfo.loadIcon(pkm));
        mAppInfo.setType(2);
        return mAppInfo;
    }
}
