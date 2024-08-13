package accessibility;


import android.app.Activity;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.core.app.NotificationManagerCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RomUtils;
import com.hjq.permissions.XXPermissions;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.RomUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import accessibility.lib.ModelManager;
import im.niu.testapp.AutoInstallService;
import im.niu.testapp.R;

public class Tools{
    private static float f12846;


    public static boolean isPad() {
        String str = Build.MODEL;
        return ObjectUtils.equals(str, "MI PAD 4") || ObjectUtils.equals(str, "MI PAD 4 PLUS");
    }


    public static boolean m14279(Context context) {
        return ((UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE)).queryUsageStats(0, System.currentTimeMillis() - 86400000, System.currentTimeMillis()).size() > 0;
    }

    public static void openDevelopment() {
        try {
            try {
                try {
                    Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    App.mContext.startActivity(intent);
                } catch (Exception unused) {
                    ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
                    Intent intent2 = new Intent();
                    intent2.setComponent(componentName);
                    intent2.setAction("android.intent.action.View");
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    App.mContext.startActivity(intent2);
                }
            } catch (Exception unused2) {
                Intent intent3 = new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                App.mContext.startActivity(intent3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean m22473(Context context) {
        return TextUtils.equals(Build.MANUFACTURER, "Xiaomi") &&
                Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }


    public static boolean versionLess11() {
        return RomUtil.isMiui() && version() < 11.0f;
    }

    public static String miuiVersion() {
        String str = Build.MANUFACTURER;
        if (TextUtils.isEmpty(str) || !str.equals("Xiaomi")) {
            return "0";
        }
        String m22505 = m22505("ro.miui.ui.version.name");
        return ObjectUtils.isEmpty((CharSequence) m22505) ? "0" : m22505;
    }


    public static float version() {
        float f = f12846;
        if (f > 0.0f) {
            return f;
        }
        float parseFloat = 100;//Float.parseFloat(miuiVersion().replace(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ""));
        if (parseFloat > 100.0f) {
            parseFloat /= 10.0f;
        }
        f12846 = parseFloat;
        return parseFloat;
    }

    public static boolean isDeveloperOpen() {
        return Settings.Secure.getInt(App.mContext.getContentResolver(), "development_settings_enabled", 0) == 1;
    }

    public static boolean adbEnable(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "adb_enabled", 0) > 0;
    }

    public static boolean m22470(Context context) {
        Set<String> enabledListenerPackages = NotificationManagerCompat.getEnabledListenerPackages(context);
        return enabledListenerPackages.contains(context.getPackageName());
    }

    public static boolean isXiaomi() {
        if (!"Xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
            return false;
        }
        String m14275 = xiaomiVersion();
        return !ObjectUtils.isEmpty((CharSequence) m14275) && !ObjectUtils.equals("0", m14275);
    }

    public static String xiaomiVersion() {
        String str = Build.MANUFACTURER;
        if (TextUtils.isEmpty(str) || !str.equals("Xiaomi")) {
            return "0";
        }
        String m22505 = m22505("ro.mi.os.version.name");
        return ObjectUtils.isEmpty((CharSequence) m22505) ? "0" : m22505;
    }

    public static String m22505(String cmd){
        Runtime runtime = Runtime.getRuntime();
        String ecmd = "getprop "+cmd;
        try {
            java.lang.Process rs = runtime.exec(ecmd);
            InputStream in = rs.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            return bufferedReader.readLine();
        } catch (IOException e) {
        }
        return "";
    }

    public static boolean isOwner(Context context) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName m22771 = Tools.m22771(context);
        if (devicePolicyManager.isAdminActive(m22771)) {
            return true;
        }
        List<ComponentName> activeAdmins = devicePolicyManager.getActiveAdmins();
        if (activeAdmins == null) {
            return false;
        }
        for (ComponentName componentName : activeAdmins) {
            if (context.getPackageName().equals(componentName.getPackageName()) && m22771.getClassName().equals(componentName.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static ComponentName m22771(Context context) {
        return new ComponentName(context, (Class<?>) DeviceReceiver.class);
    }



    public static boolean canDrawOverlays(Context context) {
        try {
            return Settings.canDrawOverlays(context);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean m22449(Context context) {
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Class cls = Integer.TYPE;
            return ((Integer) AppOpsManager.class.getMethod("checkOp", cls, cls, String.class)
                    .invoke(appOpsManager, 24, Integer.valueOf(Binder.getCallingUid()), context.getApplicationContext().getPackageName())).intValue() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void m22499(Context context) {
        try {
            ((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE)).removeActiveAdmin(m22771(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static int m22502(Context context) {
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.vivo.permissionmanager.provider.permission/float_window_apps"), null, "pkgname = ?", new String[]{context.getPackageName()}, null);
        if (query == null) {
            return m22484(context);
        }
        if (query.moveToFirst()) {
            int idx = query.getColumnIndex("currentmode");
            int i = query.getInt(idx);
            query.close();
            return i;
        }
        query.close();
        return m22484(context);
    }

    public static int m22484(Context context) {
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.iqoo.secure.provider.secureprovider/allowfloatwindowapp"), null, "pkgname = ?", new String[]{context.getPackageName()}, null);
        if (query == null) {
            return 1;
        }
        query.getColumnNames();
        if (query.moveToFirst()) {
            int idx = query.getColumnIndex("currentlmode");
            int i = query.getInt(idx);
            query.close();
            return i;
        }
        query.close();
        return 1;
    }



    public static boolean m22479(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return powerManager != null && powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
    }

    public static boolean m22464(Context context) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        try {
            Class<?> cls = appOpsManager.getClass();
            Class<?> cls2 = Integer.TYPE;
            Object invoke = cls.getMethod("checkOpNoThrow", cls2, cls2, String.class).invoke(appOpsManager, 10021, Integer.valueOf(Process.myUid()), context.getPackageName());
            if (invoke != null) {
                return ((Integer) invoke).intValue() == 0;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean m22455() {
        return RomUtil.isOppo() || m22497();
    }

    public static boolean m22497() {
        return Build.MANUFACTURER.equalsIgnoreCase("realme");
    }

    public static boolean m22488(Context context) {
        return (context.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED)
                && (context.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean m22495(Context context) {
        try {
            String str = context.getPackageName() + "/" + AccessibilityHelperService.class.getCanonicalName();
            int i = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), "accessibility_enabled");
            if (i != 1) {
                return false;
            }
            String string = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "enabled_accessibility_services");
            if (TextUtils.isEmpty(string)) {
                return false;
            }

            if (string.toLowerCase().contains(str.toLowerCase())) {
                return true;
            }
            return false;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean m22503(Context context) {
        if (context != null) {
            Cursor query = context.getContentResolver().query(Uri.parse("content://com.vivo.permissionmanager.provider.permission/start_bg_activity"), null, "pkgname = ?", new String[]{context.getPackageName()}, null);
            if (query == null) {
                return false;
            }
            query.getColumnNames();
            if (query.moveToFirst()) {
                int idx = query.getColumnIndex("currentstate");
                int i = query.getInt(idx);
                query.close();
                return i == 0;
            }
            query.close();
        }
        return false;
    }

    public static boolean m22462(Context context) {
        if (context != null) {
            Cursor query = context.getContentResolver().query(Uri.parse("content://com.vivo.permissionmanager.provider.permission/bg_start_up_apps"), null, "pkgname = ?", new String[]{context.getPackageName()}, null);
            if (query == null) {
                return false;
            }
            query.getColumnNames();
            if (query.moveToFirst()) {
                int idx = query.getColumnIndex("currentstate");
                int i = query.getInt(idx);
                query.close();
                return i == 0;
            }
            query.close();
        }
        return false;
    }

    public static int f20842;

    public static boolean m22483() {
        int i = f20842;
        int i2 = 1;
        boolean z = false;
        if (i > 0) {
            return i == 1;
        }
        try {
            Class<?> cls = Class.forName("com.huawei.system.BuildEx");
            z = "harmony".equals(cls.getMethod("getOsBrand", new Class[0]).invoke(cls, new Object[0]));
            if (!z) {
                i2 = 2;
            }
            f20842 = i2;
        } catch (Exception unused) {
        }
        return z;
    }
    public static boolean m22507() {
        return ObjectUtils.equals("blackshark", Build.MANUFACTURER);
    }

    public static boolean m22508() {
        if (Build.VERSION.SDK_INT < 26) {
            return false;
        }
        String[] split = Build.VERSION.SECURITY_PATCH.split("-");
        if (Integer.parseInt(split[0]) <= 2020 && Integer.parseInt(split[1]) < 5) {
            return false;
        }
        String str = Build.VERSION.INCREMENTAL;
        if (str.contains(".")) {
            String[] split2 = str.split("\\.");
            return Integer.parseInt(split2[0]) > 11 || split2.length <= 3 || Integer.parseInt(split2[3].subSequence(0, 3).toString()) >= 140;
        }
        return true;
    }


    public static boolean m22457() {
        return Build.MANUFACTURER.equalsIgnoreCase("HONOR");
    }
    public static String m22477() {
        return OSUtils.m21271("hw_sc.build.platform.version", "");
    }
    public static int m22498() {
        String m22477 = m22477();
        if (ObjectUtils.isNotEmpty((CharSequence) m22477)) {
            return Integer.parseInt(m22477.replaceAll("\\.", ""));
        }
        return 0;
    }

    public static void m22459(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings"));
//        intent.addFlags(1350631424);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        ComponentName componentName = new ComponentName(context.getPackageName(), AutoInstallService.class.getName());
        String preferenceKey = context.getPackageName() + "/" + AutoInstallService.class.getName();
        bundle.putString("preference_key", preferenceKey);
        ILog.d("Tools",preferenceKey);
        bundle.putBoolean("checked", false);
        bundle.putString("title", context.getString(R.string.app_name));
        bundle.putString("summary", context.getString(R.string.accessibility));
        bundle.putParcelable("component_name", componentName);
        intent.putExtra(":android:show_fragment", "com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment");
        intent.putExtra(":android:show_fragment_args", bundle);
        intent.putExtra(":android:no_headers", true);
        intent.putExtra("setting:ui_options", 1);
        context.startActivity(intent);
    }

//    public static void m22482(Context context) {
//        ComponentName m22771 = ModelManager.m22771(context);
//        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
//        intent.putExtra("android.app.extra.DEVICE_ADMIN", m22771);
//        intent.putExtra("android.app.extra.ADD_EXPLANATION", "提供于管理被监护人的设备的自动化工作");
//        context.startActivity(intent);
//    }

    public static void m22489(Context context) {
        Intent intent;
        Intent intent2;
        Intent intent3 = new Intent();
        try {
            if (m22471(context, "com.miui.securitycenter")) {
                ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                intent3.putExtra("extra_pkgname", context.getPackageName());
                intent3.setComponent(componentName);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent3, 10020);
                    return;
                } else {
                    context.startActivity(intent3);
                    return;
                }
            }
            if (isPad()) {
                intent2 = new Intent("android.settings.SETTINGS");
            } else {
                intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent2.setData(Uri.fromParts("package", context.getPackageName(), null));
            }
            try {
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent2, 10020);
                } else {
                    context.startActivity(intent2);
                }
            } catch (Exception e) {
                e.printStackTrace();
                App.showToast("启动自启管理页面失败，请联系客服");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (isPad()) {
                intent = new Intent("android.settings.SETTINGS");
            } else {
                Intent intent4 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent4.setData(Uri.fromParts("package", context.getPackageName(), null));
                intent = intent4;
            }
            try {
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, 10020);
                } else {
                    context.startActivity(intent);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                App.showToast("启动自启管理页面失败，请联系客服");
            }
        }
    }


    public static boolean m22469(Context context) {
//        if (RomUtils.isHuawei()) {
//            m22504(context);
//            return true;
//        }
//        if (m22457()) {
//            m22500(context);
//            return true;
//        }
        if (RomUtils.isXiaomi()) {
            m22489(context);
            return true;
        }
//        if (!RomUtils.isOppo() && !m22497()) {
//            if (RomUtils.isVivo()) {
//                m22509(context);
//                return true;
//            }
//            if (RomUtils.isMeizu()) {
//                m22454(context);
//                return true;
//            }
//            if (RomUtils.isSamsung()) {
//                m14277(context);
//                return true;
//            }
//            m22465(context, 10020, context.getPackageName());
//            return true;
//        }
//        m22510(context);
        return true;
    }

    public static boolean m14276(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 23) {
                return false;
            }
            Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
            if (queryIntentActivities != null && !queryIntentActivities.isEmpty()) {
                intent.setComponent(new ComponentName(queryIntentActivities.get(0).activityInfo.packageName, queryIntentActivities.get(0).activityInfo.name));
                intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, 10020);
                    return true;
                }
            }
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void m22448(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                context.startActivity(new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean m22501(Context context) {
        try {
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            try {
                try {
                    Intent intent2 = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    intent2.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                    intent2.putExtra("extra_pkgname", context.getPackageName());
                    context.startActivity(intent2);
                    return true;
                } catch (Exception unused) {
                    Intent intent3 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent3.setData(Uri.fromParts("package", context.getPackageName(), null));
                    context.startActivity(intent3);
                    return true;
                }
            } catch (Exception unused2) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static void m14280(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity"));
            intent.putExtra("package_name", context.getPackageName());
            intent.putExtra("package_label", AppUtils.getAppName());
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            m22460(context);
        }
    }

    public static boolean m22481(Context context) {
        try {
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.settings.PrivacySettingsActivity");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void m22482(Context context) {
        ComponentName m22771 = ModelManager.m22771(context);
        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        intent.putExtra("android.app.extra.DEVICE_ADMIN", m22771);
        intent.putExtra("android.app.extra.ADD_EXPLANATION", "提供于管理被监护人的设备的自动化工作");
        context.startActivity(intent);
    }

    public static void m22480() {
        try {
            try {
                try {
                    Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    App.mContext.startActivity(intent);
                } catch (Exception unused) {
                    ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
                    Intent intent2 = new Intent();
                    intent2.setComponent(componentName);
                    intent2.setAction("android.intent.action.View");
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    App.mContext.startActivity(intent2);
                }
            } catch (Exception unused2) {
                Intent intent3 = new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                App.mContext.startActivity(intent3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static void m22463(Context context) {
        try {
            Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= 22) {
                activity.startActivityForResult(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"), 1001);
            } else {
                activity.startActivityForResult(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"), 1001);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m22510(context);
        }
    }


    public static void m22467(Context context) {
        try {
            Intent intent = new Intent();
            intent.setClassName("com.miui.securitycenter", "com.miui.securityscan.ui.settings.SettingsActivity");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void m22511(Context context) {
        Activity activity = (Activity) context;
        try {
            if (Build.VERSION.SDK_INT > 22) {
                activity.startActivityForResult(new Intent("android.settings.BATTERY_SAVER_SETTINGS"), 10020);
            } else if (RomUtils.isOneplus()) {
                Intent component = new Intent().setComponent(new ComponentName("com.oplus.battery", "com.oplus.powermanager.fuelgaue.PowerConsumptionOptimizationActivity"));
                component.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                component.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivityForResult(component, 10020);
            } else {
                Intent component2 = new Intent().setComponent(new ComponentName("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerConsumptionActivity"));
                component2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                component2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivityForResult(component2, 10020);
            }
        } catch (Exception unused) {
            m22460(activity);
        }
    }

    public static void m22460(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, 10020);
        } else {
            context.startActivity(intent);
        }
    }


    public static void m22510(Context context) {
        Intent intent = new Intent("android.settings.SETTINGS");
//        intent.setFlags(C2860y.f10012a);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static boolean m22466(Context context) {
        Intent intent = new Intent();
        try {
            if (m22471(context, "com.huawei.systemmanager")) {
                if (Build.VERSION.SDK_INT <= 27) {
                    intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.HwPowerManagerActivity"));
                } else {
                    intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.PowerSettingActivity"));
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, 10020);
                } else {
                    context.startActivity(intent);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Intent intent2 = new Intent("android.settings.BATTERY_SAVER_SETTINGS");
                intent2.setData(Uri.fromParts("package", context.getPackageName(), null));
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent2, 10020);
                } else {
                    context.startActivity(intent2);
                }
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }
    }

    public static void m22487(Context context) {
        try {
            Activity activity = (Activity) context;
            Intent intent = new Intent();
            intent.setPackage(activity.getPackageName());
            intent.setClassName("com.miui.voiceassist", "com.xiaomi.voiceassistant.settings.MiuiVoiceSettingActivity");
            activity.startActivityForResult(intent, 10020);
        } catch (Exception e) {
            e.printStackTrace();
            m22510(context);
        }
    }

    public static void m22465(Context context, int i, String str) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", str, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        try {
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, i);
            } else {
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            App.showToast("启动APP详情页面失败，请联系客服");
        }
    }

    public static void m22461(Context context) {
        XXPermissions.with(context).permission("android.permission.ACCESS_BACKGROUND_LOCATION",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_COARSE_LOCATION").request(new C3619());
    }

    public static void m22496(Context context) {
//        Intent intent = new Intent();
//        try {
//            intent.setComponent(m22471(context, "com.vivo.permissionmanager") ? new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity") : null);
//            if (intent.getComponent() != null) {
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
//                if (context instanceof Activity) {
//                    ((Activity) context).startActivityForResult(intent, 10020);
//                } else {
//                    context.startActivity(intent);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
//            intent2.setData(Uri.fromParts("package", context.getPackageName(), null));
//            try {
//                if (context instanceof Activity) {
//                    ((Activity) context).startActivityForResult(intent2, 10020);
//                } else {
//                    context.startActivity(intent2);
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//                App.showToast("启动自启管理页面失败，请联系客服");
//            }
//        }
    }

    public static void m22450(Context context) {
        Activity activity = (Activity) context;
        try {
            if (RomUtils.isVivo()) {
                Intent intent = new Intent();
                intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
                intent.putExtra("packagename", activity.getPackageName());
                activity.startActivityForResult(intent, 10020);
            } else if (Build.VERSION.SDK_INT >= 23) {
                activity.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + activity.getPackageName())), 10020);
            } else if (RomUtils.isXiaomi()) {
                m22492(activity);
            } else if (RomUtils.isOppo()) {
                Intent intent2 = new Intent();
                intent2.setClassName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");
                intent2.putExtra("packagename", activity.getPackageName());
                activity.startActivityForResult(intent2, 10020);
            } else {
                m22460(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m22460(activity);
        }
    }

    public static void m22492(Context context) {
        try {
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            if ("V5".equals(m22505("ro.miui.ui.version.name"))) {
                PackageInfo packageInfo = null;
                try {
                    packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                intent.setClassName("com.miui.securitycenter", "com.miui.securitycenter.permission.AppPermissionsEditor");
                intent.putExtra("extra_package_uid", packageInfo.applicationInfo.uid);
            } else {
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.putExtra("extra_pkgname", context.getPackageName());
            }
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, 10020);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }


    public static void m22453(Context context) {
        m22465(context, 10020, context.getPackageName());
    }

    public static void m22476(Context context) {
        try {
            if (RomUtils.isXiaomi() && Build.VERSION.SDK_INT < 33) {
                try {
                    m22459(context);
                } catch (Exception e) {
                    e.printStackTrace();
                    context.startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
                }
            } else {
                context.startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }




    public static void m22493(Context context) {
        try {
            Intent intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        } catch (Exception unused) {
            context.startActivity(new Intent("android.settings.USAGE_ACCESS_SETTINGS"));
        }
    }

    public static boolean m22491(Context context) {
        try {
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT <= 23) {
                intent.setClassName("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity");
            } else if (m22471(context, "com.iqoo.powersaving")) {
                intent.setClassName("com.iqoo.powersaving", "com.iqoo.powersaving.PowerSavingManagerActivity");
            } else {
                intent.setAction("android.settings.BATTERY_SAVER_SETTINGS");
            }
            ((Activity) context).startActivityForResult(intent, 10020);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean m22471(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}


class OSUtils {
    public static String m21271(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            String str3 = (String) cls.getDeclaredMethod("get", String.class).invoke(cls, str);
            return TextUtils.isEmpty(str3) ? str2 : str3;
        } catch (Throwable th) {
            th.printStackTrace();
            return str2;
        }
    }

    /* renamed from: 肌緭 */
    public static String m13863() {
        return RomUtils.isXiaomi() ? "小米" : RomUtils.isHuawei() ? "华为" : RomUtils.isVivo() ? "vivo" : RomUtils.isMeizu() ? "魅族" : RomUtils.isOppo() ? "OPPO" : RomUtils.is360() ? "360" : RomUtils.isCoolpad() ? "酷派" : RomUtils.isGoogle() ? "谷歌" : RomUtils.isSamsung() ? "三星" : RomUtils.isGionee() ? "金立" : RomUtils.isHtc() ? "HTC" : RomUtils.isLeeco() ? "乐视" : RomUtils.isLg() ? "LG" : RomUtils.isMotorola() ? "摩托罗拉" : RomUtils.isLenovo() ? "联想" : RomUtils.isNubia() ? "努比亚" : RomUtils.isOneplus() ? "一加" : RomUtils.isSmartisan() ? "锤子" : RomUtils.isSony() ? "索尼" : RomUtils.isZte() ? "中兴" : RomUtils.getRomInfo().getName();
    }
}