package com.niuniu.babyprotect.service;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.lifecycle.CoroutineLiveDataKt;
import androidx.work.WorkRequest;
import com.miui.enterprise.sdk.ApplicationManager;
import com.niuniu.babyprotect.manager.LocationManager;
import com.niuniu.babyprotect.manager.UploadAppManager;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.model.OtherTimeInfo;
import com.niuniu.babyprotect.model.ParentModelInfo;
import com.niuniu.babyprotect.model.TeacherModelInfo;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.EventUtils;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.tools.image.ImageSave;
import com.umeng.analytics.pro.ak;
import com.umeng.message.MsgConstant;
import com.xiaomi.mipush.sdk.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
public class MyService2 extends Service {
    Context context;
    String userAppName = "";
    long nowUserTime = 0;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Log.d("un", "=======================================onCreate");
                makeUpNet();
                return;
            }
            makeBlackApp();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(WorkRequest.MIN_BACKOFF_MILLIS);
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
                        Thread.sleep(CoroutineLiveDataKt.DEFAULT_TIMEOUT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void makeUpNet() {
        String token = Tools.getToken(this.context);
        if (token == null) {
            return;
        }
        LocationManager.getInstance().onCreate(this.context);
        UploadAppManager.getInstance(this.context).GetInstallAppList();
        upAppIcon();
        getTeacherModel();
        getParentHolidayModel();
        getParentSchoolModel();
        getUserBlacklist();
        getSystemBlacklist();
        getOrderTimeModel();
        getUserInfo();
        makeBlackApp();
    }

    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("un", "Service onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("un", "Service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("un", "Service onDestory");
        Intent i = new Intent("com.example.service_destory");
        sendBroadcast(i);
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void upAppIcon() {
        List<PackageInfo> mlist = UploadAppManager.getInstance(this).getAllApp();
        for (PackageInfo packageInfo : mlist) {
            Drawable drawable = packageInfo.applicationInfo.loadIcon(this.context.getPackageManager());
            Bitmap bitmap = ImageSave.drawableToBitmap(drawable);
            String filename = packageInfo.packageName;
            ImageSave.saveBitmap(this.context, bitmap, filename.replace(".", ""));
        }
        String fileStr = ImageSave.makeZip();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("file", fileStr);
        parameters.put(ak.e, "123123");
        NetTools.getInstance().postImageAsynHttp(this, false, StudentBaseUrl.fileInfos_uploadZip, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    Log.e("aaa", msg.toString());
                }
            }
        });
    }

    public void getTeacherModel() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        if (!userInfo.isBindTeacher()) {
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.patternTeachers_schoolModel, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        String data = msg.getString("data");
                        Tools.saveTeacher(context, data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getParentHolidayModel() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.patternParents_holidayModel, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        String data = msg.getString("data");
                        Tools.saveParentHoliday(context, data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getParentSchoolModel() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.patternParents_schoolModel, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        String data = msg.getString("data");
                        Tools.saveParentSchool(context, data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getUserInfo() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.members_getMemberInfo, parameters, new ResultCallBackListener() {
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
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.applicationPrograms_general, parameters, new ResultCallBackListener() {
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
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.appBlacklists_studentBlacklist, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
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
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.appBlacklists_systemBlacklist, parameters, new ResultCallBackListener() {
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
        String pageName;
        String pageName2;
        String pageName3;
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
        Log.i(EventUtils.TAG, "aaaaaa ===== " + pagename + Constants.COLON_SEPARATOR + this.nowUserTime);
        List<OtherTimeInfo> myapp = Tools.getOtherTime(this.context);
        List<String> list = ApplicationManager.getInstance().getApplicationBlackList();
        list.clear();
        boolean isTimeOk = true;
        TeacherModelInfo teacherModelInfo = Tools.getTeacher(this);
        Tools.getParentHoliday(this);
        ParentModelInfo parentSchoolInfo = Tools.getParentSchool(this);
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        if (!Tools.objIsNullStr(Integer.valueOf(userInfo.getParentPattern())) && userInfo.getParentPattern() == 2) {
            for (OtherTimeInfo otherTimeInfo : myapp) {
                if (otherTimeInfo.isDisableHoliday()) {
                    list.add(otherTimeInfo.getPackageName());
                    Log.i("wanplus", otherTimeInfo.getName());
                } else if (otherTimeInfo.getAvailableHolidayTime() > 0 && (pageName3 = checkTimeOut(otherTimeInfo, otherTimeInfo.getAvailableHolidayTime(), pagename)) != null) {
                    list.add(pageName3);
                }
            }
            ApplicationManager.getInstance().setDisallowedRunningAppList(list);
            return;
        }
        if (teacherModelInfo == null || !teacherModelInfo.isweekend()) {
            Log.e("xxxx", "isweekend==NO");
            if (teacherModelInfo != null && !teacherModelInfo.checkNowTime()) {
                isTimeOk = false;
            }
            if (parentSchoolInfo != null && !parentSchoolInfo.checkOkTime()) {
                isTimeOk = false;
            }
            if (isTimeOk) {
                for (OtherTimeInfo otherTimeInfo2 : myapp) {
                    if (otherTimeInfo2.isDisableDaily()) {
                        list.add(otherTimeInfo2.getPackageName());
                        Log.i("wanplus", otherTimeInfo2.getName());
                    } else if (otherTimeInfo2.getAvailableDailyTime() > 0 && (pageName = checkTimeOut(otherTimeInfo2, otherTimeInfo2.getAvailableDailyTime(), pagename)) != null) {
                        list.add(pageName);
                    }
                }
            } else {
                for (OtherTimeInfo packageInfo : myapp) {
                    if (!packageInfo.getPackageName().equals(getPackageName())) {
                        list.add(packageInfo.getPackageName());
                    }
                }
            }
        } else {
            Log.e("xxxx", "isweekend");
            for (OtherTimeInfo otherTimeInfo3 : myapp) {
                if (otherTimeInfo3.isDisableWeekend()) {
                    list.add(otherTimeInfo3.getPackageName());
                    Log.i("wanplus", otherTimeInfo3.getName());
                } else if (otherTimeInfo3.getAvailableWeekendTime() > 0 && (pageName2 = checkTimeOut(otherTimeInfo3, otherTimeInfo3.getAvailableWeekendTime(), pagename)) != null) {
                    list.add(pageName2);
                }
            }
        }
        ApplicationManager.getInstance().setDisallowedRunningAppList(list);
    }

    public String checkTimeOut(OtherTimeInfo otherTimeInfo, int outTime, String pagename) {
        return null;
    }

    private boolean isScreenLocked() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService("keyguard");
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    public String getForegroundApp(Context context) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
        long ts = System.currentTimeMillis();
        usageStatsManager.queryUsageStats(4, 0L, ts);
        UsageEvents usageEvents = usageStatsManager.queryEvents(1 != 0 ? 0L : ts - CoroutineLiveDataKt.DEFAULT_TIMEOUT, ts);
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
        ActivityManager activityManager = (ActivityManager) this.context.getSystemService(MsgConstant.KEY_ACTIVITY);
        if (Build.VERSION.SDK_INT > 21) {
            long end = System.currentTimeMillis();
            UsageStatsManager usageStatsManager = (UsageStatsManager) this.context.getSystemService("usagestats");
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
            if (packageName.contains("miui.home") || packageName.contains("com.android.systemui") || packageName.equals(getPackageName())) {
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
            String Name = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
            return Name;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }
}
