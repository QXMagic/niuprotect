package com.niu.protect.accessibility.auto.service;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.AppUtils;
import com.niu.protect.accessibility.auto.service.tmp.C6891;

public class ModelManager {
    private static long f20960;
    public static void m22768(Context context) {
        if (System.currentTimeMillis() - f20960 > 50) {
            m22790(context);
            f20960 = System.currentTimeMillis();
        }
    }
    public static Intent m22785(String str) {
        String appPackageName = AppUtils.getAppPackageName();
        Intent intent = new Intent();
        intent.setAction(appPackageName + ".setting");
        intent.addCategory("com.guard.child.setting");
        if (ObjectUtils.isNotEmpty((CharSequence) str)) {
            C6891.m25859("setting_from", str);
        }
        return intent;
    }

    public static void m22790(Context context) {
        if (context == null) {
            context = App.mContext;
        }
        GuardFloatUtils.m25766(2);
        Intent m22785 = m22785("default");
        m22785.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(m22785);
    }

    public static String m14371() {
        String appPackageName = AppUtils.getAppPackageName();
        appPackageName.hashCode();
        char c2 = 65535;
        switch (appPackageName.hashCode()) {
            case -1498676132:
                if (appPackageName.equals("com.zlfcapp.guard")) {
                    c2 = 0;
                    break;
                }
                break;
            case -459465929:
                if (appPackageName.equals("com.fltapp.guard")) {
                    c2 = 1;
                    break;
                }
                break;
            case 620259790:
                if (appPackageName.equals("com.jjl.children")) {
                    c2 = 2;
                    break;
                }
                break;
            case 1810089837:
                if (appPackageName.equals("com.ape.apefather")) {
                    c2 = 3;
                    break;
                }
                break;
            case 1934049783:
                if (appPackageName.equals("com.joke.familycare")) {
                    c2 = 4;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return "binddevice";
            case 1:
                return "sunshine_guard";
            case 2:
                return "jjl_guard";
            case 3:
                return "ape_guard";
            case 4:
                return "guard";
            default:
                return "";
        }
    }


}

