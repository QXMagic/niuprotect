package com.niuniu.babyprotect.ui.main;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.WorkRequest;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.accessibility.OpenAccessibilitySettingHelper;
import com.niuniu.babyprotect.accessibility.StatusUseAccessibilityService;
import com.niuniu.babyprotect.broadcastReceiver.BroadcastManager;
import com.niuniu.babyprotect.databinding.ActivityMainBinding;
import com.niuniu.babyprotect.download.DownloadService;
import com.niuniu.babyprotect.download.MyReceiver;
import com.niuniu.babyprotect.manager.AutoSettingManager;
import com.niuniu.babyprotect.manager.SharedPreManager;
import com.niuniu.babyprotect.manager.StudentMainController;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.manager.WebSocketManager;
import com.niuniu.babyprotect.model.AppInfo;
import com.niuniu.babyprotect.model.SystemConfigModel;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.model.eventbus.EventParentChangeBindMode;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.service.UploadAppIntentService;
import com.niuniu.babyprotect.third.umeng.UMengManager;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.QRCodeUtil;
import com.niuniu.babyprotect.tools.RomUtil;
import com.niuniu.babyprotect.tools.SharedPreUtil;
import com.niuniu.babyprotect.tools.ToastUtil;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import com.niuniu.babyprotect.ui.mine.MineActivity;
import com.niuniu.babyprotect.ui.setting.OpenQxActivity;
import com.niuniu.babyprotect.viewmodel.MainViewModel;
import com.niuniu.babyprotect.viewmodel.MainViewModelFactory;
import com.niuniu.babyprotect.widget.UpdateDialog;
import com.niuniu.babyprotect.work.MineWorkerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import atmp.consts.Constants;
import cn.bertsir.zbar.Qr.ScanResult;
import cn.bertsir.zbar.QrManager;
import im.niu.protect.R;
public class MainActivity extends BaseActivity {
    public static final String TAG_USERINFO = "tag_intent_userinfo";
    public static boolean mainRunning = false;
    ActivityMainBinding binding;
    UserInfo mUserInfo;
    MainViewModel mainViewModel;
    SystemConfigModel systemConfigModel;
    private WindowManager.LayoutParams wmParams;
    private LayoutInflater inflater = null;
    String[] PermissionString = {Permission.ACCESS_BACKGROUND_LOCATION};
    boolean isLoaded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding inflate = ActivityMainBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.mainViewModel = (MainViewModel) new ViewModelProvider(this, new MainViewModelFactory()).get(MainViewModel.class);
        initUi();
        initObserve();
        this.mUserInfo = UserInfoManager.getInstance().getUserInfo(this);
        regist();
        WebSocketManager.getInstance().start();
        mainRunning = true;
        uploadAllAPP();
    }

    public void uploadAllAPP() {
        boolean isUploadAPP = ((Boolean) SharedPreUtil.getParam(BabyApplication.getInstance(), SharedPreManager.KEY_APP_UPLOAD_APPS, false)).booleanValue();
        if (!isUploadAPP) {
            UploadAppIntentService.startActionBaz(this, "", "");
        }
    }

    private void checkPermissonOfAutoSetting() {
        ILog.d("OpenAccess--", OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(this, StatusUseAccessibilityService.class.getName()) + "");
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        sb.append(AutoSettingManager.getInstance().isSettingFinish() ^ true);
        sb.append("");
        ILog.d("isSettingFinish--", sb.toString());
        if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(this, StatusUseAccessibilityService.class.getName()) && !OpenAccessibilitySettingHelper.isAccessibilitySettingsOnByService(this)) {
            z = false;
        }
        boolean accessSuccess = z;
        if (!accessSuccess || !AutoSettingManager.getInstance().isSettingFinish()) {
            enterQx();
        } else {
            checkPermission();
        }
    }

    void initObserve() {
        this.mainViewModel.getUpdateAppJson().observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                if (jsonObject != null) {
                    UpdateDialog.checkUpdate(jsonObject, _context);
                }
            }
        });
        this.mainViewModel.getBindNetInfo().observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                if (jsonObject != null) {
                    Toast.makeText(_context, "绑定成功", Toast.LENGTH_LONG).show();
                    uploadAllAPP();
                }
            }
        });
        this.mainViewModel.getLoadgyInfo().observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                if (jsonObject != null) {
                    try {
                        String data = jsonObject.getJSONObject("data").getString("content");
                        TextView gytxt = (TextView) findViewById(R.id.gytxt);
                        gytxt.setText(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.mainViewModel.getSystemConfig().observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                if (jsonObject != null) {
                    systemConfigModel = (SystemConfigModel) new Gson().fromJson(jsonObject.toString(),  SystemConfigModel.class);
                }
            }
        });
        this.mainViewModel.requestLoadgy();
        this.mainViewModel.requestSystemConfig();
    }

    private void initUi() {
        this.binding.scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindQRCode();
            }
        });
        this.binding.clockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent("android.intent.action.SHOW_ALARMS");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.binding.userHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(_context, MineActivity.class);
                _context.startActivity(intent);
            }
        });
        this.binding.mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(_context, MineActivity.class);
                _context.startActivity(intent);
            }
        });
        this.binding.goTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(_context);
                if (!userInfo.isBindTeacher()) {
                    bindQRCode();
                    return;
                }
                ToastUtil.show("已绑定");
                StudentMainController.getInstance().requestMainControl();
            }
        });
        this.binding.goParentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(_context);
                if (!userInfo.isBindParent()) {
                    bindQRCode();
                    return;
                }
                ToastUtil.show("已绑定");
                StudentMainController.getInstance().requestMainControl();
            }
        });
        this.binding.userDesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUserInfo == null) {
                    showToast("数据加载失败,请稍后");
                    return;
                }
                MainActivity mainActivity = MainActivity.this;
                UserDesActivity.enterUserDesActivity(mainActivity, mainActivity.mUserInfo);
            }
        });
        this.binding.staAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUserInfo == null) {
                    showToast("数据加载失败,请稍后");
                    return;
                }
                MainActivity mainActivity = MainActivity.this;
                StaActivity.enterStaActivity(mainActivity, mainActivity.mUserInfo);
            }
        });
    }

    private void enterQx() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        if (userInfo.isBindTeacher() || userInfo.isBindParent()) {
            if (RomUtil.isOppo() || RomUtil.isVivo() || RomUtil.isEmui()) {
                Intent intent = new Intent();
                intent.setClass(this._context, OpenQxActivity.class);
                this._context.startActivity(intent);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getAction() == 0) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void bindQRCode() {
        QRCodeUtil.bindQRCode(this, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(ScanResult result) {
                String resultContent = result.getContent();
                if (!TextUtils.isEmpty(resultContent)) {
                    mainViewModel.requestBindNet(result.getContent());
                } else {
                    ToastUtil.show("扫码失败");
                }
            }
        });
    }

    public void refUi(UserInfo userInfo, boolean checkBind) {
        if (userInfo == null) {
            userInfo = UserInfoManager.getInstance().getUserInfo(this);
        }
        if (userInfo == null) {
            ILog.d(Constants.KEY_USER_ID, "userInfo is null");
            return;
        }
        String userName = userInfo.getName();
        if (TextUtils.isEmpty(userName)) {
            userName = userInfo.getMobile();
        }
        this.binding.username.setText(userName);
        if (!userInfo.isBindTeacher()) {
            this.binding.bingttxt.setText("未绑定老师");
        } else {
            this.binding.bingttxt.setText("已绑定老师");
        }
        if (!userInfo.isBindParent()) {
            this.binding.bingptxt.setText("未绑定家长");
        } else {
            this.binding.bingptxt.setText("已绑定家长");
        }
        if (checkBind) {
            checkBind(userInfo);
        }
    }

    private void checkBind(UserInfo userInfo) {
        if (userInfo.isBindTeacher() || userInfo.isBindParent()) {
            checkPermissonOfAutoSetting();
        }
    }

    public void pushInit() {
        UMengManager.addAlias(this, this.mUserInfo);
    }

    public void checkPermission() {
        int targetSdkVersion = 0;
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 23 && targetSdkVersion >= 23) {
            boolean isAllGranted = checkPermissionAllGranted(this.PermissionString);
            if (isAllGranted) {
                Log.e(NotificationCompat.CATEGORY_ERROR, "所有权限已经授权！");
            } else {
                ActivityCompat.requestPermissions(this, this.PermissionString, 1);
            }
        }
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            boolean isAllGranted = true;
            int length = grantResults.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                int grant = grantResults[i];
                if (grant == 0) {
                    i++;
                } else {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                Log.e(NotificationCompat.CATEGORY_ERROR, "权限都授权了");
            }
        }
    }

    public void getUserInfo(final boolean isFirstLoad) {
        if (isFirstLoad) {
            this.isLoaded = true;
        }
        final UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.members_getMemberInfo, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log("getUserInfo --" + msg);
                    try {
                        String data = msg.getString("data");
                        mUserInfo = (UserInfo) new Gson().fromJson(data, UserInfo.class);
                        UserInfoManager.getInstance().saveUser(_context, data);
                        refUi(mUserInfo, true);
                        if (userInfo.isBindTeacher() || userInfo.isBindParent()) {
                            requestControlInfo(isFirstLoad);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if (!Tools.objIsNullStr(userInfo.getImageUrl())) {
            Glide.with((FragmentActivity) this).asBitmap().load(userInfo.getImageUrl()).into(this.binding.userHead);
        }
        this.binding.username.setText(userInfo.getName());
    }

    public void requestControlInfo(boolean isFirstLoad) {
        StringBuilder sb = new StringBuilder();
        sb.append(!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(this, StatusUseAccessibilityService.class.getName()));
        sb.append("");
        ILog.d("request Accessibility", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(!AutoSettingManager.getInstance().isSettingFinish());
        sb2.append("");
        ILog.d("request isSettingFinish", sb2.toString());
        if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(this, StatusUseAccessibilityService.class.getName()) || !AutoSettingManager.getInstance().isSettingFinish()) {
            ILog.d("requestControlInfo", "requestControlInfo-----return----");
            return;
        }
        this.binding.username.postDelayed(new Runnable() {
            @Override
            public void run() {
                ILog.d("requestControlInfo", "requestControlInfo---------");
                StudentMainController.getInstance().requstWeekControlTime(MainActivity.this);
                StudentMainController.getInstance().getUserWhiteApp();
                StudentMainController.getInstance().getXcxControl();
            }
        }, WorkRequest.MIN_BACKOFF_MILLIS);
        MineWorkerManager.getInstance().periodicWorkRequest(BabyApplication.getInstance());
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo(!this.isLoaded);
    }

    private boolean isNoOption() {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        Intent intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private boolean isNoSwitch() {
        if (Build.VERSION.SDK_INT >= 21) {
            long ts = System.currentTimeMillis();
            UsageStatsManager usageStatsManager = (UsageStatsManager) getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(4, 0L, ts);
            return (queryUsageStats == null || queryUsageStats.isEmpty()) ? false : true;
        }
        return true;
    }

    private void regist() {
        MyReceiver receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(DownloadService.BROADCAST_ACTION);
        intentFilter.addCategory("android.intent.category.DEFAULT");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    public void download(String url) {
        Intent myserviceIntent = new Intent(this, DownloadService.class);
        myserviceIntent.setData(Uri.parse(url));
        startService(myserviceIntent);
    }

    private boolean isIgnoringBatteryOptimizations() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager == null || Build.VERSION.SDK_INT < 23) {
            return false;
        }
        boolean isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        return isIgnoring;
    }

    public void requestIgnoreBatteryOptimizations() {
        try {
            Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadApp() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        showLoad();
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.parents_statistic, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                dissLoad();
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        JSONObject data = msg.getJSONObject("data");
                        int duration = data.getInt("duration");
                        int stepNumber = data.getInt("stepNumber");
                        AppInfo appInfo = new AppInfo();
                        if (Tools.objIsNullStr(data.get("useMostApp"))) {
                            Gson gson = new Gson();
                            appInfo = (AppInfo) gson.fromJson(data.getJSONObject("useMostApp").toString(), AppInfo.class);
                        }
                        refAppView(duration, stepNumber, appInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Tools.showAlert3(MainActivity.this, e.getMessage());
                    }
                }
            }
        });
    }

    public void refAppView(int duration, int stepNumber, AppInfo appInfo) {
        TextView textView = this.binding.appTimeTxt;
        textView.setText("今日已经使用了" + (duration / 60) + "分钟");
        if (Tools.objIsNullStr(appInfo.getAppName())) {
            this.binding.appMoreTxt.setText("暂无使用APP");
        } else {
            TextView textView2 = this.binding.appMoreTxt;
            textView2.setText("使用最多的APP " + appInfo.getAppName());
        }
        TextView textView3 = this.binding.appStepTxt;
        textView3.setText("步数" + stepNumber);
    }

    public void loadappmsg() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        parameters.put("recordDate", Tools.timeFormat(new Date(), "yyyy-MM-dd"));
        showLoad();
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.appUseRecords_appuserecord, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                dissLoad();
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        JSONArray data = msg.getJSONArray("data");
                        if (data.length() == 0) {
                            JSONObject mlist = new JSONObject();
                            mlist.put("useTime", "今天暂无使用APP");
                            mlist.put("appList", new JSONArray());
                            refAppview(mlist);
                        }
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            String userTime = object.getString("useTime");
                            JSONArray applist = object.getJSONArray("appList");
                            JSONArray alist = new JSONArray();
                            for (int j = 0; j < applist.length(); j++) {
                                Gson gson = new Gson();
                                AppInfo appInfo = (AppInfo) gson.fromJson(applist.get(j).toString(), AppInfo.class);
                                alist.put(appInfo);
                            }
                            JSONObject mlist2 = new JSONObject();
                            mlist2.put("useTime", userTime);
                            mlist2.put("appList", alist);
                            if (i == 0) {
                                refAppview(mlist2);
                                return;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void refAppview(JSONObject mlist) {
        LinearLayout convertView = (LinearLayout) findViewById(R.id.userDesBtn);
        try {
            String userTime = mlist.getString("useTime");
            JSONArray alist = mlist.getJSONArray("appList");
            TextView timelal = (TextView) convertView.findViewById(R.id.timelal);
            timelal.setText(userTime);
            for (int i = 0; i < 3; i++) {
                LinearLayout appview = (LinearLayout) convertView.findViewWithTag(((i + 1) * 1000) + "");
                if (i < alist.length()) {
                    appview.setVisibility(View.VISIBLE);
                } else {
                    appview.setVisibility(View.GONE);
                }
            }
            int j = 0;
            while (j < alist.length() && j <= 2) {
                int tag = (j + 1) * 1000;
                LinearLayout linearLayout = (LinearLayout) convertView.findViewWithTag(tag + "");
                AppInfo appInfo = (AppInfo) alist.get(j);
                ImageView img1 = (ImageView) convertView.findViewWithTag((tag + 1) + "");
                TextView txt1 = (TextView) convertView.findViewWithTag((tag + 2) + "");
                TextView txt2 = (TextView) convertView.findViewWithTag((tag + 3) + "");
                ProgressBar probar = (ProgressBar) convertView.findViewWithTag((tag + 4) + "");
                String userTime2 = userTime;
                Glide.with((FragmentActivity) this).asBitmap().load("http://139.9.121.96:8281" + appInfo.getAppImage()).into(img1);
                probar.setMax(1440);
                probar.setProgress(appInfo.getUseTime());
                String timeStr = "";
                int hour = appInfo.getUseTime() / 60;
                int min = appInfo.getUseTime() % 60;
                if (hour > 0) {
                    timeStr = hour + "时";
                }
                txt1.setText(appInfo.getAppName());
                txt2.setText(timeStr + min + "分");
                j++;
                userTime = userTime2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventParentChangeBindMode event) {
        ILog.d("EventParentChangeBindMode", "---EventParentChangeBindMode-----");
        refUi(null, false);
        Toast.makeText(BabyApplication.getInstance(), "收到新的指令", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BroadcastManager.sendAccessibilityStop(this);
        WebSocketManager.getInstance().onDestroy();
    }
}
