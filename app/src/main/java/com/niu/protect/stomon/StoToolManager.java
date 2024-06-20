package com.niu.protect.stomon;

import android.content.Context;
import com.niu.protect.tools.SystemUtil;
import java.util.List;

import atmp.consts.Constants;

public class StoToolManager {
    private static StoToolManager instance;
    public Context context;

    private StoToolManager(Context context) {
        this.context = context;
    }

    public static synchronized StoToolManager getInstance(Context context) {
        StoToolManager stoToolManager;
        synchronized (StoToolManager.class) {
            if (instance == null) {
                instance = new StoToolManager(context);
            }
            stoToolManager = instance;
        }
        return stoToolManager;
    }

    public void cleanAppBlack() {
        if (SystemUtil.checkPhone().equals("xiaomi")) {
            MiManager.getInstance().cleanAppBlack();
        } else {
            SystemUtil.checkPhone().equals(Constants.MESSAGE_SYSTEM_SOURCE_HUAWEI);
        }
    }

    public void setAppBlack(List<String> list) {
        if (SystemUtil.checkPhone().equals("xiaomi")) {
            MiManager.getInstance().setAppBlack(list);
        } else {
            SystemUtil.checkPhone().equals(Constants.MESSAGE_SYSTEM_SOURCE_HUAWEI);
        }
    }

    public void disRestoreFactory(boolean isopen) {
        if (SystemUtil.checkPhone().equals("xiaomi")) {
            MiManager.getInstance().disRestoreFactory(isopen);
        } else {
            SystemUtil.checkPhone().equals(Constants.MESSAGE_SYSTEM_SOURCE_HUAWEI);
        }
    }

    public void openGps(boolean isOpen) {
        if (SystemUtil.checkPhone().equals("xiaomi")) {
            MiManager.getInstance().openGps(isOpen);
        } else {
            SystemUtil.checkPhone().equals(Constants.MESSAGE_SYSTEM_SOURCE_HUAWEI);
        }
    }

    public void disSetTime(boolean isOpen) {
        if (SystemUtil.checkPhone().equals("xiaomi")) {
            MiManager.getInstance().disSetTime(isOpen);
        } else {
            SystemUtil.checkPhone().equals(Constants.MESSAGE_SYSTEM_SOURCE_HUAWEI);
        }
    }

    public void makeAppLive(String pagename) {
        if (SystemUtil.checkPhone().equals("xiaomi")) {
            MiManager.getInstance().makeAppLive(pagename);
        } else {
            SystemUtil.checkPhone().equals(Constants.MESSAGE_SYSTEM_SOURCE_HUAWEI);
        }
    }

    public void noAppLive(String pagename) {
        if (SystemUtil.checkPhone().equals("xiaomi")) {
            MiManager.getInstance().noAppLive(pagename);
        } else {
            SystemUtil.checkPhone().equals(Constants.MESSAGE_SYSTEM_SOURCE_HUAWEI);
        }
    }

    public void makeSetView(int index) {
        if (!SystemUtil.checkPhone().equals("xiaomi")) {
            SystemUtil.checkPhone().equals(Constants.MESSAGE_SYSTEM_SOURCE_HUAWEI);
        }
    }

    public void clearAllController() {
        getInstance(this.context).cleanAppBlack();
        getInstance(this.context).noAppLive(this.context.getPackageName());
        getInstance(this.context).openGps(false);
        getInstance(this.context).disSetTime(false);
        getInstance(this.context).disRestoreFactory(false);
    }
}
