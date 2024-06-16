package com.niuniu.babyprotect.accessibility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.niuniu.babyprotect.BuildConfig;
import com.niuniu.babyprotect.accessibility.auto.app.AppActivityTool;
import com.niuniu.babyprotect.accessibility.auto.service.BaseAccessibility;
import com.niuniu.babyprotect.action.MessageEvent;
import com.niuniu.babyprotect.broadcastReceiver.BroadcastManager;
import com.niuniu.babyprotect.manager.AutoSettingManager;
import com.niuniu.babyprotect.manager.KeepAliveManger;
import com.niuniu.babyprotect.manager.StudentMainController;
import com.niuniu.babyprotect.manager.SystemBlackAppListManager;
import com.niuniu.babyprotect.manager.SystemWhiteAppListManager;
import com.niuniu.babyprotect.manager.TempOutControlManager;
import com.niuniu.babyprotect.manager.UploadAppManager;
import com.niuniu.babyprotect.manager.UserProtectManager;
import com.niuniu.babyprotect.manager.UserWhiteAppListManager;
import com.niuniu.babyprotect.manager.WebSocketManager;
import com.niuniu.babyprotect.manager.XcxControlManager;
import com.niuniu.babyprotect.model.eventbus.EventScreenOnOrOff;
import com.niuniu.babyprotect.repository.ControllerStatusRepository;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.RomUtil;
import com.niuniu.babyprotect.tools.SystemUtil;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.login.SplashActivity;
import com.niuniu.babyprotect.ui.main.DesktopActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import atmp.consts.Constants;
public class StatusUseAccessibilityService extends BaseAccessibility {
    public static StatusUseAccessibilityService mService;
    public static int step = 0;
    String brand;
    volatile AccessibilityEvent event;
    private long remenberTIme;
    boolean roomIsVivo = false;
    boolean roomIsHuawei = false;
    boolean roomIsOppo = false;
    private final String TAG = getClass().getName();
    private String appName = "3985学生端";
    String[] backClickInfos = {"[Breeno]", "[卸载" + this.appName + "]", "卸载" + this.appName, "手机管家", "概览"};
    String[] windowsChanges = {"[快捷中心,", "[卸载" + this.appName + "]", "卸载 " + this.appName, "概览"};
    String[] blackPackageNames = {"com.huawei.intelligent"};
    List<String> eventEditDesks = new ArrayList();
    boolean isStartControl = false;
    long changeTime = System.currentTimeMillis();
    String packageNameLastTime = "";
    boolean isConnect = false;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ILog.d("mHandler--", "mHandler  packageNameLastTime:" + isStartControl);
            if (isStartControl) {
                ILog.d("mHandler--", "mHandler" + ((Object) getRootInActiveWindow().getPackageName()));
                String packName = getRootInActiveWindow().getPackageName() != null ? getRootInActiveWindow().getPackageName().toString() : null;
                checkBalckAppAndUseTime(packName);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        BroadcastManager.registerShutDownReciver(this);
        this.roomIsVivo = RomUtil.isVivo() || RomUtil.isOppo() || RomUtil.isFlyme();
        this.roomIsHuawei = RomUtil.isEmui();
        this.roomIsOppo = RomUtil.isOppo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventScreenOnOrOff event) {
        AccessibilityNodeInfo info;
        ILog.d("onMessageEvent--", "onMessageEvent  isStartControl:" + this.isStartControl);
        if (this.isStartControl && (info = getRootInActiveWindow()) != null && info.getPackageName() != null) {
            String packageName = info.getPackageName().toString();
            checkBalckAppAndUseTime(packageName);
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String str = this.TAG;
        ILog.d(str, "eventType:" + event.getEventType() + "--roomIsVivo---" + this.roomIsVivo);
        String str2 = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("eventType text:");
        sb.append(event.getText());
        ILog.d(str2, sb.toString());
        String str3 = this.TAG;
        ILog.d(str3, "eventType getPackageName:" + ((Object) event.getPackageName()));
        String str4 = this.TAG;
        ILog.d(str4, "eventType classname:" + ((Object) event.getClassName()));
        boolean z = UserProtectManager.getInstance().getProtectStatus() != -2 && AutoSettingManager.getInstance().isSettingFinish();
        this.isStartControl = z;
        if (z) {
            if (event == null || TempOutControlManager.getInstance().getTempOutTime(this)) {
                return;
            }
            luncherEvent(event);
            this.event = event;
            return;
        }
        openAccessibilityEvent(event);
    }

    private void luncherEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        String className = "";
        if (event.getPackageName() == null) {
            return;
        }
        if (event.getClassName() != null) {
            className = event.getClassName().toString();
        }
        String packagetName = event.getPackageName().toString();
        if (eventType == 1 || eventType == 32 || eventType == 4096) {
            WebSocketManager.getInstance().reconnect();
            if (packagetName.equals("com.coloros.oppoguardelf") || packagetName.contains("com.huawei.systemmanager.power")) {
                goBack();
                return;
            }
            if (eventType == 32 && !TextUtils.isEmpty(packagetName)) {
                if (this.roomIsVivo && packagetName.equals("com.vivo.upslide") && event.getText().size() == 0) {
                    return;
                }
                ILog.d("sendMessage", "packageName:" + packagetName);
                WebSocketManager webSocketManager = WebSocketManager.getInstance();
                webSocketManager.sendEventMessage(eventType + "", packagetName);
            }
            if (this.roomIsVivo) {
                if (packagetName.equals("com.android.launcher") && eventType == 4096 && event.getText().size() == 0 && className.equals("androidx.recyclerview.widget.RecyclerView")) {
                    goBack();
                }
                if (packagetName.equals("com.heytap.quicksearchbox")) {
                    goBack();
                }
            }
            if (packagetName.equals(Constants.ANDROID) || packagetName.contains("input") || packagetName.contains("input_huawei") || packagetName.contains("UniHomeLauncher") || packagetName.contains("password") || packagetName.contains("keyboard") || packagetName.contains("sogouoem") || packagetName.equals(BuildConfig.APPLICATION_ID) || packagetName.equals("com.android.launcher") || packagetName.equals("com.android.mms") || packagetName.equals("com.android.systemUi")) {
                return;
            }
            if (event.getText() != null && packagetName.equals("com.hihonor.systemmanager") && event.getText().toString().contains("应用锁")) {
                return;
            }
            if (packagetName.equals("com.coloros.assistantscreen") || packagetName.equals("com.vivo.hiboard") || packagetName.equals("com.huawei.intelligent")) {
                ILog.d(this.TAG, "负一屏幕返回 goBack()---");
                goBack();
                return;
            }
        }
        if (eventType == 2 && packagetName.contains("launcher") && event.getText().toString().contains(this.appName)) {
            ILog.d(this.TAG, "长按防止卸载返回 goBack()---");
            goBack();
        } else if (packagetName.equals("com.ss.android.ugc.aweme") && eventType == 4096) {
            checkBalckAppAndUseTime(packagetName);
        } else {
            if (this.roomIsVivo) {
                if (packagetName.contains("launcher") && eventType == 32) {
                    if (findById("com.oppo.launcher:id/btn_remove", 0) || findById("com.bbk.launcher2:id/uninstall_drop_target", 0) || findById("com.android.launcher:id/title", 0) || findClickById("com.android.launcher:id/btn_remove", 0)) {
                        if (!event.getText().toString().equals("快捷方式")) {
                            ILog.d("", "---防止卸载-");
                            goBack();
                            return;
                        }
                        return;
                    } else if (event.getText() != null && !TextUtils.isEmpty(event.getText().toString())) {
                        String text = event.getText().toString();
                        if (text.contains("最近用过的应用")) {
                            ILog.d(this.TAG, "最近用过的应用--");
                            goBack();
                            return;
                        } else if (text.contains("卸载") && className.equals("android.app.AlertDialog")) {
                            ILog.d(this.TAG, "卸载--");
                            goBack();
                            return;
                        }
                    }
                }
                if (!this.roomIsOppo && (findById("com.bbk.launcher2:id/tv_drag_rearrange", 0) || findById("com.android.launcher:id/btn_remove", 0))) {
                    ILog.d(this.TAG, "防卸载--");
                    goBack();
                    return;
                }
            }
            if (eventType == 1) {
                if (event.getText() != null && this.roomIsVivo && event.getText().toString().contains("交互池")) {
                    ILog.d(this.TAG, "交互池--");
                    return;
                }
                if (packagetName.contains("com.android") || packagetName.contains("com.bbk") || packagetName.contains("com.huawei")) {
                    int i = 0;
                    while (true) {
                        if (i >= this.backClickInfos.length) {
                            break;
                        } else if (!event.getText().toString().contains(this.backClickInfos[i])) {
                            i++;
                        } else {
                            ILog.d(this.TAG, "返回关键字--");
                            goBack();
                            break;
                        }
                    }
                }
                if (packagetName.contains("com.oppo.launcher") && event.getText().contains("清除")) {
                    Intent intent = new Intent(this, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return;
                }
            }
            if (packagetName.equals("com.android.settings") && eventType == 32) {
                List<CharSequence> eventTexts = event.getText();
                String eventText = eventTexts.toString();
                String str = this.TAG;
                ILog.d(str, "Setting info" + eventTexts.toString() + "---packagetName---" + packagetName + "--className-" + className);
                if (eventText.contains("移动") || eventText.contains("蓝牙") || eventText.contains("WLAN") || eventText.contains("通话") || eventText.contains("网络详情") || eventText.contains("输入密码") || eventText.contains("加密") || eventText.contains("•")) {
                    return;
                }
                if (!TextUtils.isEmpty(className) && className.contains("Wifi")) {
                    return;
                }
                if (this.roomIsHuawei) {
                    if (className.equals("com.android.settings.HWSettings")) {
                        gotoDefindSetting();
                        return;
                    }
                    return;
                }
                gotoDefindSetting();
                return;
            }
            if (eventType == 32) {
                int i2 = 0;
                while (true) {
                    if (i2 >= this.windowsChanges.length) {
                        break;
                    } else if (!event.getText().toString().contains(this.windowsChanges[i2])) {
                        i2++;
                    } else {
                        goBack();
                        break;
                    }
                }
                if (packagetName.equals("com.oplus.battery")) {
                    goBack();
                    return;
                }
            }
            if (eventType == 1 || eventType == 32 || eventType == 4096 || eventType == 2048) {
                if (eventType == 2048) {
                    long nowTime = System.currentTimeMillis();
                    if (nowTime - this.remenberTIme < 4000) {
                        this.remenberTIme = System.currentTimeMillis();
                        return;
                    }
                    this.remenberTIme = System.currentTimeMillis();
                }
                if (!this.packageNameLastTime.equals(packagetName)) {
                    UploadAppManager.getInstance(this).upNewInstallAPP(packagetName);
                }
                checkBalckAppAndUseTime(packagetName);
                this.changeTime = System.currentTimeMillis();
                this.packageNameLastTime = packagetName;
                if (!packagetName.equals("com.tencent.mm") || event.getClassName() == null) {
                    return;
                }
                boolean isLimit = XcxControlManager.getInstance().checkXcxInControl(this, event.getClassName().toString());
                if (isLimit) {
                    goBack();
                }
            }
        }
    }

    public synchronized void checkBalckAppAndUseTime(String packageName) {
        if (packageName != null) {
            if (!packageName.equals(BuildConfig.APPLICATION_ID) && !packageName.equals("com.oplus.wirelesssettings") && !packageName.equals("com.android.launcher.Launcher")) {
                if (UserWhiteAppListManager.getInstance().userWhiteApp(this, packageName)) {
                    return;
                }
                if (SystemWhiteAppListManager.getInstance().systemWhiteApp(this, packageName)) {
                    return;
                }
                if (SystemBlackAppListManager.getInstance().systemBlackApp(this, packageName)) {
                    goBack();
                    return;
                } else if ((packageName.contains("com.oppo") || packageName.contains("com.android") || packageName.contains("coloros") || packageName.contains("com.bbk") || packageName.equals(BuildConfig.APPLICATION_ID) || packageName.equals("com.baidu.input_vivo") || packageName.contains("contacts") || packageName.contains("deskclock") || packageName.contains("camera") || packageName.contains("com.vivo") || packageName.contains("com.huawei") || packageName.contains("launcher") || packageName.contains("com.hihonor") || packageName.contains("com.jiankong.jia")) && !packageName.toLowerCase().contains("browser") && !packageName.toLowerCase().contains("video") && !packageName.toLowerCase().contains("music") && !packageName.toLowerCase().contains("store") && !packageName.toLowerCase().contains("chrome") && !packageName.toLowerCase().contains("market") && !packageName.toLowerCase().contains("community") && !packageName.toLowerCase().contains("phonemanager") && !packageName.toLowerCase().contains("minigamecenter") && !packageName.toLowerCase().contains("hwvplayer")) {
                    return;
                } else {
                    checkCanUse(packageName);
                }
            }
            return;
        }
        ILog.d(this.TAG, "method   checkBalckAppAndUseTime package is null ");
    }

    private void checkCanUse(String packageName) {
        boolean isCantNotUse = StudentMainController.getInstance().checkNotUseTime(this);
        if (isCantNotUse) {
            String str = this.TAG;
            ILog.d(str, "can't not use:" + packageName);
            if (packageName.toLowerCase().contains("browser") || packageName.toLowerCase().contains("video") || packageName.toLowerCase().contains("store") || packageName.toLowerCase().contains("music") || packageName.toLowerCase().contains("market") || packageName.toLowerCase().contains("community") || packageName.toLowerCase().contains("chrome") || packageName.toUpperCase().contains("hwvplayer")) {
                goBack();
            }
            if (!packageName.contains("com.oppo") && !packageName.contains("com.android") && !packageName.contains("coloros") && !packageName.contains("com.bbk") && !packageName.contains("launcher")) {
                goBack();
                return;
            }
            return;
        }
        boolean isCannotUseApp = StudentMainController.getInstance().appCanUse(packageName);
        if (isCannotUseApp) {
            String str2 = this.TAG;
            ILog.d(str2, "can't use---" + packageName);
            goBack();
            return;
        }
        String str3 = this.TAG;
        ILog.d(str3, "can use---" + packageName);
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        ILog.d("------------", "---onServiceConnected--------");
        mService = this;
        this.brand = SystemUtil.getDeviceBrand();
        String str = this.TAG;
        ILog.d(str, Tools.getAutoSet(this) + "getAutoSet");
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Tools.getAutoSet(this) == 1) {
            Tools.saveAutoSet(this, 0);
            AppActivityTool.backApp(this);
        }
        ControllerStatusRepository.getInstance().requestControlStatus(this, 1);
        BroadcastManager.sendAccessibilityStart(this);
        this.isConnect = true;
        KeepAliveManger.getInstance().keepAliveByTowService(this);
    }

