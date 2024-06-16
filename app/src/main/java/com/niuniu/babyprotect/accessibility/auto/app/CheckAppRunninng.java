package com.niuniu.babyprotect.accessibility.auto.app;

import android.app.ActivityManager;
import android.content.Context;
import java.util.List;
public class CheckAppRunninng {
    public static boolean isServiceWork(Context mContext, String serviceName) {
        ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(50);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
