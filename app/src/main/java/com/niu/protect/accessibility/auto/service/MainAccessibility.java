package com.niu.protect.accessibility.auto.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.accessibility.AccessibilityEvent;
import com.niu.protect.BabyApplication;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.accessibility.auto.device.info.DeviceAccessiFactory;
import java.util.List;
public class MainAccessibility extends BaseAccessibility {
    private static final int START_ACCESS = 2;
    private static final String TAG = "OppoAccessibility.class";
    private static final int waitTime = 200;
    AccessibilityEvent mEvent;
    boolean stop = false;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                MainAccessibility mainAccessibility = MainAccessibility.this;
                mainAccessibility.openAccessibilityEvent(mainAccessibility.mEvent);
            }
        }
    };
    private List<PageInfoModel> mVivoInfos = DeviceAccessiFactory.createDeviceInfo(BabyApplication.getInstance());

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        openAccessibilityEvent(event);
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
    }
}
