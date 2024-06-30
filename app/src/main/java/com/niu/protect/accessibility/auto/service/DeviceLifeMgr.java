package com.niu.protect.accessibility.auto.service;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;

import com.niu.protect.accessibility.auto.service.tmp.SecureUtils;
import com.niu.protect.core.Constants;
import com.niu.protect.tools.ILog;

public class DeviceLifeMgr {
    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    public void m24463(Context context, Intent intent) {
        Mgr.m24656().m15111();
    }

    public void m15022(Context context, Intent intent) {
        Mgr.m24656().m15111();
        boolean m24077 = SecureUtils.m24077();
        boolean m24084 = SecureUtils.m24084();
        ILog.log("super:" + m24077 + "----" + m24084 + "--normal:" + SecureUtils.m24086());
        if (m24077 || m24084) {
            Mgr.m24658().mo22199();
        }
    }

    public void m24464(Context context, Intent intent) {
        ((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE)).setProfileName(Tools.m22771(context), Constants.APP_NAME);
    }

    public void m15023(Context context, Intent intent) {
        Mgr.m24656().m15111();
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public void m24465(Context context, Intent intent, String str) {
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public void m24466(Context context, Intent intent) {
    }
}
