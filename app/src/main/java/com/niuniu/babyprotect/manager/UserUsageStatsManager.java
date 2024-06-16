package com.niuniu.babyprotect.manager;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.niuniu.babyprotect.model.AppInfo;
import com.niuniu.babyprotect.model.UsePackageInfo;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class UserUsageStatsManager {
    List<AppInfo> alist;
    List<UsePackageInfo> showList = new ArrayList();
    List<UsePackageInfo> showappList = new ArrayList();

    public static void getAppUse(Context mContext) {
        UsageEvents events;
        long end = System.currentTimeMillis();
        long startTime = Tools.zeroTimeForLong(Long.valueOf(end));
        UsageStatsManager usageStatsManager = (UsageStatsManager) mContext.getSystemService("usagestats");
        if (usageStatsManager == null || (events = usageStatsManager.queryEvents(startTime, end)) == null) {
            return;
        }
        UsageEvents.Event usageEvent = new UsageEvents.Event();
        PackageManager pm = mContext.getPackageManager();
        while (events.hasNextEvent()) {
            events.getNextEvent(usageEvent);
            if (usageEvent.getEventType() == 1) {
                UsePackageInfo myPackageInfo = new UsePackageInfo();
                try {
                    PackageInfo appPackageInfo = pm.getPackageInfo(usageEvent.getPackageName(), 0);
                    myPackageInfo.setmAppName(appPackageInfo.applicationInfo.loadLabel(mContext.getPackageManager()).toString());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                myPackageInfo.setStaTime(usageEvent.getTimeStamp());
                myPackageInfo.setmPackageName(usageEvent.getPackageName());
                myPackageInfo.setAppType(usageEvent.getEventType());
                myPackageInfo.setChangeTimes(usageEvent.getTimeStamp());
                ILog.d("UsageEvents-onresu-", myPackageInfo.getmAppName());
            } else if (usageEvent.getEventType() == 2) {
                UsePackageInfo myPackageInfo2 = new UsePackageInfo();
                try {
                    PackageInfo appPackageInfo2 = pm.getPackageInfo(usageEvent.getPackageName(), 0);
                    myPackageInfo2.setmAppName(appPackageInfo2.applicationInfo.loadLabel(mContext.getPackageManager()).toString());
                } catch (PackageManager.NameNotFoundException e2) {
                    e2.printStackTrace();
                }
                myPackageInfo2.setmPackageName(usageEvent.getPackageName());
                myPackageInfo2.setAppType(usageEvent.getEventType());
                myPackageInfo2.setChangeTimes(usageEvent.getTimeStamp());
                ILog.d("UsageEvents-onPaused-", myPackageInfo2.getmAppName());
            }
        }
        new HashMap();
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
                mInfo.setIco(info.activityInfo.applicationInfo.loadIcon(pm));
                mInfo.setName(info.activityInfo.applicationInfo.loadLabel(pm).toString());
                mInfo.setPackageName(packName);
                Intent launchIntent = new Intent();
                launchIntent.setComponent(new ComponentName(packName, info.activityInfo.name));
                mInfo.setIntent(launchIntent);
                list.add(mInfo);
            }
        }
        return list;
    }
}
