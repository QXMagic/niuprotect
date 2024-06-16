package com.niuniu.babyprotect.manager;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import com.google.gson.Gson;
import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.model.AppInfo;
import com.niuniu.babyprotect.model.AppUseInfo;
import com.niuniu.babyprotect.model.UsePackageInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.SystemUtil;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.tools.secret.Base64Utils;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
public class UseTimeDataManager {
    public static final String TAG = "UseTimeDataManager";
    private static UseTimeDataManager mUseTimeDataManager;
    List<AppInfo> alist;
    List<AppUseInfo> appUseInfos;
    private Context mContext;
    private long requestStartTime;
    List<UsePackageInfo> showappList = new ArrayList();
    List<UsePackageInfo> topappList = new ArrayList();

    public UseTimeDataManager() {
        BabyApplication babyApplication = BabyApplication.getInstance();
        this.mContext = babyApplication;
        this.alist = getAppList(babyApplication);
    }

    public static UseTimeDataManager getInstance() {
        if (mUseTimeDataManager == null) {
            mUseTimeDataManager = new UseTimeDataManager();
        }
        return mUseTimeDataManager;
    }

    public void upAppUser() {
        this.requestStartTime = System.currentTimeMillis();
        queryEvent();
    }

    private void getSystemAppUseInfo() {
        UsageStatsManager usageStatsManager;
        List<UsageStats> queryUsageStats;
        this.appUseInfos = new ArrayList();
        long end = System.currentTimeMillis();
        long currentStamp = Tools.zeroTimeForLong(Long.valueOf(end));
        UsageStatsManager usageStatsManager2 = (UsageStatsManager) this.mContext.getSystemService("usagestats");
        if (usageStatsManager2 == null) {
            ILog.d(TAG, "not get usageStatsManager upload use info");
            return;
        }
        List<UsageStats> queryUsageStats2 = usageStatsManager2.queryUsageStats(0, currentStamp, end);
        for (UsageStats usageStats : queryUsageStats2) {
            AppUseInfo appUseInfo = new AppUseInfo();
            long useTime = usageStats.getTotalTimeInForeground();
            if (useTime / 1000 <= 10) {
                usageStatsManager = usageStatsManager2;
                queryUsageStats = queryUsageStats2;
            } else {
                String packageName = usageStats.getPackageName();
                if (!SystemUtil.isSystemApp(this.mContext, packageName) && !packageName.equals(this.mContext.getPackageName())) {
                    appUseInfo.setUseTime(useTime / 1000);
                    StringBuilder sb = new StringBuilder();
                    usageStatsManager = usageStatsManager2;
                    queryUsageStats = queryUsageStats2;
                    sb.append(usageStats.getFirstTimeStamp());
                    sb.append("");
                    appUseInfo.setStartTimeStamp(sb.toString());
                    appUseInfo.setEndTimeStamp(usageStats.getLastTimeStamp() + "");
                    appUseInfo.setPackageName(usageStats.getPackageName());
                    this.appUseInfos.add(appUseInfo);
                }
            }
            usageStatsManager2 = usageStatsManager;
            queryUsageStats2 = queryUsageStats;
        }
        uploadAppinfo(this.appUseInfos);
    }

