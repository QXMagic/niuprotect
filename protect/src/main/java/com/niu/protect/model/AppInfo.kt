package com.niu.protect.model

import android.content.Intent
import android.graphics.drawable.Drawable

class AppInfo {
    var appId: String? = null
    @JvmField
    var appImage: String? = null
    @JvmField
    var appName: String? = null
    @JvmField
    var ico: Drawable? = null
    var id: String? = null
    @JvmField
    var intent: Intent? = null
    @JvmField
    var isDefault = false
    @JvmField
    var name: String? = null
    @JvmField
    var packageName: String? = null
    var studentId: String? = null
    @JvmField
    var type = 0
    @JvmField
    var useTime = 0
}