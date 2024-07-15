package com.niu.protect.manager

import com.niu.protect.manager.SharedPreManager.saveAutoSettingFinish
import com.niu.protect.tools.ILog

object AutoSettingManager {
    const val AUTO_SETTING_NOT_SET = 0
    const val AUTO_SETTING_HAND = 1
    const val AUTO_SETTING_AUTO = 2
    const val AUTO_SETTING_FINISH = 3
//    private val instance: AutoSettingManager? = null
    var autoSettingFinish: Int
        get() = SharedPreManager.autoSettingFinish
        set(step) {
            saveAutoSettingFinish(step)
        }
    val isNeedAutoSetting: Boolean
        get() {
            val i = autoSettingFinish
            return i != AUTO_SETTING_FINISH && i != AUTO_SETTING_HAND
        }

    @get:Synchronized
    val isSettingFinish: Boolean
        get() {
            val i = autoSettingFinish
            ILog.d("auto set", "------$i")
            return i == AUTO_SETTING_FINISH
        }
}