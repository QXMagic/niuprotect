package com.niu.protect.manager;

import android.content.Context;

import com.niu.protect.core.Constants;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;
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
        Tools.savePertTop(Constants.MainInstance.getContext(), status);
    }

    public int getProtectStatus() {
        if (this.protectStatus == -2) {
            this.protectStatus = getProtectStatus(Constants.MainInstance.getContext());
            ILog.d("protectStatus--", this.protectStatus + "");
        }
        return this.protectStatus;
    }
}
