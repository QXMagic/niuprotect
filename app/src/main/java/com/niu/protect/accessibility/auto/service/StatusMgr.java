package com.niu.protect.accessibility.auto.service;

import com.niu.protect.accessibility.auto.service.tmp.C6891;
import com.niu.protect.accessibility.auto.service.tmp.ConfigKey;

public class StatusMgr {
    public boolean mo23037() {
        return C6891.m25858("is_activity_super", false);
    }

    public boolean mo14471() {
        return UserUtils.m24165();
    }

    public void mo23038(int i) {
        UserUtils.m24166(i == 0);
    }

    public void mo23039(boolean z) {
        C6891.m25863("is_app_switch_open", z);
        SafeGuardManager.m25638(App.mContext).m25642(z);
    }

    public boolean mo23040() {
        return C6891.m25854("model_choose", 0) == 2;
    }

    public boolean mo14472() {
        return C6891.m25857("is_app_switch_open", true);
    }

    public void mo23041() {
        ConfigKey.m27239(0);
        UserUtils.m14906(false);
    }

    public void mo14473() {
        ConfigKey.m27239(0);
    }

    public void mo23042() {
        ConfigKey.m27239(1);
    }
}
