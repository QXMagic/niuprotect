package com.niuniu.babyprotect.manager;

import android.content.Context;
import com.niuniu.babyprotect.model.UserInfo;
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
