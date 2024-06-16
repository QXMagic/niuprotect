package com.niuniu.babyprotect.keepalive;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import com.umeng.message.MsgConstant;
import java.util.List;
public class ServiceUtils {
    public static boolean isServiceRunning(Context context, String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(MsgConstant.KEY_ACTIVITY);
        List<ActivityManager.RunningServiceInfo> infos = activityManager.getRunningServices(200);
        for (ActivityManager.RunningServiceInfo info : infos) {
            if (TextUtils.equals(info.service.getClassName(), serviceName)) {
                return true;
            }
        }
        return false;
    }
}
