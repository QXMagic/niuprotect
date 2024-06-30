package com.niu.protect.accessibility.auto.service;

import android.content.Context;

import com.niu.protect.accessibility.auto.service.tmp.GuardAppMgr;
import com.niu.protect.accessibility.auto.service.tmp.SecureUtils;

public class Mgr {

    public static volatile Mgr f22423;
//
    public final GuardAppMgr f22424;
//
//    /* renamed from: 肌緭 */
    public final BaseGuardIntercept f13676;
//
//    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public final StatusMgr f22425;
//
    public boolean f22426 = SecureUtils.m24093();
//
    public Mgr() {
        this.f13676 = new GuardIntercept();
        this.f22424 = new GuardAppMgr();
        this.f22425 = new StatusMgr();
    }
//
    public static Mgr m24656() {
        if (f22423 == null) {
            synchronized (Mgr.class) {
                if (f22423 == null) {
                    f22423 = new Mgr();
                }
            }
        }
        return f22423;
    }

    public static StatusMgr m15110() {
        return m24656().f22425;
    }
    public static GuardAppMgr m24657() {
        return m24656().f22424;
    }
    public static BaseGuardIntercept m24658() {
        return m24656().f13676;
    }
    public boolean m24659() {
        return this.f22426;
    }
    public Context m24660() {
        Context context = App.mContext;
        return context != null ? context : AccessibilityHelperService.f19276;
    }
//
//    /* renamed from: 肌緭 */
    public void m15111() {
        this.f22426 = SecureUtils.m24093();
    }
}

