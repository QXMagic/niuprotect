package com.niuniu.babyprotect.accessibility.auto.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import androidx.fragment.app.Fragment;
import com.niuniu.babyprotect.BuildConfig;
import com.niuniu.babyprotect.accessibility.OpenAccessibilitySettingHelper;
import com.niuniu.babyprotect.accessibility.StatusUseAccessibilityService;
import com.niuniu.babyprotect.broadcastReceiver.DeviceReceiver;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.main.DesktopActivity;
import com.niuniu.babyprotect.ui.main.MainActivity;
import com.niuniu.babyprotect.ui.setting.OpenQxActivity;
import com.niuniu.babyprotect.ui.setting.SysSetActivity;
import java.io.IOException;
import java.util.List;
public class AppActivityTool {
    private static DevicePolicyManager dpm;
    private static ComponentName mDeviceAdminSample;

    public static void openApp(Context context, String packagename) {
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }
        Intent resolveIntent = new Intent("android.intent.action.MAIN", (Uri) null);
        resolveIntent.addCategory("android.intent.category.LAUNCHER");
        resolveIntent.setPackage(packageinfo.packageName);
        List<ResolveInfo> resolveinfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
        if (resolveinfoList != null && resolveinfoList.size() > 0) {
            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            if (resolveinfo != null) {
                String packageName = resolveinfo.activityInfo.packageName;
                String className = resolveinfo.activityInfo.name;
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                ComponentName cn2 = new ComponentName(packageName, className);
                intent.setComponent(cn2);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return;
            }
            return;
        }
        ILog.d("AppActivityTool", "没有找到");
    }

    public static void openOtherApp(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setClassName(packageName, className);
        context.startActivity(intent);
    }

    public static void openAppPage(Context context, String packagename) {
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }
        Intent resolveIntent = new Intent("android.intent.action.MAIN", (Uri) null);
        resolveIntent.addCategory("android.intent.category.LAUNCHER");
        resolveIntent.setPackage(packageinfo.packageName);
        List<ResolveInfo> resolveinfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            String packageName = resolveinfo.activityInfo.packageName;
            String className = resolveinfo.activityInfo.name;
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            ComponentName cn2 = new ComponentName(packageName, className);
            intent.setComponent(cn2);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static void openOppoSystemSetting(Context context) {
        if (Build.MANUFACTURER.equals("OPPO")) {
            Intent[] AUTO_START_OPPO = {new Intent().setComponent(new ComponentName("com.coloros.safe", "com.coloros.safe.permission.startup.StartupAppListActivity")), new Intent().setComponent(new ComponentName("com.coloros.safe", "com.coloros.safe.permission.startupapp.StartupAppListActivity")), new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity")), new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startup.StartupAppListActivity"))};
            for (Intent intent : AUTO_START_OPPO) {
                if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                    try {
                        context.startActivity(intent);
                        return;
                    } catch (Exception e) {
                        Log.d("TAG", "OPPO - Exception: " + e.toString());
                    }
                }
            }
        }
    }

    public static void openLock(Fragment context) {
        dpm = (DevicePolicyManager) context.getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDeviceAdminSample = new ComponentName(context.getContext(), DeviceReceiver.class);
        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        intent.putExtra("android.app.extra.DEVICE_ADMIN", mDeviceAdminSample);
        intent.putExtra("android.app.extra.ADD_EXPLANATION", "开启后就可以使用锁屏功能了...");
        context.startActivityForResult(intent, 0);
    }

    public static void openNotification(Fragment fragment) {
        Intent localIntent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", fragment.getContext().getPackageName(), null));
        } else if (Build.VERSION.SDK_INT >= 21) {
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", fragment.getContext().getPackageName());
            localIntent.putExtra("app_uid", fragment.getContext().getApplicationInfo().uid);
        }
        fragment.startActivity(localIntent);
    }

    public static void openPowerSetting(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.LAUNCHER");
        ComponentName cn2 = ComponentName.unflattenFromString("com.android.settings/.Settings$HighPowerApplicationsActivity");
        intent.setComponent(cn2);
        context.startActivity(intent);
    }

    public static void ignoreBatteryOptimization(Activity activity) {
        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.getPackageName());
            ILog.d("is power", "" + hasIgnored);
            if (!hasIgnored) {
                Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(intent);
            }
        }
    }

    public static void power(Activity context) {
        Intent powerUsageIntent = new Intent("android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS");
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(powerUsageIntent, 0);
        if (resolveInfo != null) {
            context.startActivity(powerUsageIntent);
        }
    }

    public void showDesActivy(Context context) {
        Intent secondIntent = new Intent(context, DesktopActivity.class);
        secondIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP  |Intent.FLAG_ACTIVITY_CLEAR_TOP );//872480768
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, secondIntent, PendingIntent.FLAG_IMMUTABLE);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static void showNewSetActivy(Context context) {
        Intent secondIntent = new Intent(context, SysSetActivity.class);
        secondIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP  |Intent.FLAG_ACTIVITY_CLEAR_TOP );
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, secondIntent, PendingIntent.FLAG_IMMUTABLE);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static void showHomeActivy(Context context) {
        Intent secondIntent = new Intent(context, MainActivity.class);
        secondIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP  |Intent.FLAG_ACTIVITY_CLEAR_TOP );
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, secondIntent, PendingIntent.FLAG_IMMUTABLE);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static void showSetActivy(Context context) {
        Intent intent = new Intent();
        ComponentName cn2 = new ComponentName(BuildConfig.APPLICATION_ID, "com.niuniu.babyprotect.ui.setting.SysSetActivity");
        intent.setComponent(cn2);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP  |Intent.FLAG_ACTIVITY_CLEAR_TOP );
        Uri uri = Uri.parse("com.niuniu.babyprotect.ui.setting.SysSetActivity");
        intent.setData(uri);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static void backApp(Context context) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent secondIntent = new Intent(context, OpenQxActivity.class);
        secondIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP  |Intent.FLAG_ACTIVITY_CLEAR_TOP );
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, secondIntent, PendingIntent.FLAG_IMMUTABLE);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e2) {
            e2.printStackTrace();
        }
    }

    public static void checkNeedOpenSettingPerm(Context context) {
        if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(context, StatusUseAccessibilityService.class.getName())) {
            Tools.getQxSet(context);
            Intent intent = new Intent();
            intent.setClass(context, OpenQxActivity.class);
            context.startActivity(intent);
        }
    }

    public static void killService(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();
        for (int i = 0; i < list.size(); i++) {
            String process = list.get(i).processName;
            ILog.d("APPtools", "kill -" + process);
            if (!process.contains(BuildConfig.APPLICATION_ID)) {
                try {
                    manager.killBackgroundProcesses(process);
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("kill " + list.get(i).pid);
                    ILog.d("APP tools", "kill -------process----" + process);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    ILog.d("APP tools", "kill--" + e.getMessage());
                    return;
                }
            }
        }
    }

    public static void killServiceByPackage(Context context, String packageName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        manager.killBackgroundProcesses(packageName);
    }
}