    private void upAppUseTime() {
        String token;
        boolean findApp;
        boolean findApp2;
        String token2 = Tools.getToken(this.mContext);
        if (token2 == null) {
            return;
        }
        List<AppUseInfo> appUseInfos = new ArrayList<>();
        int k = 0;
        while (k < this.showappList.size()) {
            UsePackageInfo packageInfo = this.showappList.get(k);
            if (!SystemUtil.isSystemApp(this.mContext, packageInfo.getmPackageName()) && !packageInfo.getmPackageName().equals(this.mContext.getPackageName())) {
                int i = 0;
                while (i < packageInfo.getPatternTimeScopes().length()) {
                    try {
                        JSONObject jsonObject = packageInfo.getPatternTimeScopes().getJSONObject(i);
                        long usetime = (Long.parseLong(jsonObject.get("endTimeStamp").toString()) - Long.parseLong(jsonObject.get("startTimeStamp").toString())) / 1000;
                        int size = appUseInfos.size();
                        if (size <= 0) {
                            token = token2;
                            findApp = false;
                        } else {
                            for (int j = 0; j < size; j++) {
                                if (appUseInfos.get(j).getPackageName().equals(packageInfo.getmPackageName())) {
                                    long useTimeApp = appUseInfos.get(j).getUseTime() + usetime;
                                    token = token2;
                                    try {
                                        appUseInfos.get(j).setUseTime(useTimeApp);
                                        appUseInfos.get(j).setEndTimeStamp(jsonObject.get("endTimeStamp").toString());
                                        findApp2 = true;
                                        break;
                                    } catch (JSONException e) {
                                        e = e;
                                        e.printStackTrace();
                                        i++;
                                        token2 = token;
                                    }
                                }
                            }
                            token = token2;
                            findApp = false;
                        }
                        findApp2 = findApp;
                        if (!findApp2) {
                            AppUseInfo appUseInfo = new AppUseInfo();
                            appUseInfo.setPackageName(packageInfo.getmPackageName());
                            appUseInfo.setStartTimeStamp(jsonObject.get("startTimeStamp").toString());
                            appUseInfo.setEndTimeStamp(jsonObject.get("endTimeStamp").toString());
                            appUseInfo.setUseTime(usetime);
                            if (usetime > 0) {
                                appUseInfos.add(appUseInfo);
                            }
                        }
                        Log.i(TAG, "packageInfo" + packageInfo.getmAppName() + Constants.COLON_SEPARATOR + usetime);
                    } catch (JSONException e2) {
                        e = e2;
                        token = token2;
                    }
                    i++;
                    token2 = token;
                }
            }
            k++;
            token2 = token2;
        }
        uploadAppinfo(appUseInfos);
    }

    private void queryEvent() {
        List<AppUseInfo> appUseInfos = new ArrayList<>();
        long end = System.currentTimeMillis();
        long currentStamp = Tools.zeroTimeForLong(Long.valueOf(end));
        UsageStatsManager usageStatsManager = (UsageStatsManager) this.mContext.getSystemService("usagestats");
        if (usageStatsManager == null) {
            ILog.d(TAG, "usageStatsManager is null");
            return;
        }
        UsageEvents events = usageStatsManager.queryEvents(currentStamp, end);
        if (events == null) {
            ILog.d(TAG, "events is null");
            return;
        }
        UsageEvents.Event usageEvent = new UsageEvents.Event();
        AppUseInfo mAppUseInfo = null;
        while (events.hasNextEvent()) {
            events.getNextEvent(usageEvent);
            String packageName = usageEvent.getPackageName();
            if (!SystemUtil.isSystemApp(this.mContext, packageName) && !packageName.equals(this.mContext.getPackageName())) {
                if (usageEvent.getEventType() == 1) {
                    mAppUseInfo = new AppUseInfo();
                    mAppUseInfo.setPackageName(packageName);
                    mAppUseInfo.setStartTimeStamp(usageEvent.getTimeStamp() + "");
                } else if (usageEvent.getEventType() == 2 && mAppUseInfo != null) {
                    if (mAppUseInfo.getPackageName().equals(packageName)) {
                        mAppUseInfo.setEndTimeStamp(usageEvent.getTimeStamp() + "");
                        long useTime = Long.parseLong(mAppUseInfo.getEndTimeStamp()) - Long.parseLong(mAppUseInfo.getStartTimeStamp());
                        mAppUseInfo.setUseTime(useTime / 1000);
                    }
                    appUseInfos.add(mAppUseInfo);
                }
            }
        }
        uploadAppinfo(appUseInfos);
    }

