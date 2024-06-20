package com.niu.protect.accessibility.auto.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import com.niu.protect.BabyApplication;
import com.niu.protect.accessibility.auto.device.CustomOSUtils;
import com.niu.protect.ui.login.SplashActivity;
import com.niu.protect.ui.setting.PermCollectActivity;
import java.util.List;
public class OpenSettingApp {
    public static void gotoSetting(Activity context, int type) {
        switch (type) {
            case 1:
                gotoSystemSetting(context);
                return;
            case 2:
                getAppDetailSettingIntent(context);
                return;
            case 3:
                gotoAppUseAccessSetting(context);
                return;
            case 4:
                openNotification(context);
                return;
            case 5:
                requestSettingCanDrawOverlays(context);
                return;
            case 6:
                openOtherApp(context, "com.iqoo.secure", "com.iqoo.secure.MainActivity");
                return;
            case 7:
                hwAutoSetting(context);
                return;
            case 8:
                openOtherApp(context, "com.coloros.phonemanager", "com.coloros.phonemanager.FakeActivity");
                return;
            case 9:
                vivoGotoAutoStart(context);
                return;
            case 10:
                hwAutoSetting(context);
                return;
            default:
                return;
        }
    }

    private static void hwAutoSetting(Activity context) {
        String systemOs = CustomOSUtils.getCustomOS(Build.BRAND);
        String brand = Build.BRAND;
        if (systemOs.toUpperCase().equals("EMUI")) {
            openOtherApp(context, "com.huawei.systemmanager", "com.huawei.systemmanager.mainscreen.MainScreenActivity");
        } else if (brand.toUpperCase().equals("HUAWEI")) {
            openHONGMeng(context);
        } else {
            openHONGMeng(context);
        }
    }

    public static void openHONGMeng(Context context) {
        Intent[] AUTO_START_HUAWEI = {new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.mainscreen.MainScreenActivity")), new Intent().setComponent(new ComponentName("com.hihonor.systemmanager", "com.huawei.systemmanager.mainscreen.MainScreenActivity")), new Intent().setComponent(new ComponentName("com.hihonor.systemmanager", "com.hihonor.systemmanager.mainscreen.MainScreenActivity"))};
        for (Intent intent : AUTO_START_HUAWEI) {
            if (context.getPackageManager().resolveActivity(intent, 65536) != null) {
                try {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory("android.intent.category.DEFAULT");
                    context.startActivity(intent);
                    return;
                } catch (Exception e) {
                    Log.d("TAG", "OPPO - Exception: " + e.toString());
                }
            }
        }
    }

    public static void openOtherApp(Activity context, String packageName, String className) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setClassName(packageName, className);
        context.startActivityForResult(intent, 6);
    }

    public static void openAppPage(Activity context, String packagename) {
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

    public static void gotoSystemSetting(Activity context) {
        Intent intent = new Intent("android.settings.SETTINGS");
        context.startActivity(intent);
    }

    public static void getAppDetailSettingIntent(Activity context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction("android.intent.action.VIEW");
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    public static void gotoAppUseAccessSetting(Activity context) {
        Intent intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
        context.startActivityForResult(intent, 3);
    }

    public static void openNotification(Activity context) {
        try {
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            if (Build.VERSION.SDK_INT >= 26) {
                localIntent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
                localIntent.putExtra("android.provider.extra.CHANNEL_ID", context.getApplicationInfo().uid);
            } else if (Build.VERSION.SDK_INT >= 21) {
                localIntent.putExtra("app_package", context.getPackageName());
                localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
            }
            context.startActivityForResult(localIntent, 4);
        } catch (Exception e) {
            getAppDetailSettingIntent(context);
        }
    }

    public static void backMineApp(Context context) {
        try {
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent secondIntent = new Intent(context, PermCollectActivity.class);
        secondIntent.addFlags(872480768);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, secondIntent, 0);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e2) {
            e2.printStackTrace();
        }
    }

    public static void requestSettingCanDrawOverlays(Activity context) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 26) {
            context.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION"), 0);
        } else if (sdkInt >= 23) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivityForResult(intent, 0);
        }
    }

    public static void hideIcon(Context context) {
        PackageManager packageManager = BabyApplication.getInstance().getPackageManager();
        context.getPackageName();
        ComponentName componentName = new ComponentName(BabyApplication.getInstance(), SplashActivity.class);
        packageManager.setComponentEnabledSetting(componentName, 2, 1);
    }

    public static void showIcon(Context context) {
        PackageManager packageManager = BabyApplication.getInstance().getPackageManager();
        ComponentName componentName = new ComponentName(BabyApplication.getInstance(), SplashActivity.class);
        packageManager.setComponentEnabledSetting(componentName, 1, 1);
    }

    public static void checkShowIcon(Context context, int showStatus) {
        if (showStatus == 1) {
            showIcon(context);
        } else if (showStatus == 2) {
            hideIcon(context);
        }
    }

    public static void vivoGotoAutoStart(Context context) {
        Intent intent = new Intent();
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName componentName = ComponentName.unflattenFromString("com.vivo.permissionmanager/.activity.BgStartUpManagerActivity");
            intent.setComponent(componentName);
            context.startActivity(intent);
        } catch (Exception e) {
            context.startActivity(new Intent("android.settings.SETTINGS"));
        }
    }
}
