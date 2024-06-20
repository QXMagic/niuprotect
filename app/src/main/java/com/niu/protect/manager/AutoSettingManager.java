package com.niu.protect.manager;

import android.content.Context;
import com.niu.protect.tools.ILog;
public class AutoSettingManager {
    public static final int AUTO_SETTING_AUTOSETTING = 2;
    public static final int AUTO_SETTING_BYHAND = 1;
    public static final int AUTO_SETTING_FINISH = 3;
    public static final int AUTO_SETTING_NOT_SET = 0;
    private static AutoSettingManager instance;
    private int autoSettingStatus = 0;

    public static AutoSettingManager getInstance() {
        if (instance == null) {
            synchronized (AutoSettingManager.class) {
                if (instance == null) {
                    instance = new AutoSettingManager();
                }
            }
        }
        return instance;
    }

    public int getAutoSettingStatus() {
        return this.autoSettingStatus;
    }

    public void setAutoSettingFinish(Context context, int isFinish) {
        this.autoSettingStatus = isFinish;
        SharedPreManager.saveAutoSettingFinish(context, isFinish);
    }

    public int getAutoSettingFinish() {
        if (this.autoSettingStatus == 0) {
            this.autoSettingStatus = SharedPreManager.getAutoSettingFinish();
        }
        return this.autoSettingStatus;
    }

    public boolean isNeedAutoSetting() {
        getAutoSettingFinish();
        int i = this.autoSettingStatus;
        return (i == 3 || i == 1) ? false : true;
    }

    public synchronized boolean isSettingFinish() {
        getAutoSettingFinish();
        ILog.d("auto set", "------" + this.autoSettingStatus);
        return this.autoSettingStatus == 3;
    }
}