    @Override
    public void onInterrupt() {
        mService = null;
        this.isStartControl = false;
        this.isConnect = false;
        ControllerStatusRepository.getInstance().requestControlStatus(this, 0);
        ILog.d("--onInterrupt-----", "-------onInterrupt---------");
    }

    private void gotoDefindSetting() {
        ILog.d("RomUtil--", "" + RomUtil.isVivo());
        if (RomUtil.isVivo()) {
            int version = Build.VERSION.SDK_INT;
            if (version >= 29) {
                AppActivityTool.showNewSetActivy(this);
            } else {
                AppActivityTool.showSetActivy(this);
            }
        } else if (RomUtil.isOppo()) {
            int version2 = Build.VERSION.SDK_INT;
            if (version2 >= 30) {
                AppActivityTool.showNewSetActivy(this);
            } else {
                AppActivityTool.showSetActivy(this);
            }
        } else if (RomUtil.isEmui()) {
            int version3 = Build.VERSION.SDK_INT;
            if (version3 >= 30) {
                AppActivityTool.showNewSetActivy(this);
            } else {
                AppActivityTool.showSetActivy(this);
            }
        }
    }

    private void goBack() {
        ILog.d("goBack-------", "goBack");
        performGlobalAction(1);
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(1);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        performGlobalAction(1);
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e3) {
            e3.printStackTrace();
        }
        performGlobalAction(2);
    }

    public void goBackHome() {
        Log.e("xxxxcser", "111112");
        performGlobalAction(2);
    }

    public boolean islock() {
        int lct = Tools.getLocTask(this);
        if (lct == 1) {
            performGlobalAction(3);
            Tools.saveLocTask(this, 2);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mService = null;
        ILog.d("--onDestroy-----", "----------------");
        EventBus.getDefault().unregister(this);
        BroadcastManager.unRegisterShutDownReciver(this);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(MessageEvent event) {
        Log.d("---", "-----");
        clickMenu();
    }

    public void clickMenu() {
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(3);
    }

    private void startActivity() {
        Intent intent = new Intent(this, DesktopActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long now = Calendar.getInstance().getTimeInMillis();
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, now, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, now, pendingIntent);
        }
    }
}
