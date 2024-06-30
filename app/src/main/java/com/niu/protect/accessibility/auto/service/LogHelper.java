package com.niu.protect.accessibility.auto.service;

import androidx.annotation.RequiresPermission;

import com.blankj.utilcode.util.NetworkUtils;

public class LogHelper {
    private static LogHelper instance;
    public static LogHelper m27986() {
        return instance;
    }

    public static void m27987(String s) {
    }

    public static void m27985(String startWorkNow) {
    }

    public void m27999(String msg) {
    }

    public void m16303(String app管理, String s) {
    }

    public void m27996(String message) {
    }
}


class NetWorkUtils{
    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    /* renamed from: 肌緭 */
    public static boolean m14317() {
        return NetworkUtils.isConnected();
    }

}

