package com.niu.protect.accessibility.auto.service.tmp;

import android.graphics.Bitmap;

public class LauncherBean implements InterfaceC3920 {
    public String appName;
    public Bitmap mIconBitmap;
    public String packageName;

    public Bitmap getAppIcon() {
        return this.mIconBitmap;
    }

    @Override // p386.InterfaceC3920
    public String getAppName() {
        return this.appName;
    }

    @Override // p386.InterfaceC3920
    public String getPackageName() {
        return this.packageName;
    }
}
