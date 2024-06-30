package com.niu.protect.accessibility.auto.service.tmp;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.UserHandle;

import java.util.List;

public class LauncherAppsCompatVL extends LauncherAppsCompat {

    public final LauncherApps f23139;

    public LauncherAppsCompatVL(Context context) {
        this.f23139 = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
    }

    @Override // p451.LauncherAppsCompat
    public List<LauncherActivityInfo> mo15434(String str, UserHandle userHandle) {
        return this.f23139.getActivityList(str, userHandle);
    }
}
