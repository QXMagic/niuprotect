package com.niuniu.babyprotect.service;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.lifecycle.CoroutineLiveDataKt;
import androidx.work.WorkRequest;
import im.niu.protect.R;
import com.niuniu.babyprotect.download.DownloadUtil;
import com.niuniu.babyprotect.manager.LocationManager;
import com.niuniu.babyprotect.manager.TXTManager;
import com.niuniu.babyprotect.manager.UploadAppManager;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.model.AppInfo;
import com.niuniu.babyprotect.model.OtherTimeInfo;
import com.niuniu.babyprotect.model.ParentModelInfo;
import com.niuniu.babyprotect.model.TeacherModelInfo;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.stomon.StoToolManager;
import com.niuniu.babyprotect.tools.EventUtils;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.tools.image.ImageSave;
import com.taobao.accs.common.Constants;
import com.umeng.analytics.pro.ak;
import com.umeng.message.MsgConstant;
import com.umeng.message.proguard.z;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
public class MyService3 extends Service {
    public static final String CHANNEL_ID = "com.github.103style.SampleService";
    public static final String CHANNEL_NAME = "com.github.103style";
    public static final String TAG = "MyService3";
    Context context;
    String downBreUrl;
    String userAppName = "";
    long nowUserTime = 0;
    int notifyId = 1101;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(MyService3.this);
            if (userInfo == null || userInfo.getExpireTimeStamp() == 0) {
                return;
            }
            long timea = System.currentTimeMillis();
            long etime = userInfo.getExpireTimeStamp();
            if (timea > etime) {
                StoToolManager.getInstance(getApplicationContext()).cleanAppBlack();
            } else if (msg.what == 200) {
                insbrwApk();
            } else if (msg.what == 232) {
                installApk();
            } else if (msg.what == 1) {
                makeBlackApp();
            } else if (msg.what == 201) {
                makeUpApp();
            } else if (msg.what == 202) {
                makeUpNet();
            }
        }
    };
    String basePatha = Environment.getExternalStorageDirectory().getAbsolutePath();
    String fileName = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        ILog.d(TAG, "onCreate-------");
        this.context = this;
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, getClass());
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long triggerAtTime = SystemClock.elapsedRealtime();
        manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, CoroutineLiveDataKt.DEFAULT_TIMEOUT, pendingIntent);
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(202);
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
                    handler.sendEmptyMessage(Constants.COMMAND_PING);
                    try {
                        Thread.sleep(1200000L);
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
        boolean isok = checkApkExist(this, StudentBaseUrl.brwPageName);
        if (!isok) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.niuniu.babyprotect.service.MyService3.4.run():void");
                }
            }).start();
        }
        registerNotificationChannel();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "com.github.103style.SampleService");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("3985学生端");
        mBuilder.setContentText("正在守护");
        if (Build.VERSION.SDK_INT < 24) {
            mBuilder.setContentTitle(getResources().getString(R.string.app_name));
        }
        startForeground(this.notifyId, mBuilder.build());
    }

    private void registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = mNotificationManager.getNotificationChannel("com.github.103style.SampleService");
            if (notificationChannel == null) {
                NotificationChannel channel = new NotificationChannel("com.github.103style.SampleService", "com.github.103style", NotificationManager.IMPORTANCE_HIGH);
                channel.enableLights(false);
                channel.setLightColor(Color.GRAY);
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
        TXTManager.writeTxt("login", token);
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
            upAppIcon();
        }
    }

    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("un", "Service onStart");
        Log.e("xxxxcser", "onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("un", "Service onStartCommand");
        Log.e("xxxxcser", "onStartCommand");
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
        ImageSave.delFile();
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
                    Log.e("xaxc", msg.toString());
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
        Log.i(EventUtils.TAG, "aaaaaa ===== " + pagename + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + this.nowUserTime);
        List<OtherTimeInfo> myapp = Tools.getOtherTime(this.context);
        List<String> list = new ArrayList<>();
        list.clear();
        TeacherModelInfo teacherModelInfo = Tools.getTeacher(this);
        Tools.getParentHoliday(this);
        ParentModelInfo parentSchoolInfo = Tools.getParentSchool(this);
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        if (!userInfo.isBindTeacher() && !userInfo.isBindParent()) {
            StoToolManager.getInstance(getApplicationContext()).setAppBlack(list);
            return;
        }
        int i = 1;
        if (Tools.objIsNullStr(Integer.valueOf(userInfo.getParentPattern())) || userInfo.getParentPattern() != 2) {
            if (teacherModelInfo != null && !teacherModelInfo.checkNowTime()) {
                if (userInfo.isBindTeacher()) {
                    Log.e("xxxx", "isweekend");
                    for (OtherTimeInfo otherTimeInfo : myapp) {
                        list.add(otherTimeInfo.getPackageName());
                    }
                }
            } else if (userInfo.isBindParent()) {
                Log.e("xxxx", "isweekend==NO");
                if (parentSchoolInfo != null) {
                    if (parentSchoolInfo.checkOkTime()) {
                        if (parentSchoolInfo.checkWork()) {
                            for (OtherTimeInfo otherTimeInfo2 : myapp) {
                                if (otherTimeInfo2.getDailyStatus() == 1) {
                                    list.add(otherTimeInfo2.getPackageName());
                                    Log.i("wanplus", otherTimeInfo2.getName());
                                } else {
                                    String pageName = checkTimeOut(otherTimeInfo2, 1);
                                    if (pageName != null) {
                                        list.add(pageName);
                                    }
                                }
                            }
                        } else {
                            for (OtherTimeInfo otherTimeInfo3 : myapp) {
                                if (otherTimeInfo3.getWeekendStatus() != i) {
                                    String pageName2 = checkTimeOut(otherTimeInfo3, 2);
                                    if (pageName2 != null) {
                                        list.add(pageName2);
                                    }
                                } else {
                                    list.add(otherTimeInfo3.getPackageName());
                                    Log.i("wanplus", otherTimeInfo3.getName());
                                }
                                i = 1;
                            }
                        }
                    } else {
                        for (OtherTimeInfo packageInfo : myapp) {
                            if (!packageInfo.getPackageName().equals(getPackageName())) {
                                list.add(packageInfo.getPackageName());
                            }
                        }
                    }
                }
            }
        } else if (userInfo.isBindParent()) {
            for (OtherTimeInfo otherTimeInfo4 : myapp) {
                if (otherTimeInfo4.getHolidayStatus() != 1) {
                    String pageName3 = checkTimeOut(otherTimeInfo4, 3);
                    if (pageName3 != null) {
                        list.add(pageName3);
                    }
                } else {
                    list.add(otherTimeInfo4.getPackageName());
                    Log.i("wanplus", otherTimeInfo4.getName());
                }
            }
        }
        if (userInfo.isBindParent()) {
            List<AppInfo> ulist = Tools.getBlackUserApp(this);
            for (AppInfo appInfo : ulist) {
                list.add(appInfo.getPackageName());
                token = token;
            }
            Log.e("xxxzzz", ulist.size() + "");
        }
        List<AppInfo> slist = Tools.getBlackSysApp(this);
        for (AppInfo appInfo2 : slist) {
            list.add(appInfo2.getPackageName());
        }
        Log.e("xxxzzz", slist.size() + "");
        for (String appInfo3 : list) {
            Log.i("xaxawwwww", "bb===" + appInfo3);
        }
        int isIng = Tools.getIngAotuSet(this.context);
        Log.i("xaxa", "aa===" + isIng);
        Log.i("xaxa", "aa===" + list.size());
        if (isIng == 1) {
            remSystemManager(list);
        }
        Log.i("xaxa", "aa===" + list.size());
        Log.i("xaxa", "aa===" + list.toString());
        StoToolManager.getInstance(getApplicationContext()).setAppBlack(list);
    }

    public void remSystemManager(List<String> list) {
        for (String page : list) {
            if (page.equals("com.huawei.systemmanager")) {
                Log.i("xaxa", "aa===xxxxxx+com.huawei.systemmanager");
                list.remove(page);
                remSystemManager(list);
                return;
            }
        }
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
            String Name = pm.getApplicationLabel(pm.getApplicationInfo(packageName, 128)).toString();
            return Name;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public void insbrwApk() {
    }

    private void downFile(String url) {
        this.downBreUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startDown();
            }
        }).start();
    }

    public void startDown() {
        Pattern pat = Pattern.compile("[\\w]+[\\.](apk" + z.t);
        Matcher mc = pat.matcher(this.downBreUrl);
        while (mc.find()) {
            this.fileName = mc.group();
        }
        if (this.fileName == null) {
            this.fileName = "aaa.apk";
        }
        DownloadUtil.get().download(this.downBreUrl, this.basePatha, this.fileName, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                Log.e("xaxaxa", "ok");
                Message msg = new Message();
                msg.what = 232;
                handler.sendMessage(msg);
            }

            @Override
            public void onDownloading(int progress) {
            }

            @Override
            public void onDownloadFailed(Exception e) {
            }
        });
    }

    public void installApk() {
    }

    public static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
