package com.niuniu.babyprotect.manager;

import android.view.accessibility.AccessibilityEvent;
public class XiaoChengXuControllerManager {
    private static final String MINI_GAME_PLUGIN_CLASS_NAME = "com.tencent.mm.plugin.game";
    private static final String MINI_GAME_TAB_CLASS_NAME = "com.tencent.mm.plugin.game.ui.chat_tab.GameWebTabUI";
    private static final String MINI_PROGRAM_CLASS_NAME = "com.tencent.mm.plugin.appbrand.ui.AppBrandUI";
    private static final String MINI_PROGRAM_PACKAGE_NAME = "com.tencent.mm";
    private static XiaoChengXuControllerManager instance;
    AccessibilityEvent mEvent;

    public static XiaoChengXuControllerManager getInstance() {
        if (instance == null) {
            synchronized (XiaoChengXuControllerManager.class) {
                if (instance == null) {
                    instance = new XiaoChengXuControllerManager();
                }
            }
        }
        return instance;
    }

    public boolean controllerOpenXiaochengxu(AccessibilityEvent event) {
        this.mEvent = event;
        if (event != null) {
            int eventType = event.getEventType();
            if (eventType == 32) {
                String packageName = event.getPackageName().toString();
                String className = event.getClassName().toString();
                if (packageName.equals(MINI_PROGRAM_PACKAGE_NAME)) {
                    if (className.contains(MINI_PROGRAM_CLASS_NAME) || className.contains(MINI_GAME_PLUGIN_CLASS_NAME) || className.equals(MINI_GAME_TAB_CLASS_NAME)) {
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
