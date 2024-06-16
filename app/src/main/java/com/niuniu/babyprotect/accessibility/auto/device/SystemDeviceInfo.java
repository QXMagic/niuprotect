package com.niuniu.babyprotect.accessibility.auto.device;

import android.os.Build;
public class SystemDeviceInfo {
    public static int getSdk() {
        return Build.VERSION.SDK_INT;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getDeviceInfo() {
        return Build.DEVICE;
    }

    public static String getRelease() {
        return Build.VERSION.RELEASE;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getProduct() {
        return Build.PRODUCT;
    }

    public static String getPhoneOs() {
        return CustomOSUtils.getCustomOS(Build.BRAND) + CustomOSUtils.getCustomOSVersion(Build.BRAND);
    }

    public static String getInfo() {
        String phoneInfo = "Product: " + Build.PRODUCT;
        return ((((((((((((phoneInfo + ", CPU_ABI: " + Build.CPU_ABI) + ", TAGS: " + Build.TAGS) + ", VERSION_CODES.BASE: 1") + ", MODEL: " + Build.MODEL) + ", SDK: " + Build.VERSION.SDK) + ", VERSION.RELEASE: " + Build.VERSION.RELEASE) + ", DEVICE: " + Build.DEVICE) + ", DISPLAY: " + Build.DISPLAY) + ", BRAND: " + Build.BRAND) + ", BOARD: " + Build.BOARD) + ", FINGERPRINT: " + Build.FINGERPRINT) + ", ID: " + Build.ID) + ", getCustomOS: " + CustomOSUtils.getCustomOS(Build.BRAND) + "   OSVersion:" + CustomOSUtils.getCustomOSVersion(Build.BRAND);
    }
}
