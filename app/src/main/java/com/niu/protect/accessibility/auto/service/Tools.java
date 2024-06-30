package com.niu.protect.accessibility.auto.service;


import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.PowerManager;
import android.os.Process;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;
import androidx.exifinterface.media.ExifInterface;

import com.niu.protect.core.Constants;
import com.niu.protect.lib.receiver.DeviceReceiver;
import com.niu.protect.tools.RomUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

public class Tools{
    private static float f12846;

    public static boolean m22475() {
        String str = Build.MODEL;
        return ObjectUtils.equals(str, "MI PAD 4") || ObjectUtils.equals(str, "MI PAD 4 PLUS");
    }


    public static boolean m14279(Context context) {
        return ((UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE)).queryUsageStats(0, System.currentTimeMillis() - 86400000, System.currentTimeMillis()).size() > 0;
    }

    public static void m22480() {
        try {
            try {
                try {
                    Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Constants.MainInstance.getContext().startActivity(intent);
                } catch (Exception unused) {
                    ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
                    Intent intent2 = new Intent();
                    intent2.setComponent(componentName);
                    intent2.setAction("android.intent.action.View");
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Constants.MainInstance.getContext().startActivity(intent2);
                }
            } catch (Exception unused2) {
                Intent intent3 = new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Constants.MainInstance.getContext().startActivity(intent3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean m22473(Context context) {
        return TextUtils.equals(Build.MANUFACTURER, "Xiaomi") &&
                Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }


    public static boolean m22490() {
        return RomUtil.isMiui() && m22494() < 11.0f;
    }

    public static String miuiVersion() {
        String str = Build.MANUFACTURER;
        if (TextUtils.isEmpty(str) || !str.equals("Xiaomi")) {
            return "0";
        }
        String m22505 = m22505("ro.miui.ui.version.name");
        return ObjectUtils.isEmpty((CharSequence) m22505) ? "0" : m22505;
    }


    public static float m22494() {
        float f = f12846;
        if (f > 0.0f) {
            return f;
        }
        float parseFloat = Float.parseFloat(miuiVersion().replace(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ""));
        if (parseFloat > 100.0f) {
            parseFloat /= 10.0f;
        }
        f12846 = parseFloat;
        return parseFloat;
    }

    public static boolean m22474() {
        return Settings.Secure.getInt(Constants.MainInstance.getContext().getContentResolver(), "development_settings_enabled", 0) == 1;
    }

    public static boolean m22451(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "adb_enabled", 0) > 0;
    }

    public static boolean m22470(Context context) {
        Set<String> enabledListenerPackages = NotificationManagerCompat.getEnabledListenerPackages(context);
        return enabledListenerPackages != null && enabledListenerPackages.contains(context.getPackageName());
    }

    public static boolean m22486() {
        if (!"Xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
            return false;
        }
        String m14275 = m14275();
        return (ObjectUtils.isEmpty((CharSequence) m14275) || ObjectUtils.equals("0", m14275)) ? false : true;
    }

    public static String m14275() {
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
            String data = bufferedReader.readLine();
            return data;
        } catch (IOException e) {
        }
        return "";
    }

    /* renamed from: 垡玖 */
    public static boolean m14274(Context context) {
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



    public static boolean m22472(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                return Settings.canDrawOverlays(context);
            }
            if (RomUtil.isVivo()) {
                return m22502(context) == 0;
            }
//            if (!RomUtil.isGoogle() && !RomUtil.getRomInfo().getName().equals("UNKNOWN")) {
                return m22449(context);
//            }
//            return true;
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

    public static boolean m22457() {
        return Build.MANUFACTURER.equalsIgnoreCase("HONOR");
    }


}