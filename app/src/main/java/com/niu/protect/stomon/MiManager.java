package com.niu.protect.stomon;

import com.miui.enterprise.sdk.ApplicationManager;
import com.miui.enterprise.sdk.RestrictionsManager;
import com.niu.protect.network.StudentBaseUrl;
import java.util.List;
public class MiManager {
    private static MiManager instance;

    private MiManager() {
    }

    public static synchronized MiManager getInstance() {
        MiManager miManager;
        synchronized (MiManager.class) {
            if (instance == null) {
                instance = new MiManager();
            }
            miManager = instance;
        }
        return miManager;
    }

    public void cleanAppBlack() {
        List<String> list = ApplicationManager.getInstance().getApplicationBlackList();
        list.clear();
        ApplicationManager.getInstance().setDisallowedRunningAppList(list);
    }

    public void disRestoreFactory(boolean isopen) {
        RestrictionsManager mRestrictionsManager = RestrictionsManager.getInstance();
        if (isopen) {
            mRestrictionsManager.setControlStatus("disallow_factoryreset", 4);
        } else {
            mRestrictionsManager.setControlStatus("disallow_factoryreset", 1);
        }
    }

    public void setAppBlack(List<String> list) {
        ApplicationManager.getInstance().setDisallowedRunningAppList(list);
    }

    public void openGps(boolean isOpen) {
        RestrictionsManager mRestrictionsManager = RestrictionsManager.getInstance();
        if (isOpen) {
            mRestrictionsManager.setControlStatus("gps_state", 4);
        } else {
            mRestrictionsManager.setControlStatus("gps_state", 1);
        }
    }

    public void disSetTime(boolean isOpen) {
        RestrictionsManager mRestrictionsManager = RestrictionsManager.getInstance();
        mRestrictionsManager.setRestriction("disallow_timeset", isOpen);
    }

    public void makeAppLive(String pagename) {
        ApplicationManager mApplicationManager = ApplicationManager.getInstance();
        mApplicationManager.setApplicationSettings(pagename, 29);
        mApplicationManager.setApplicationSettings(StudentBaseUrl.brwPageName, 28);
    }

    public void noAppLive(String pagename) {
        ApplicationManager.getInstance();
        ApplicationManager.getInstance().setApplicationSettings(pagename, 0);
        ApplicationManager.getInstance().setApplicationSettings(StudentBaseUrl.brwPageName, 0);
    }
}
