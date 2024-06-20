package com.niu.protect.accessibility.auto.service;

import android.view.accessibility.AccessibilityEvent;
import com.niu.protect.tools.ILog;
public class GetAppSettingInfoAccessibility extends BaseAccessibility {
    private static final String TAG = "OppoAccessibility.class";
    private static final int openWaiteTime = 500;
    private static final int waitTime = 100;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        oppoAccess(event);
    }

    private void oppoAccess(AccessibilityEvent event) {
        ILog.d(TAG, "event--" + event.getEventType() + "--32");
        if (event.getClassName() != null && event.getClassName().toString().startsWith("com")) {
            ILog.d(TAG, "event--" + event.getAction());
            ILog.d(TAG, "event-getPackageName-" + ((Object) event.getPackageName()));
            ILog.d(TAG, "event-getClassName-" + ((Object) event.getClassName()));
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName() != null && event.getClassName().toString().startsWith("com")) {
            normalModeSleep(500L);
            ILog.d(TAG, "event--" + event.getAction());
            ILog.d(TAG, "event-getPackageName-" + ((Object) event.getPackageName()));
            ILog.d(TAG, "event-getClassName-" + ((Object) event.getClassName()));
        }
    }

    private void settingBreeno(int eventType, String packageName, String className) {
    }

    private void settingAutoStart(int eventType, String packageName, String className) {
    }

    private void settingBackgroundRunning(int eventType, String packageName, String className) {
    }

    private void settingUseInfo(int eventType, String packageName, String className) {
    }

    private void settingNotification(int eventType, String packageName, String className) {
    }

    private void settingOverlayWindows(int eventType, String packageName, String className) {
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
