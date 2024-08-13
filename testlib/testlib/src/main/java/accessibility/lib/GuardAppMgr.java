package accessibility.lib;

import android.os.Build;

import java.util.Iterator;
import java.util.List;

import accessibility.App;
import accessibility.Mgr;

public class GuardAppMgr {

        public void mo23043() {
            if (Mgr.getInstance().isOwnerApp()) {
                Iterator<LauncherBean> it = new LauncherAppGetHelper(App.mContext).m26064().iterator();
                while (it.hasNext()) {
                    mo14474(it.next().packageName);
                }
                mo14474("com.android.settings");
            }
        }

    public boolean mo22203() {
        if (DataKV.m25854("super_statue", 0) == 1) {
            return true;
        }
        return mo22192();
    }
    public boolean mo22192() {
        int r0 = (SecureUtils.m24093() && SecureUtils.m24091(App.mContext)) ? 1 : 0;
        DataKV.m15531("super_statue", r0);
        return r0>0;
    }


    public void mo14474(String str) {
            if (Build.VERSION.SDK_INT < 24 || !Mgr.getInstance().isOwnerApp()) {
                return;
            }
            SecureUtils.m24081(new String[]{str}, false);
        }


        public void mo23044(boolean z) {
            if (Mgr.getInstance().isOwnerApp()) {
                List<AppSuperMgrBean> m24183 = ChildDBManager.m24183();
                if (z) {
                    Iterator<AppSuperMgrBean> it = m24183.iterator();
                    while (it.hasNext()) {
                        Mgr.getGuardMgr().mo14474(it.next().getPackageName());
                    }
                    return;
                }
                if (Mgr.getStatusMgr().mo14472()) {
                    Iterator<AppSuperMgrBean> it2 = m24183.iterator();
                    while (it2.hasNext()) {
                        Mgr.getGuardMgr().mo23045(it2.next().getPackageName());
                    }
                }
            }
        }

        public void mo14475(boolean z) {
            if (!Mgr.getInstance().isOwnerApp() || Mgr.getStatusMgr().mo14471()) {
                return;
            }
            List<AppSuperMgrBean> m24187 = ChildDBManager.m24187(1);
            if (z) {
                Iterator<AppSuperMgrBean> it = m24187.iterator();
                while (it.hasNext()) {
                    Mgr.getGuardMgr().mo23045(it.next().getPackageName());
                }
                return;
            }
            Iterator<AppSuperMgrBean> it2 = m24187.iterator();
            while (it2.hasNext()) {
                Mgr.getGuardMgr().mo14474(it2.next().getPackageName());
            }
        }

        public void mo23045(String str) {
            if (Build.VERSION.SDK_INT < 24 || !Mgr.getInstance().isOwnerApp()) {
                return;
            }
            SecureUtils.m24081(new String[]{str}, false);
            SecureUtils.m24081(new String[]{str}, true);
        }

        public void mo23046(String str, int i) {
            if (Build.VERSION.SDK_INT < 24 || !Mgr.getInstance().isOwnerApp()) {
                return;
            }
            SecureUtils.m24081(new String[]{str}, false);
            SecureUtils.m24081(new String[]{str}, true);
            ChildDBManager.m14914(str, i);
        }
    }

