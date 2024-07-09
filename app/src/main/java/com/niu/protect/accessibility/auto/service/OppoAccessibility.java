package im.niu.testapp.accessibility;

import android.view.accessibility.AccessibilityEvent;
import com.niu.protect.accessibility.auto.device.OppoDeviceInfo;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.ToastUtil;
public class OppoAccessibility extends BaseAccessibility {
    private static final String TAG = "OppoAccessibility.class";
    private static final int waitTime = 100;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        oppoAccess(event);
    }

    private void oppoAccess(AccessibilityEvent event) {
        ILog.d(TAG, "event--" + event.getEventType() + "--32");
        StringBuilder sb = new StringBuilder();
        sb.append("event--");
        sb.append(event.getAction());
        ILog.d(TAG, sb.toString());
        ILog.d(TAG, "event-getPackageName-" + ((Object) event.getPackageName()));
        ILog.d(TAG, "event-getClassName-" + ((Object) event.getClassName()));
        int eventType = event.getEventType();
        normalModeSleep(100L);
        if (event.getPackageName() != null && event.getClassName() != null) {
            String packageName = event.getPackageName().toString();
            String className = event.getClassName().toString();
            if (packageName.equals("com.jinli.accessibility") && eventType == 32 && className.equals("com.jinli.accessibility.MainActivity")) {
                normalModeSleep(100L);
                clickMineAPP();
            } else if (this.step == 0) {
                settingBreeno(eventType, packageName, className);
            } else if (this.step == 2) {
                ILog.d(TAG, "进入第二步");
                settingAutoStart(eventType, packageName, className);
            } else if (this.step == 3) {
                settingBackgroundRunning(eventType, packageName, className);
            } else if (this.step == 4) {
                settingUseInfo(eventType, packageName, className);
            } else if (this.step == 5) {
                settingNotification(eventType, packageName, className);
            } else if (this.step == 6) {
                settingOverlayWindows(eventType, packageName, className);
            }
        }
    }

    private void settingBreeno(int eventType, String packageName, String className) {
        if (packageName.equals("com.android.settings") && eventType == 32 && className.equals(OppoDeviceInfo.StepOneBreeno.SETTING_Class_name)) {
            ILog.d(TAG, "event--Breeno--");
            normalModeSleep(100L);
            for (int times = 0; times < 3; times++) {
                boolean clickSuccess = scroll2PositionClick(this, OppoDeviceInfo.StepOneBreeno.BREENO, "com.android.settings:id/recycler_view", 1, 1);
                if (clickSuccess) {
                    return;
                }
            }
        } else if (packageName.equals(OppoDeviceInfo.StepOneBreeno.BREENO_setting_PackageName) && className.equals(OppoDeviceInfo.StepOneBreeno.BREENO_setting_class)) {
            ILog.d(TAG, "event--Breeno--");
            normalModeSleep(100L);
            recycleCheckBox(getRootInActiveWindow(), OppoDeviceInfo.StepOneBreeno.BREENO_Click_Text, 1);
            if (!OppoDeviceInfo.StepOneBreeno.STATUS_CLICKED) {
                ILog.d(TAG, "第一步已经完成");
            } else {
                ILog.d(TAG, "需要继续点击");
                boolean click = findTextAndClickParent(OppoDeviceInfo.StepOneBreeno.BREENO_Click_Text, 0);
                if (click) {
                    OppoDeviceInfo.StepOneBreeno.STATUS_CLICKED = true;
                }
            }
            this.step = 2;
            BackClick();
            BackClick();
        }
    }

    private void settingAutoStart(int eventType, String packageName, String className) {
        if (packageName.equals("com.android.settings") && eventType == 32 && className.equals("com.android.settings.applications.InstalledAppDetailsTop")) {
            normalModeSleep(100L);
            recycleCheckBox(getRootInActiveWindow(), "允许自动启动", 2);
            if (OppoDeviceInfo.StepTwoAutoStart.STATUS_CLICKED) {
                ILog.d(TAG, "第二步已经完成");
                ToastUtil.show("第二步已经完成");
            } else {
                ILog.d(TAG, "需要继续点击");
                ToastUtil.show("第二步已经完成");
                findTextAndClickParent("允许自动启动", 0);
            }
            this.step = 3;
            BackClick();
        }
    }

    private void settingBackgroundRunning(int eventType, String packageName, String className) {
        if (packageName.equals("com.android.settings") && eventType == 32 && className.equals("com.android.settings.applications.InstalledAppDetailsTop")) {
            for (int times = 0; times < 3 && !scroll2PositionClick(this, "耗电保护", "com.android.settings:id/recycler_view", 1, 1); times++) {
                normalModeSleep(100L);
            }
        } else if (packageName.equals("com.coloros.oppoguardelf") && className.equals("com.coloros.powermanager.fuelgaue.PowerAppsBgSetting")) {
            ILog.d(TAG, "event--StepThree--");
            normalModeSleep(100L);
            recycleCheckBox(getRootInActiveWindow(), "允许后台运行", 3);
            if (OppoDeviceInfo.StepThreeBackRunning.STATUS_CLICKED) {
                ILog.d(TAG, "第三步已经完成");
                ToastUtil.show("第三步已经完成");
            } else {
                boolean clickSuccess = findTextClickByParent("允许后台运行", 0);
                if (clickSuccess) {
                    OppoDeviceInfo.StepThreeBackRunning.STATUS_CLICKED = true;
                    ToastUtil.show("第三步完成");
                }
                ILog.d(TAG, "需要继续点击clickSuccess" + clickSuccess);
            }
            this.step = 4;
            BackClick();
            BackClick();
        }
    }

    private void settingUseInfo(int eventType, String packageName, String className) {
        if (packageName.equals("com.android.settings") && eventType == 32 && className.equals("com.android.settings.Settings$UsageAccessSettingsActivity")) {
            normalModeSleep(100L);
            for (int times = 0; times < 10; times++) {
                boolean clickSuccess = scroll2PositionClick(this, "Accessibility", "com.android.settings:id/recycler_view", 1, 1);
                if (!clickSuccess) {
                    normalModeSleep(100L);
                } else {
                    return;
                }
            }
        } else if (packageName.equals("com.android.settings") && className.equals("com.android.settings.SubSettings")) {
            ILog.d(TAG, "event--StepFourAppUse--");
            normalModeSleep(100L);
            recycleCheckBox(getRootInActiveWindow(), "允许查看使用情况", 4);
            if (!OppoDeviceInfo.StepFourAppUse.STATUS_CLICKED) {
                ILog.d(TAG, "需要继续点击");
                boolean click = findTextAndClickParent("允许查看使用情况", 0);
                if (click) {
                    OppoDeviceInfo.StepFourAppUse.STATUS_CLICKED = true;
                    ToastUtil.show("第四步已经完成");
                }
            } else {
                ILog.d(TAG, "第四步已经完成");
                ToastUtil.show("第四步已经完成");
            }
            this.step = 5;
            BackClick();
            normalModeSleep(300L);
            BackClick();
        }
    }

    private void settingNotification(int eventType, String packageName, String className) {
        if (packageName.equals("com.android.settings") && eventType == 32 && className.equals("com.android.settings.applications.InstalledAppDetailsTop")) {
            for (int times = 0; times < 3 && !scroll2PositionClick(this, "通知管理", "com.android.settings:id/recycler_view", 1, 1); times++) {
                normalModeSleep(100L);
            }
        } else if (packageName.equals("com.coloros.notificationmanager") && className.equals("com.coloros.notificationmanager.AppNotificationSettingsActivity")) {
            ILog.d(TAG, "event--Breeno--");
            normalModeSleep(100L);
            recycleCheckBox(getRootInActiveWindow(), "允许通知", 5);
            if (OppoDeviceInfo.StepFiveNOtification.STATUS_CLICKED) {
                ILog.d(TAG, "第五步已经完成");
                ToastUtil.show("第五步已经完成");
            } else {
                boolean clickSuccess = findTextClickByParent("允许通知", 0);
                if (clickSuccess) {
                    OppoDeviceInfo.StepFiveNOtification.STATUS_CLICKED = true;
                    ToastUtil.show("第五步已经完成");
                }
                ILog.d(TAG, "需要继续点击clickSuccess" + clickSuccess);
            }
            this.step = 6;
            BackClick();
            normalModeSleep(300L);
            BackClick();
        }
    }

    private void settingOverlayWindows(int eventType, String packageName, String className) {
        if (packageName.equals("com.android.settings") && eventType == 32 && className.equals("com.android.settings.Settings$OverlaySettingsActivity")) {
            for (int times = 0; times < 20 && !findRecycleViewScrollClick(this, "Accessibility", "com.android.settings:id/recycler_view", 1, 1); times++) {
                normalModeSleep(300L);
            }
        } else if (packageName.equals("com.android.settings") && className.equals("com.android.settings.SubSettings")) {
            ILog.d(TAG, "event--悬浮框--");
            normalModeSleep(100L);
            recycleCheckBox(getRootInActiveWindow(), "允许显示在其他应用的上层", 6);
            if (OppoDeviceInfo.StepSixOverlayWindows.STATUS_CLICKED) {
                ILog.d(TAG, "第六步已经完成");
                ToastUtil.show("第六步已经完成");
            } else {
                boolean clickSuccess = findTextClickByParent("允许显示在其他应用的上层", 0);
                if (clickSuccess) {
                    OppoDeviceInfo.StepSixOverlayWindows.STATUS_CLICKED = true;
                    ToastUtil.show("第六步已经完成");
                }
                ILog.d(TAG, "需要继续点击clickSuccess" + clickSuccess);
            }
            this.step = 7;
            BackClick();
            normalModeSleep(300L);
            BackClick();
        }
    }

    private void clickMineAPP() {
        if (this.step == 0) {
            clickButton("com.jinli.accessibility:id/button_2");
        } else if (this.step == 2) {
            clickButton("com.jinli.accessibility:id/button_4");
        } else if (this.step == 3) {
            clickButton("com.jinli.accessibility:id/button_4");
        } else if (this.step == 4) {
            clickButton("com.jinli.accessibility:id/button_5");
        } else if (this.step == 5) {
            clickButton("com.jinli.accessibility:id/button_8");
        } else if (this.step == 6) {
            clickButton("com.jinli.accessibility:id/button_7");
        }
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        ILog.d(TAG, "onServiceConnected");
    }
}
