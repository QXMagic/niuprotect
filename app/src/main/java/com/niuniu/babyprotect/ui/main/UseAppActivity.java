package com.niuniu.babyprotect.ui.main;

import static android.app.usage.UsageEvents.Event.ACTIVITY_RESUMED;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.niuniu.babyprotect.BuildConfig;
import com.niuniu.babyprotect.adapter.UseAppAdapter;
import com.niuniu.babyprotect.model.AppInfo;
import com.niuniu.babyprotect.model.UsePackageInfo;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.niu.protect.R;
public class UseAppActivity extends BaseActivity {
    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1101;
    List<AppInfo> alist;
    ListView listview;
    UseAppAdapter useAppAdapter;
    List<UsePackageInfo> appList = new ArrayList();
    List<UsePackageInfo> showList = new ArrayList();
    List<UsePackageInfo> desList = new ArrayList();
    List<UsePackageInfo> writeList = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_app);
        changeTitle("app使用时间");
        showBack();
        this.alist = getAppList(this);
        getHigherPackageName();
        this.listview = (ListView) findViewById(R.id.listview);
        UseAppAdapter useAppAdapter = new UseAppAdapter(this, this.desList);
        this.useAppAdapter = useAppAdapter;
        this.listview.setAdapter((ListAdapter) useAppAdapter);
        TextView gjTime = (TextView) findViewById(R.id.gjTime);
        gjTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.settings.USAGE_ACCESS_SETTINGS"));
            }
        });
    }
    private synchronized void getHigherPackageName(){

        this.appList.clear();
        this.writeList.clear();
        this.desList.clear();
        this.showList.clear();
        long time_now = java.lang.System.currentTimeMillis();
        long timeZero = Tools.zeroTimeForLong(time_now);
        UsageStatsManager statsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);     // Catch: java.lang.Throwable -> L123
        if (statsManager == null) return;
        UsageEvents events = statsManager.queryEvents(timeZero, time_now);     // Catch: java.lang.Throwable -> L123
        if (events == null) return;

        UsageEvents.Event event = new UsageEvents.Event();     // Catch: java.lang.Throwable -> L123
        PackageManager pk0 = getPackageManager();     // Catch: java.lang.Throwable -> L123
        for (;events.hasNextEvent() ;events.getNextEvent(event) ) {
            UsePackageInfo pkinfo = new UsePackageInfo();
            if (event.getEventType() != ACTIVITY_RESUMED) {
//                if (event.getEventType() != ACTIVITY_PAUSED) {
//                    for (UsePackageInfo usePackageInfo : writeList) {
//                        String r10 = usePackageInfo.getChangeTimes() + "------" +
//                                Tools.timeFormat(new Date(usePackageInfo.getChangeTimes()),"yyyy-MM-dd HH:mm:ss")
//                                + "-----" + usePackageInfo.getmAppName() + "-----";     // Catch: java.lang.Throwable -> L123
//                        if (usePackageInfo.getAppType() != ACTIVITY_RESUMED) {
//                            //L37:
//                            if (usePackageInfo.getAppType() != ACTIVITY_PAUSED) {
//                                //L41:
//                                if (usePackageInfo.getAppType() != 23) {
//                                    continue;
//                                }
//                                java.lang.String rs0 = r10 + "停止";     // Catch: java.lang.Throwable -> L123
//                                continue;
//                            }
//                            java.lang.String r11 = r10 + "暂停";     // Catch: java.lang.Throwable -> L123
//                            continue;
//                        } else {
//                            java.lang.String r11 = r10 + "开始";     // Catch: java.lang.Throwable -> L123
//                            continue;
//                        }
//                    }
//                }
                     // Catch: java.lang.Throwable -> L123
//                android.content.pm.PackageInfo pr0 = pk0.getPackageInfo(event.getPackageName(), 0);     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L61 java.lang.Throwable -> L123
//                pkinfo.setmAppName(pr0.applicationInfo.loadLabel(getPackageManager()).toString());
                pkinfo.setStaTime(event.getTimeStamp());
                pkinfo.setmPackageName(event.getPackageName());     // Catch: java.lang.Throwable -> L123
                pkinfo.setAppType(event.getEventType());     // Catch: java.lang.Throwable -> L123
                pkinfo.setChangeTimes(event.getTimeStamp());     // Catch: java.lang.Throwable -> L123
                if (checkApp(pkinfo.getmPackageName())) {
                    appList.add(pkinfo);
                } else {
                    writeList.add(pkinfo);//新增app
                }
            }
        }

//L64
        //计算使用时间
        UsePackageInfo start = null;
        for (UsePackageInfo endInfo : appList) {
            if (start == null) {
                if (endInfo.getAppType() != ACTIVITY_RESUMED) continue;
                start = endInfo;
                continue;
            }
            if (!start.getmPackageName().equals(endInfo.getmPackageName())) {
                if (endInfo.getAppType() != ACTIVITY_RESUMED) continue;
                start = endInfo;
                continue;
            }
            if (start.getAppType() != ACTIVITY_RESUMED) {
                if (start.getAppType() != ACTIVITY_RESUMED) {
                    if (endInfo.getAppType() != ACTIVITY_RESUMED) continue;
                    start = endInfo;
                    continue;
                }
                ;
                if (endInfo.getAppType() == ACTIVITY_RESUMED) {
                    continue;
                }
                ;
            }
            if (endInfo.getAppType() != 2) {
                if (start.getAppType() != ACTIVITY_RESUMED) {
                    if (endInfo.getAppType() != ACTIVITY_RESUMED) continue;
                    start = endInfo;
                    continue;
                }
                ;
                if (endInfo.getAppType() == ACTIVITY_RESUMED) continue;
            }
            long timeDiff = (endInfo.getChangeTimes() - start.getChangeTimes()) / 1000;
            if (timeDiff <= 5) {
                start = null;
            } else {
                start.setStaTime(start.getChangeTimes());     // Catch: java.lang.Throwable -> L123
                start.setEndTime(endInfo.getChangeTimes());     // Catch: java.lang.Throwable -> L123
                start.setmUsedTime(timeDiff * 1000);     // Catch: java.lang.Throwable -> L123
                showList.add(start);     // Catch: java.lang.Throwable -> L123
            }
        }
        //合并使用时间
        Map<String, UsePackageInfo> map = new HashMap();
        for (UsePackageInfo pinfo : showList) {
            UsePackageInfo old = map.get(pinfo.getmPackageName());
            if (old == null) {
                map.put(pinfo.getmPackageName(), pinfo);
                desList.add(pinfo);
            } else {
                old.setmUsedTime(old.getmUsedTime() + pinfo.getmUsedTime());
            }
        }
    }

   /**
    * 检测Name 是否在alist中,是否为本APP
    * */
    public boolean checkApp(String appName) {
        for(AppInfo info:alist){
            if(info.getPackageName().equals(appName)){
                return true;
            }
            if(appName.equals(BuildConfig.APPLICATION_ID)){
                return true;
            }
        }
        return false;
    }



    /**
     * 获取所有APP list
     * */
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

    private String getLowerVersionPackageName() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName topActivity = activityManager.getRunningTasks(1).get(0).topActivity;
        String topPackageName = topActivity.getPackageName();
        return topPackageName;
    }

    private boolean hasPermission() {
        AppOpsManager appOpsM = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT > 19) {
            mode = appOpsM.checkOpNoThrow("android:get_usage_stats", Process.myUid(), getPackageName());
        }
        return mode == 0;
    }
}
