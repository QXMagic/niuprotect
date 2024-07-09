package accessibility.lib;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.os.UserHandle;

import java.util.List;

public abstract class LauncherAppsCompat {

    public static final Object f23140 = new Object();

    public static LauncherAppsCompat f14103;

    public static LauncherAppsCompat m25586(Context context) {
        LauncherAppsCompat launcherAppsCompat;
        synchronized (f23140) {
            if (f14103 == null) {
                f14103 = new LauncherAppsCompatVL(context.getApplicationContext());
            }
            launcherAppsCompat = f14103;
        }
        return launcherAppsCompat;
    }

    /* renamed from: 肌緭 */
    public abstract List<LauncherActivityInfo> mo15434(String str, UserHandle userHandle);
}

