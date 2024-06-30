package com.niu.protect.accessibility.auto.service.tmp;

import android.os.Build;

import com.niu.protect.accessibility.auto.service.App;
import com.niu.protect.accessibility.auto.service.Mgr;

import org.litepal.LitePal;

import java.util.Iterator;
import java.util.List;

public class GuardAppMgr {

        public void mo23043() {
            if (Mgr.m24656().m24659()) {
                Iterator<LauncherBean> it = new LauncherAppGetHelper(App.mContext).m26064().iterator();
                while (it.hasNext()) {
                    mo14474(it.next().packageName);
                }
                mo14474("com.android.settings");
            }
        }


        public void mo14474(String str) {
            if (Build.VERSION.SDK_INT < 24 || !Mgr.m24656().m24659()) {
                return;
            }
            SecureUtils.m24081(new String[]{str}, false);
        }


        public void mo23044(boolean z) {
            if (Mgr.m24656().m24659()) {
                List<AppSuperMgrBean> m24183 = ChildDBManager.m24183();
                if (z) {
                    Iterator<AppSuperMgrBean> it = m24183.iterator();
                    while (it.hasNext()) {
                        Mgr.m24657().mo14474(it.next().getPackageName());
                    }
                    return;
                }
                if (Mgr.m15110().mo14472()) {
                    Iterator<AppSuperMgrBean> it2 = m24183.iterator();
                    while (it2.hasNext()) {
                        Mgr.m24657().mo23045(it2.next().getPackageName());
                    }
                }
            }
        }

        public void mo14475(boolean z) {
            if (!Mgr.m24656().m24659() || Mgr.m15110().mo14471()) {
                return;
            }
            List<AppSuperMgrBean> m24187 = ChildDBManager.m24187(1);
            if (z) {
                Iterator<AppSuperMgrBean> it = m24187.iterator();
                while (it.hasNext()) {
                    Mgr.m24657().mo23045(it.next().getPackageName());
                }
                return;
            }
            Iterator<AppSuperMgrBean> it2 = m24187.iterator();
            while (it2.hasNext()) {
                Mgr.m24657().mo14474(it2.next().getPackageName());
            }
        }

        public void mo23045(String str) {
            if (Build.VERSION.SDK_INT < 24 || !Mgr.m24656().m24659()) {
                return;
            }
            SecureUtils.m24081(new String[]{str}, false);
            SecureUtils.m24081(new String[]{str}, true);
        }

        public void mo23046(String str, int i) {
            if (Build.VERSION.SDK_INT < 24 || !Mgr.m24656().m24659()) {
                return;
            }
            SecureUtils.m24081(new String[]{str}, false);
            SecureUtils.m24081(new String[]{str}, true);
            ChildDBManager.m14914(str, i);
        }
    }

