package com.niuniu.babyprotect.manager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.niuniu.babyprotect.network.StudentBaseUrl;
public class CheckInstallMineBrower {
    public static void checkInstallBre(Context context) {
        checkApkExist(context, StudentBaseUrl.brwPageName);
    }

    private static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, 8192);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
