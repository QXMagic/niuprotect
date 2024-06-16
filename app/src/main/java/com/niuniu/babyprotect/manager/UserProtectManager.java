package com.niuniu.babyprotect.manager;

import android.content.Context;
import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
public class UserProtectManager {
    public static final int STATUS_NOT_VIP_PROTECT = -1;
    public static final int STATUS_OUT_OFF_PROTECT = -2;
    public static final int STATUS_PROTECT = 1;
    private static UserProtectManager mUserProtectManager;
    int protectStatus = -2;

    private UserProtectManager() {
    }

    public static UserProtectManager getInstance() {
        if (mUserProtectManager == null) {
            synchronized (UserProtectManager.class) {
                mUserProtectManager = new UserProtectManager();
            }
        }
        return mUserProtectManager;
    }

    private int getProtectStatus(Context context) {
        if (this.protectStatus == -2) {
            this.protectStatus = Tools.getPertTop(context);
        }
        return this.protectStatus;
    }

    public void setProtect(int status) {
        if (this.protectStatus == status) {
            return;
        }
        this.protectStatus = status;
        Tools.savePertTop(BabyApplication.getInstance(), status);
    }

    public int getProtectStatus() {
        if (this.protectStatus == -2) {
            this.protectStatus = getProtectStatus(BabyApplication.getInstance());
            ILog.d("protectStatus--", this.protectStatus + "");
        }
        return this.protectStatus;
    }
}