    private void uploadAppinfo(List<AppUseInfo> appUseInfos) {
        if (appUseInfos.size() == 0) {
            ILog.d(TAG, "app use info size is 0");
            return;
        }
        List<AppUseInfo> appUseInfoUploads = new ArrayList<>();
        for (int i = 0; i < appUseInfos.size(); i++) {
            ILog.d("UsageEvents", "appUseInfoUploads:" + appUseInfos.get(i).getPackageName() + "---" + appUseInfos.get(i).getUseTime());
            if (appUseInfos.get(i).getUseTime() >= 10) {
                appUseInfoUploads.add(appUseInfos.get(i));
            }
        }
        int i2 = appUseInfoUploads.size();
        if (i2 == 0) {
            return;
        }
        long endTime = System.currentTimeMillis();
        ILog.d("queryEventTime", "queryEventTime:" + (endTime - this.requestStartTime));
        String listData = Base64Utils.encodeToString(new Gson().toJson(appUseInfoUploads)).replace("\n", "");
        Map<String, String> parameters = new HashMap<>();
        Log.i(TAG, "app datas" + Base64Utils.decodeToString(listData));
        parameters.put("listData", listData);
        NetTools.getInstance().postAsynHttp(this.mContext, StudentBaseUrl.appUseRecords_batchAddRecord, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.d(UseTimeDataManager.TAG, msg.toString());
                }
                long endTime2 = System.currentTimeMillis();
                ILog.d("queryEventTime requestEnd", "queryEventTime:" + (endTime2 - requestStartTime));
            }
        });
    }

    private synchronized String getTopApp() {
        long end = System.currentTimeMillis();
        long currentStamp = Tools.zeroTimeForLong(Long.valueOf(end));
        UsageStatsManager usageStatsManager = (UsageStatsManager) this.mContext.getSystemService("usagestats");
        if (usageStatsManager == null) {
            return null;
        }
        UsageEvents events = usageStatsManager.queryEvents(currentStamp, end);
        if (events == null) {
            return null;
        }
        UsageEvents.Event usageEvent = new UsageEvents.Event();
        PackageManager pm = this.mContext.getPackageManager();
        while (events.hasNextEvent()) {
            events.getNextEvent(usageEvent);
            if (usageEvent.getEventType() == 1) {
                UsePackageInfo myPackageInfo = new UsePackageInfo();
                try {
                    PackageInfo appPackageInfo = pm.getPackageInfo(usageEvent.getPackageName(), 0);
                    myPackageInfo.setmAppName(appPackageInfo.applicationInfo.loadLabel(this.mContext.getPackageManager()).toString());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                myPackageInfo.setStaTime(usageEvent.getTimeStamp());
                myPackageInfo.setmPackageName(usageEvent.getPackageName());
                myPackageInfo.setAppType(usageEvent.getEventType());
                myPackageInfo.setChangeTimes(usageEvent.getTimeStamp());
                this.topappList.add(myPackageInfo);
            }
        }
        Collections.sort(this.topappList, new Comparator<UsePackageInfo>() {
            @Override
            public int compare(UsePackageInfo o1, UsePackageInfo o2) {
                if (o1.getChangeTimes() < o2.getChangeTimes()) {
                    return 1;
                }
                if (o1.getChangeTimes() == o2.getChangeTimes()) {
                    return 0;
                }
                return -1;
            }
        });
        if (this.topappList.size() <= 0) {
            return null;
        }
        return this.topappList.get(0).getmPackageName();
    }

    private static List<AppInfo> getAppList(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", (Uri) null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> activities = pm.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo info : activities) {
            String packName = info.activityInfo.applicationInfo.packageName;
            if (!packName.equals(context.getPackageName())) {
                AppInfo mInfo = new AppInfo();
                mInfo.setIco(info.loadIcon(pm));
                try {
                    CharSequence charSequenceLable = info.loadLabel(pm);
                    String label = charSequenceLable.toString();
                    mInfo.setAppName(label);
                    mInfo.setPackageName(packName);
                    Intent launchIntent = new Intent();
                    launchIntent.setComponent(new ComponentName(packName, info.activityInfo.name));
                    mInfo.setIntent(launchIntent);
                    list.add(mInfo);
                } catch (Resources.NotFoundException e) {
                }
            }
        }
        return list;
    }
}
