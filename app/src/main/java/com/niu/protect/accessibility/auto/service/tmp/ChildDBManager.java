package com.niu.protect.accessibility.auto.service.tmp;

import org.litepal.LitePal;

import java.util.List;

public class ChildDBManager {
    public static List<AppSuperMgrBean> m24183() {
        return LitePal.findAll(AppSuperMgrBean.class, new long[0]);
    }

    public static List<AppSuperMgrBean> m24187(int i) {
        return LitePal.where("type= ?", String.valueOf(i)).find(AppSuperMgrBean.class);
    }

    public static void m14914(String str, int i) {
        if (str == null) {
            return;
        }
        AppSuperMgrBean appSuperMgrBean = (AppSuperMgrBean) LitePal.where("packageName = ?", str).findFirst(AppSuperMgrBean.class);
        if (appSuperMgrBean == null) {
            AppSuperMgrBean appSuperMgrBean2 = new AppSuperMgrBean();
            appSuperMgrBean2.setPackageName(str);
            appSuperMgrBean2.setType(i);
            appSuperMgrBean2.save();
            return;
        }
        if (appSuperMgrBean.getType() != i) {
            appSuperMgrBean.setType(i);
            appSuperMgrBean.update();
        }
    }

}
