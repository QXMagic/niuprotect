package com.niu.protect.accessibility.auto.app

import android.app.ActivityManager
import android.content.Context

object CheckAppRunninng {
    fun isServiceWork(mContext: Context, serviceName: String): Boolean {
        val myAM = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val myList = myAM.getRunningServices(50)
        if (myList.size <= 0) {
            return false
        }
        for (i in myList.indices) {
            val mName = myList[i].service.className
            if (mName == serviceName) {
                return true
            }
        }
        return false
    }
}