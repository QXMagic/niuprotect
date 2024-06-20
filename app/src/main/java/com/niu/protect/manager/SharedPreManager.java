package com.niu.protect.manager;

import android.content.Context;
import com.niu.protect.BabyApplication;
import com.niu.protect.tools.SharedPreUtil;
import com.tencent.mmkv.MMKV;
public class SharedPreManager {
    public static final String KEY_APP_UPLOAD_APPS = "key_uploadApps";
    public static final String KEY_AUTO_SETTING_FINISH = "key_aoto_setting";
    public static final String KEY_BIND_MODE = "key_bind_mode";
    public static final String KEY_CONTROLLER_INFO = "key_controllerinfo";
    public static final String KEY_CONTROL_TEACHER = "key_control_teacher";
    public static final String KEY_CONTROL_WAY = "key_control_way";
    public static final String KEY_CUSTOM_MSG_TIMES = "key_custom_today_msg_times";
    public static final String KEY_CUSTOM_MSG_TODAY = "key_custom_today";
    public static final String KEY_KEY_SAVE_UPLOAD_APP = "key_upload_app";
    public static final String KEY_OTHER_LIMIT_TIME = "key_other_limit_time";
    public static final String KEY_PARENT_HOLIDAY = "key_parent_holiday";
    public static final String KEY_PARENT_SCHOOL = "key_parent_school";
    public static final String KEY_SYSTEM_BLACK_APP = "key_sys_black_app";
    public static final String KEY_SYSTEM_WHITE_APP = "key_sys_white_app";
    public static final String KEY_TEMP_OUT_CONTROL = "key_temp_out_control";
    public static final String KEY_USER_BLACK_APP = "key_user_black_app";
    public static final String KEY_USER_INFO = "key_user_info";
    public static final String KEY_USER_WHITE_APP = "key_user_white_app";
    public static final String KEY_XCX_CONTROL = "key_xcx_control";
    public static final String SP_NAME = "sp_protect";

    public static void saveAutoSettingFinish(Context context, int finish) {
        MMKV kv = MMKV.defaultMMKV();
        kv.encode(KEY_AUTO_SETTING_FINISH, finish);
    }

    public static int getAutoSettingFinish() {
        MMKV kv = MMKV.defaultMMKV();
        int finishStatus = kv.decodeInt(KEY_AUTO_SETTING_FINISH, 0);
        return finishStatus;
    }

    public static boolean saveCustomSendTimes(int day) {
        int remenberDay = getCustomDay();
        int times = getCustomSendTimes();
        if (remenberDay == day) {
            int times2 = times + 1;
            SharedPreUtil.setParam(BabyApplication.getInstance(), KEY_CUSTOM_MSG_TIMES, Integer.valueOf(times2));
            return times2 >= 10;
        }
        SharedPreUtil.setParam(BabyApplication.getInstance(), KEY_CUSTOM_MSG_TODAY, Integer.valueOf(day));
        SharedPreUtil.setParam(BabyApplication.getInstance(), KEY_CUSTOM_MSG_TIMES, 1);
        return false;
    }

    public static int getCustomSendTimes() {
        return ((Integer) SharedPreUtil.getParam(BabyApplication.getInstance(), KEY_CUSTOM_MSG_TIMES, 0)).intValue();
    }

    private static int getCustomDay() {
        return ((Integer) SharedPreUtil.getParam(BabyApplication.getInstance(), KEY_CUSTOM_MSG_TODAY, 0)).intValue();
    }
}
