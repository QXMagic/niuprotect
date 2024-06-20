package com.niu.protect.manager;

import android.content.Context;
import com.niu.protect.model.UserInfo;
public class VipCheckManager {
    public static boolean checkVip(Context _context, boolean showDialog) {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(_context);
        if (userInfo == null) {
            return false;
        }
        long timea = System.currentTimeMillis();
        long etime = userInfo.getExpireTimeStamp();
        if (timea > etime) {
            return false;
        }
        return true;
    }
}
