package com.niu.protect.manager

import com.niu.protect.core.Constants
import com.niu.protect.tools.SharedPreUtil
import com.tencent.mmkv.MMKV

object SharedPreManager {
    const val KEY_APP_UPLOAD_APPS = "key_uploadApps"
    const val KEY_AUTO_SETTING_FINISH = "key_aoto_setting"
//    const val KEY_BIND_MODE = "key_bind_mode"
//    const val KEY_CONTROLLER_INFO = "key_controllerinfo"
    const val KEY_CONTROL_TEACHER = "key_control_teacher"
//    const val KEY_CONTROL_WAY = "key_control_way"
    const val KEY_CUSTOM_MSG_TIMES = "key_custom_today_msg_times"
    const val KEY_CUSTOM_MSG_TODAY = "key_custom_today"
//    const val KEY_KEY_SAVE_UPLOAD_APP = "key_upload_app"
    const val KEY_OTHER_LIMIT_TIME = "key_other_limit_time"
    const val KEY_PARENT_HOLIDAY = "key_parent_holiday"
    const val KEY_PARENT_SCHOOL = "key_parent_school"
    const val KEY_SYSTEM_BLACK_APP = "key_sys_black_app"
    const val KEY_SYSTEM_WHITE_APP = "key_sys_white_app"
    const val KEY_TEMP_OUT_CONTROL = "key_temp_out_control"
    const val KEY_USER_BLACK_APP = "key_user_black_app"
    const val KEY_USER_INFO = "key_user_info"
    const val KEY_USER_WHITE_APP = "key_user_white_app"
    const val KEY_XCX_CONTROL = "key_xcx_control"
    const val SP_NAME = "sp_protect"
    @JvmStatic
    fun saveAutoSettingFinish(finish: Int) {
        val kv = MMKV.defaultMMKV()
        kv.encode(KEY_AUTO_SETTING_FINISH, finish)
    }

    @JvmStatic
    val autoSettingFinish: Int
        get() {
            val kv = MMKV.defaultMMKV()
            return kv.decodeInt(KEY_AUTO_SETTING_FINISH, AutoSettingManager.AUTO_SETTING_NOT_SET)
        }

    fun saveCustomSendTimes(day: Int): Boolean {
        val remenberDay = customDay
        val times = customSendTimes
        if (remenberDay == day) {
            val times2 = times + 1
            SharedPreUtil.setParam(
                Constants.MainInstance.getContext(),
                KEY_CUSTOM_MSG_TIMES,
                Integer.valueOf(times2)
            )
            return times2 >= 10
        }
        SharedPreUtil.setParam(
            Constants.MainInstance.getContext(),
            KEY_CUSTOM_MSG_TODAY,
            Integer.valueOf(day)
        )
        SharedPreUtil.setParam(Constants.MainInstance.getContext(), KEY_CUSTOM_MSG_TIMES, 1)
        return false
    }

    private val customSendTimes: Int
        get() = SharedPreUtil.getParam(
            Constants.MainInstance.getContext(),
            KEY_CUSTOM_MSG_TIMES,
            0
        ) as Int
    private val customDay: Int
        get() = SharedPreUtil.getParam(
            Constants.MainInstance.getContext(),
            KEY_CUSTOM_MSG_TODAY,
            0
        ) as Int
}