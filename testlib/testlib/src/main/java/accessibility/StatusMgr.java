package accessibility;

import accessibility.lib.ConfigKey;
import accessibility.lib.DataKV;

public class StatusMgr {
    public boolean mo23037() {
        return DataKV.m25858("is_activity_super", false);
    }

    public boolean mo14471() {
        return UserUtils.m24165();
    }

    public void mo23038(int i) {
        UserUtils.m24166(i == 0);
    }

    public void mo23039(boolean z) {
        DataKV.m25863("is_app_switch_open", z);
        SafeGuardManager.m25638(App.mContext).m25642(z);
    }

    public boolean mo23040() {
        return DataKV.m25854("model_choose", 0) == 2;
    }

    public boolean mo14472() {
        return DataKV.m25857("is_app_switch_open", true);
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
