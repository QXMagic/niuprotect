package com.niu.protect.accessibility.auto.service.tmp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.niu.protect.accessibility.auto.service.App;

import java.util.Calendar;
import java.util.TimeZone;

public class ExamineUtils {

    //    /* renamed from: 肌緭 */
    public static long f13453;

    //
//    /* renamed from: 刻槒唱镧詴 */
    public static String m24110(Context context, String str) {
        if (context == null || str == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            Bundle bundle = applicationInfo != null ? applicationInfo.metaData : null;
            if (bundle != null) {
                return bundle.getString(str);
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //
//    /* renamed from: 垡玖 */
//    public static void m14886() {
//        if (C6891.m25860(CommonUtils.m21223(), 0L) == 0) {
//            C6891.m25855(CommonUtils.m21223(), System.currentTimeMillis());
//        }
//    }
//
//    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public static boolean m24111() {
        if (ObjectUtils.equals(ModelManager.m22783() ? "ydhuawei" : "huawei", CommonUtils.m21228())) {
            return System.currentTimeMillis() <= m14887();
        }
        return false;
    }
//
//    /* renamed from: 肌緭 */
    public static long m14887() {
        if (f13453 == 0) {
            String replaceAll = m24110(App.mContext, "com.guard.time").replaceAll("\"", "");
            if (ObjectUtils.isNotEmpty((CharSequence) replaceAll)) {
                long m14313 = DateUtil.m14313(Long.parseLong(replaceAll));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                calendar.setTimeInMillis(m14313);
                int i = calendar.get(7);
                if (i != 6 && i != 7) {
                    calendar.add(5, 1);
                } else if (i == 6) {
                    calendar.add(5, 3);
                } else {
                    calendar.add(5, 2);
                }
                f13453 = calendar.getTime().getTime();
            }
        }
        return f13453;
    }
//
//    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//    public static boolean m24112(int i) {
//        return System.currentTimeMillis() - C6891.m25860(CommonUtils.m21223(), 0L) >= ((long) i) * 86400000;
//    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public static boolean m24113() {
        return !"vivo".equals(CommonUtils.m21228()) || System.currentTimeMillis() > m14887();
    }

    public static boolean m22783() {
        return ObjectUtils.equals("com.joke.familycare", AppUtils.getAppPackageName());
    }

}
