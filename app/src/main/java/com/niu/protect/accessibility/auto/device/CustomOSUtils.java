package com.niu.protect.accessibility.auto.device;

import android.os.Build;
import android.text.TextUtils;
import java.lang.reflect.Method;
public class CustomOSUtils {
    private static final String KEY_COLOROS_VERSION_NAME = "ro.build.version.opporom";
    private static final String KEY_EMUI_VERSION_NAME = "ro.build.version.emui";
    private static final String KEY_FLYME_VERSION_NAME = "ro.build.display.id";
    private static final String KEY_HARMONYOS_VERSION_NAME = "hw_sc.build.platform.version";
    private static final String KEY_MAGICUI_VERSION = "ro.build.version.magic";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_NUBIA_VERSION_CODE = "ro.build.nubia.rom.code";
    private static final String KEY_NUBIA_VERSION_NAME = "ro.build.nubia.rom.name";
    private static final String KEY_ONEPLUS_VERSION_NAME = "ro.rom.version";
    private static final String KEY_VIVO_VERSION = "ro.vivo.os.version";
    private static final String KEY_VIVO_VERSION_NAME = "ro.vivo.os.name";
    private static String customOS = "";
    private static String customOSVersion = "";

    private static String getSystemPropertyValue(String key) {
        try {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", String.class);
            String value = (String) getMethod.invoke(classType, key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isHarmonyOS() {
        try {
            Class<?> classType = Class.forName("com.huawei.system.BuildEx");
            Method getMethod = classType.getMethod("getOsBrand", new Class[0]);
            String value = (String) getMethod.invoke(classType, new Object[0]);
            return !TextUtils.isEmpty(value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getHarmonySystemPropertyValue() {
        try {
            Class<?> classType = Class.forName("com.huawei.system.BuildEx");
            Method getMethod = classType.getMethod("getOsBrand", new Class[0]);
            String value = (String) getMethod.invoke(classType, new Object[0]);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPhoneSystem(String phoneBrand) {
        if (TextUtils.isEmpty(customOS)) {
            setCustomOSInfo(phoneBrand);
        }
        return customOS + customOSVersion;
    }

    private static boolean isMagicUI() {
        return false;
    }

    public static String getCustomOS(String phoneBrand) {
        if (TextUtils.isEmpty(customOS)) {
            setCustomOSInfo(phoneBrand);
        }
        return customOS;
    }

    public static String getCustomOSVersion(String phoneBrand) {
        if (TextUtils.isEmpty(customOS)) {
            setCustomOSInfo(phoneBrand);
        }
        return customOSVersion;
    }

    public static String getCustomOSVersionSimple(String phoneBrand) {
        String customOSVersionSimple = customOSVersion;
        if (TextUtils.isEmpty(customOS)) {
            getCustomOSVersion(phoneBrand);
        }
        if (customOSVersion.contains(".")) {
            int index = customOSVersion.indexOf(".");
            String customOSVersionSimple2 = customOSVersion.substring(0, index);
            return customOSVersionSimple2;
        }
        return customOSVersionSimple;
    }

    public static String deleteSpaceAndToUpperCase(String str) {
        return TextUtils.isEmpty(str) ? "" : str.replaceAll(" ", "").toUpperCase();
    }

    private static void setCustomOSInfo(String phoneBrand) {
        try {
            String deleteSpaceAndToUpperCase = deleteSpaceAndToUpperCase(phoneBrand);
            char c = 65535;
            switch (deleteSpaceAndToUpperCase.hashCode()) {
                case -1881642058:
                    if (deleteSpaceAndToUpperCase.equals("REALME")) {
                        c = 4;
                        break;
                    }
                    break;
                case -1706170181:
                    if (deleteSpaceAndToUpperCase.equals("XIAOMI")) {
                        c = 2;
                        break;
                    }
                    break;
                case -602397472:
                    if (deleteSpaceAndToUpperCase.equals("ONEPLUS")) {
                        c = 7;
                        break;
                    }
                    break;
                case 2432928:
                    if (deleteSpaceAndToUpperCase.equals("OPPO")) {
                        c = 5;
                        break;
                    }
                    break;
                case 2634924:
                    if (deleteSpaceAndToUpperCase.equals("VIVO")) {
                        c = 6;
                        break;
                    }
                    break;
                case 68924490:
                    if (deleteSpaceAndToUpperCase.equals("HONOR")) {
                        c = 1;
                        break;
                    }
                    break;
                case 73239724:
                    if (deleteSpaceAndToUpperCase.equals("MEIZU")) {
                        c = '\b';
                        break;
                    }
                    break;
                case 74632627:
                    if (deleteSpaceAndToUpperCase.equals("NUBIA")) {
                        c = '\t';
                        break;
                    }
                    break;
                case 77852109:
                    if (deleteSpaceAndToUpperCase.equals("REDMI")) {
                        c = 3;
                        break;
                    }
                    break;
                case 2141820391:
                    if (deleteSpaceAndToUpperCase.equals("HUAWEI")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    if (isHarmonyOS()) {
                        customOSVersion = getSystemPropertyValue(KEY_HARMONYOS_VERSION_NAME);
                        customOS = "HarmonyOS";
                        return;
                    }
                    customOS = "EMUI";
                    customOSVersion = getSystemPropertyValue(KEY_EMUI_VERSION_NAME);
                    return;
                case 1:
                    if (isHarmonyOS()) {
                        customOS = "HarmonyOS";
                        if (!TextUtils.isEmpty(getSystemPropertyValue(KEY_HARMONYOS_VERSION_NAME))) {
                            customOSVersion = getSystemPropertyValue(KEY_HARMONYOS_VERSION_NAME);
                            return;
                        } else {
                            customOSVersion = "";
                            return;
                        }
                    } else if (!TextUtils.isEmpty(getSystemPropertyValue(KEY_MAGICUI_VERSION))) {
                        customOS = "MagicUI";
                        customOSVersion = getSystemPropertyValue(KEY_MAGICUI_VERSION);
                        return;
                    } else {
                        customOS = "EMUI";
                        customOSVersion = getSystemPropertyValue(KEY_EMUI_VERSION_NAME);
                        return;
                    }
                case 2:
                case 3:
                    customOS = "MIUI";
                    customOSVersion = getSystemPropertyValue(KEY_MIUI_VERSION_NAME);
                    return;
                case 4:
                case 5:
                    customOS = "ColorOS";
                    customOSVersion = getSystemPropertyValue(KEY_COLOROS_VERSION_NAME);
                    return;
                case 6:
                    customOS = "Funtouch";
                    customOSVersion = getSystemPropertyValue(KEY_VIVO_VERSION);
                    return;
                case 7:
                    customOS = "HydrogenOS";
                    customOSVersion = getSystemPropertyValue(KEY_ONEPLUS_VERSION_NAME);
                    return;
                case '\b':
                    customOS = "Flyme";
                    customOSVersion = getSystemPropertyValue(KEY_FLYME_VERSION_NAME);
                    return;
                case '\t':
                    customOS = getSystemPropertyValue(KEY_NUBIA_VERSION_NAME);
                    customOSVersion = getSystemPropertyValue(KEY_NUBIA_VERSION_CODE);
                    return;
                default:
                    customOS = "Android";
                    customOSVersion = Build.VERSION.RELEASE;
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getModel() {
        return Build.MODEL;
    }
}
