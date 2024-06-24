package com.niu.protect.manager;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.niu.protect.BuildConfig;
import com.niu.protect.R;
import com.niu.protect.lib.Constants;
import com.niu.protect.model.AppInfo;
import com.niu.protect.model.OtherTimeInfo;
import com.niu.protect.model.UserInfo;
import com.niu.protect.model.WeekModel;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.repository.ApkInfoRepository;
import com.niu.protect.service.FloatingService;
import com.niu.protect.stomon.StoToolManager;
import com.niu.protect.tools.EventUtils;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class PerTaskManager {
    public static final String CHANNEL_ID = "com.github.103style.SampleService";
    public static final String CHANNEL_NAME = "com.github.103style";
    private static PerTaskManager instance = new PerTaskManager();
    Context context;
    String userAppName = "";
    long nowUserTime = 0;
    int notifyId = 1101;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(context);
            if (userInfo == null || userInfo.getExpireTimeStamp() == 0) {
                return;
            }
            long timea = System.currentTimeMillis();
            long etime = userInfo.getExpireTimeStamp();
            if (timea > etime) {
                StoToolManager.getInstance(context).cleanAppBlack();
            }
            if (msg.what == 1) {
                makeBlackApp();
            } else if (msg.what != 201 && msg.what == 202) {
                makeUpNet();
            }
        }
    };

    private PerTaskManager() {
    }

    public static PerTaskManager getInstance() {
        return instance;
    }

    public void onCreate(Context mContext) {
        this.context = mContext;
        Intent floatWinIntent = new Intent(this.context, FloatingService.class);
        this.context.startService(floatWinIntent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(202);
                    try {
                        Thread.sleep(300000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(1);
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        registerNotificationChannel();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.context, "com.github.103style.SampleService");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(Constants.APP_NAME);
        mBuilder.setContentText("正在守护");
        if (Build.VERSION.SDK_INT < 24) {
            mBuilder.setContentTitle(Constants.APP_NAME);
        }
        ((Service) this.context).startForeground(this.notifyId, mBuilder.build());
    }

    private String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == Process.myPid() && proInfo.processName != null) {
                return proInfo.processName;
            }
        }
        return null;
    }

    private void registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager mNotificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = mNotificationManager.getNotificationChannel("com.github.103style.SampleService");
            if (notificationChannel == null) {
                NotificationChannel channel = new NotificationChannel("com.github.103style.SampleService", "com.github.103style", NotificationManager.IMPORTANCE_HIGH);
                channel.enableLights(false);
                channel.setLightColor(-65536);
                channel.setLockscreenVisibility(1);
                mNotificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void makeUpNet() {
        String token = Tools.getToken(this.context);
        if (token == null) {
            return;
        }
        LocationManager.getInstance().onCreate(this.context);
        getTeacherModel();
        getParentHolidayModel();
        getParentSchoolModel();
        getUserBlacklist();
        getSystemBlacklist();
        getOrderTimeModel();
        getUserInfo();
    }

    public void makeUpApp() {
        boolean isnew = UploadAppManager.getInstance(this.context).GetInstallAppList();
        if (isnew) {
            Log.i("xxxx", "上传app图片.....");
            ApkInfoRepository.getInstance().upAppIcon();
        }
        Log.e("xxx===", "nnana=====ccc");
    }

    public void getTeacherModel() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this.context);
        if (!userInfo.isBindTeacher()) {
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(this.context, StudentBaseUrl.patternTeachers_querybyteacherid, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        JSONArray data = msg.getJSONObject("data").getJSONArray("patternTeacherSlots");
                        Tools.saveTeacher(context, data.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getParentHolidayModel() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this.context);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        parameters.put("type", "2");
        NetTools.getInstance().getAsynHttp(this.context, StudentBaseUrl.patternParents_query, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        JSONArray data = msg.getJSONObject("data").getJSONArray("patternParentSlots");
                        Tools.saveParentModel(context, data.toString(), 2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getParentSchoolModel() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this.context);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        parameters.put("type", "1");
        NetTools.getInstance().getAsynHttp(this.context, StudentBaseUrl.patternParents_query, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        JSONArray data = msg.getJSONObject("data").getJSONArray("patternParentSlots");
                        Tools.saveParentModel(context, data.toString(), 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getUserInfo() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this.context);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        NetTools.getInstance().getAsynHttp(this.context, StudentBaseUrl.members_getMemberInfo, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        String data = msg.getString("data");
                        UserInfoManager.getInstance().saveUser(context, data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getOrderTimeModel() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this.context);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        NetTools.getInstance().getAsynHttp(this.context, StudentBaseUrl.applicationPrograms_general, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    Log.e("xaaa", msg.toString());
                    ILog.log(msg);
                    try {
                        String data = msg.getString("data");
                        Tools.saveOtherTime(context, data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getUserBlacklist() {
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(this.context, StudentBaseUrl.appBlacklists_studentBlacklist, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    Log.i("xxaaaxx", msg.toString());
                    try {
                        String data = msg.getString("data");
                        Tools.saveBlackUserApp(context, data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getSystemBlacklist() {
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(this.context, StudentBaseUrl.appBlacklists_systemBlacklist, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        String data = msg.getString("data");
                        Tools.saveBlackSysApp(context, data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void makeBlackApp() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this.context);
        if (userInfo != null && userInfo.getExpireTimeStamp() != 0) {
            long timea = System.currentTimeMillis();
            long etime = userInfo.getExpireTimeStamp();
            boolean isvip = timea <= etime;
            String token = Tools.getToken(this.context);
            if (token == null) {
                return;
            }
            String pagename = apprun();
            if (Tools.objIsNullStr(pagename)) {
                pagename = this.userAppName;
            }
            int addTime = 5;
            addTime = (isScreenLocked() || this.userAppName.equals("home")) ? 0 : 0;
            if (pagename != null) {
                if (this.userAppName.equals(pagename)) {
                    this.nowUserTime += addTime;
                } else {
                    this.userAppName = pagename;
                    this.nowUserTime = 0L;
                }
            }
            Log.i(EventUtils.TAG, "aaaaaa ===== " + pagename + " - " + this.nowUserTime);
            List<String> list = new ArrayList<>();
            list.clear();
            if (!userInfo.isBindTeacher() && !userInfo.isBindParent()) {
                StoToolManager.getInstance(this.context).setAppBlack(list);
                checkPer();
                return;
            }
            if (!Tools.objIsNullStr(Integer.valueOf(userInfo.getParentPattern())) && userInfo.getParentPattern() == 2 && isvip) {
                if (userInfo.isBindParent()) {
                    Log.e("xxxx", "isweekend==NO");
                    List<String> blist = checkParentModel(2);
                    list.addAll(blist);
                }
            } else {
                List<String> tlist = checkTeacherModel();
                if (tlist.size() > 0) {
                    if (userInfo.isBindTeacher()) {
                        Log.e("xxxx", "isweekend");
                        list.addAll(tlist);
                    }
                } else if (userInfo.isBindParent() && isvip) {
                    Log.e("xxxx", "isweekend==NO");
                    List<String> blist2 = checkParentModel(1);
                    list.addAll(blist2);
                }
            }
            if (userInfo.isBindParent() && isvip) {
                List<AppInfo> ulist = Tools.getBlackUserApp(this.context);
                for (AppInfo appInfo : ulist) {
                    list.add(appInfo.getPackageName());
                    userInfo = userInfo;
                }
                Log.e("xxxzzz", ulist.size() + "");
            }
            if (isvip) {
                List<AppInfo> slist = Tools.getBlackSysApp(this.context);
                for (AppInfo appInfo2 : slist) {
                    list.add(appInfo2.getPackageName());
                }
                Log.e("xxxzzz", slist.size() + "");
            }
            Iterator<String> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String appInfo3 = it.next();
                if (appInfo3.equals(BuildConfig.APPLICATION_ID)) {
                    list.remove(appInfo3);
                    break;
                }
            }
            JSONArray aaa = new JSONArray((Collection) list);
            Tools.saveBlackApp(this.context, aaa);
        }
    }

    public void checkPer() {
        if (UserProtectManager.getInstance().getProtectStatus() == 1) {
            UserProtectManager.getInstance().setProtect(-1);
        }
    }

    public List<String> checkParentModel(int type) {
        List<OtherTimeInfo> myapp = Tools.getOtherTime(this.context);
        List<String> list = new ArrayList<>();
        List<WeekModel> timelist = Tools.getParentModel(this.context, type);
        if (timelist.size() > 0) {
            boolean isWork = true;
            Iterator<WeekModel> it = timelist.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WeekModel weekModel = it.next();
                if (weekModel.checkNowTime() && !weekModel.checkOkTime()) {
                    isWork = false;
                    break;
                }
            }
            if (isWork) {
                for (OtherTimeInfo otherTimeInfo : myapp) {
                    if (type == 1) {
                        if (otherTimeInfo.getDailyStatus() == 1) {
                            list.add(otherTimeInfo.getPackageName());
                            Log.i("wanplus", otherTimeInfo.getName());
                        } else {
                            String pageName = checkTimeOut(otherTimeInfo, 1);
                            if (pageName != null) {
                                list.add(pageName);
                            }
                        }
                    } else if (otherTimeInfo.getHolidayStatus() == 1) {
                        list.add(otherTimeInfo.getPackageName());
                        Log.i("wanplus", otherTimeInfo.getName());
                    } else {
                        String pageName2 = checkTimeOut(otherTimeInfo, 3);
                        if (pageName2 != null) {
                            list.add(pageName2);
                        }
                    }
                }
            } else {
                for (OtherTimeInfo packageInfo : myapp) {
                    if (!packageInfo.getPackageName().equals(this.context.getPackageName())) {
                        list.add(packageInfo.getPackageName());
                    }
                }
            }
        }
        return list;
    }

    public List<String> checkTeacherModel() {
        List<OtherTimeInfo> myapp = Tools.getOtherTime(this.context);
        List<String> list = new ArrayList<>();
        List<WeekModel> timelist = Tools.getTeacherModel(this.context);
        if (timelist.size() > 0) {
            boolean isWork = true;
            Iterator<WeekModel> it = timelist.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WeekModel weekModel = it.next();
                if (weekModel.checkNowTime() && !weekModel.checkOkTime()) {
                    isWork = false;
                    break;
                }
            }
            if (!isWork) {
                for (OtherTimeInfo otherTimeInfo : myapp) {
                    list.add(otherTimeInfo.getPackageName());
                }
            }
        }
        return list;
    }

    public String checkTimeOut(OtherTimeInfo otherTimeInfo, int intoType) {
        String strDate = Tools.timeFormat(new Date(), "yyyy-MM-dd");
        if (intoType == 1) {
            if (otherTimeInfo.getDailyStatus() == 3) {
                String time1 = strDate + " " + otherTimeInfo.getDailyStartTime();
                String time2 = strDate + " " + otherTimeInfo.getDailyEndTime();
                long timeL1 = Tools.timeForLong(time1);
                long timeL2 = Tools.timeForLong(time2);
                long nowL1 = new Date().getTime();
                if (nowL1 > timeL1 && nowL1 < timeL2) {
                    return null;
                }
                return otherTimeInfo.getPackageName();
            }
        } else if (intoType == 2) {
            if (otherTimeInfo.getWeekendStatus() == 3) {
                String time12 = strDate + " " + otherTimeInfo.getWeekendStartTime();
                String time22 = strDate + " " + otherTimeInfo.getWeekendEndTime();
                long timeL12 = Tools.timeForLong(time12);
                long timeL22 = Tools.timeForLong(time22);
                long nowL12 = new Date().getTime();
                if (nowL12 > timeL12 && nowL12 < timeL22) {
                    return null;
                }
                return otherTimeInfo.getPackageName();
            }
        } else if (intoType == 3 && otherTimeInfo.getHolidayStatus() == 3) {
            String time13 = strDate + " " + otherTimeInfo.getHolidayStartTime();
            String time23 = strDate + " " + otherTimeInfo.getHolidayEndTime();
            long timeL13 = Tools.timeForLong(time13);
            long timeL23 = Tools.timeForLong(time23);
            long nowL13 = new Date().getTime();
            if (nowL13 > timeL13 && nowL13 < timeL23) {
                return null;
            }
            return otherTimeInfo.getPackageName();
        }
        return null;
    }

    private boolean isScreenLocked() {
        KeyguardManager keyguardManager = (KeyguardManager) this.context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    public String getForegroundApp(Context context) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        long ts = System.currentTimeMillis();
        usageStatsManager.queryUsageStats(4, 0L, ts);
        UsageEvents usageEvents = usageStatsManager.queryEvents(1 != 0 ? 0L : ts - 5000L, ts);
        if (usageEvents == null) {
            return null;
        }
        UsageEvents.Event event = new UsageEvents.Event();
        UsageEvents.Event lastEvent = null;
        while (usageEvents.getNextEvent(event)) {
            if (event.getPackageName() != null && event.getClassName() != null && (lastEvent == null || lastEvent.getTimeStamp() < event.getTimeStamp())) {
                lastEvent = event;
            }
        }
        if (lastEvent == null) {
            return null;
        }
        return lastEvent.getPackageName();
    }

    public String apprun() {
        UsageEvents events;
        String packageName = "";
        if (Build.VERSION.SDK_INT > 21) {
            long end = System.currentTimeMillis();
            UsageStatsManager usageStatsManager = (UsageStatsManager) this.context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (usageStatsManager == null || (events = usageStatsManager.queryEvents(end - 120000, end)) == null) {
                return "";
            }
            UsageEvents.Event usageEvent = new UsageEvents.Event();
            while (events.hasNextEvent()) {
                events.getNextEvent(usageEvent);
                if (usageEvent.getEventType() == 1) {
                    packageName = usageEvent.getPackageName();
                }
            }
        }
        if (packageName != null) {
            if (packageName.contains("miui.home") || packageName.contains("com.android.systemui") || packageName.equals(this.context.getPackageName())) {
                return "home";
            }
            List<OtherTimeInfo> myapp = Tools.getOtherTime(this.context);
            for (OtherTimeInfo otherTimeInfo : myapp) {
                if (otherTimeInfo.getPackageName().equals(packageName)) {
                    return packageName;
                }
            }
            return null;
        }
        return null;
    }

    public String getApplicationNameByPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            String Name = pm.getApplicationLabel(pm.getApplicationInfo(packageName, 128)).toString();
            return Name;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public void insbrwApk() {
    }

    public void installApk() {
    }
}
