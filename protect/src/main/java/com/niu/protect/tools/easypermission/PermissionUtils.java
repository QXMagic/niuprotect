package com.niu.protect.tools.easypermission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import java.util.Arrays;
import java.util.List;
final class PermissionUtils {
    PermissionUtils() {
    }

    static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }

    static boolean isOverOreo() {
        return Build.VERSION.SDK_INT >= 26;
    }

    static List<String> getManifestPermissions(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            return Arrays.asList(pm.getPackageInfo(context.getPackageName(), 4096).requestedPermissions);
        } catch (Exception e) {
            return null;
        }
    }

    public static void checkPermissions(Activity activity, List<String> requestPermissions) {
        List<String> manifest = getManifestPermissions(activity);
        if (manifest != null && manifest.size() != 0) {
            for (String permission : requestPermissions) {
                if (!manifest.contains(permission)) {
                    throw new RuntimeException("you must add this permission:" + permission + " to AndroidManifest");
                }
            }
        }
    }

    public static boolean isHasInstallPermission(Context context) {
        if (isOverOreo()) {
            return context.getPackageManager().canRequestPackageInstalls();
        }
        return true;
    }

    public static boolean isHasOverlaysPermission(Context context) {
        if (isOverMarshmallow()) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }
}
