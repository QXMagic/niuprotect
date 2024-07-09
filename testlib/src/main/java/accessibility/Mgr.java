package accessibility;

import android.content.Context;

import accessibility.lib.GuardAppMgr;
import accessibility.lib.SecureUtils;

public class Mgr {

    public static volatile Mgr instance;
//
    public final GuardAppMgr guardMgr;
//
//    /* renamed from: 肌緭 */
    public final GuardIntercept guardIntercept;
//
//    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public final StatusMgr statusMgr;
//
    public boolean isOwnerApp = SecureUtils.isOwnerApp();
//
    public Mgr() {
        this.guardIntercept = new GuardIntercept();
        this.guardMgr = new GuardAppMgr();
        this.statusMgr = new StatusMgr();
    }
//
    public static Mgr getInstance() {
        if (instance == null) {
            synchronized (Mgr.class) {
                if (instance == null) {
                    instance = new Mgr();
                }
            }
        }
        return instance;
    }

    public static StatusMgr getStatusMgr() {
        return getInstance().statusMgr;
    }
    public static GuardAppMgr getGuardMgr() {
        return getInstance().guardMgr;
    }
    public static GuardIntercept getGuardIntercept() {
        return getInstance().guardIntercept;
    }
    public boolean isOwnerApp() {
        return this.isOwnerApp;
    }
    public Context context() {
        Context context = App.mContext;
        return context != null ? context : AccessibilityHelperService.instance;
    }
//
    public void init() {
        this.isOwnerApp = SecureUtils.isOwnerApp();
    }
}

