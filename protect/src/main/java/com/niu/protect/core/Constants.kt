package com.niu.protect.core

object Constants {
    const val COLON_SEPARATOR = ":"
    const val APP_NAME = "妞管家"

    const val APPLICATION_ID = "com.niu.protect"
     const val MSG_ACCS_NOTIFY_CLICK = "8"
     const val MSG_ACCS_NOTIFY_DISMISS = "9"
     const val MSG_ACCS_READY_REPORT = "4"
     const val MSG_DB_COMPLETE = "100"
     const val MSG_DB_NOTIFY_CLICK = "2"
     const val MSG_DB_NOTIFY_DISMISS = "3"
     const val MSG_DB_NOTIFY_REACHED = "1"
     const val MSG_DB_READY_REPORT = "0"
     const val KEY_PACKAGE_NAME = "packageName"
     const val KEY_PACKAGE_NAMES = "packageNames"
     const val MESSAGE_TRACE = "trace"
     const val RESULT = "result"
     const val DEFAULT_CONTENT_TYPE = "application/json"
     const val MESSAGE_LOCAL = "local"
     const val PLATFORM = "platform"
     const val COMMAND_PING = 1
     const val DEFAULT_TIMEOUT = 5000L
     const val CATEGORY_MASK = -65536
     const val APK_NAME_REG = "[\\w]+[\\.](apk"
     const val MESSAGE_SYSTEM_SOURCE_HUAWEI = "huawei"
     const val MESSAGE_SYSTEM_SOURCE_OPPO = "oppo"
     const val MESSAGE_SYSTEM_SOURCE_VIVO = "vivo"
     const val MESSAGE_SYSTEM_SOURCE_MEIZU = "meizu"
     const val ANDROID = "android"
     const val KEY_HTTP_CODE = "code"
     const val ACCEPT_TIME_SEPARATOR_SP = ":"
     const val KEY_USER_ID = "id"
     const val bzm = 0xf
     const val ACCEPT_TIME_SEPARATOR_SERVER = "/"
    lateinit var MainInstance: IGlobalInstance
    lateinit var GlobalInstance: IProtected

     const val ACTION_ACCESSIBILITY_START = "action_app_accessibility_start"
     const val ACTION_ACCESSIBILITY_STOP = "action_app_accessibility_stop"
}