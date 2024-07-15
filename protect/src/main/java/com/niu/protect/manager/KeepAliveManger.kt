package com.niu.protect.manager

import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import com.niu.protect.keepalive.KeepAliveJobService
import com.niu.protect.keepalive.LocalForegroundService
import com.niu.protect.keepalive.RemoteForegroundService
import com.niu.protect.service.FloatingService

/**
 * 保活
 *  */
class KeepAliveManger {
    fun keepAliveByTowService(context: Context) {
        context.startForegroundService(Intent(context, LocalForegroundService::class.java))
        context.startForegroundService(Intent(context, RemoteForegroundService::class.java))
        KeepAliveJobService.startJob(context)
    }

    fun showNotification(service: Service?) {
        MineNotificationManager.getInstance().onCreate(service)
    }

    private fun showOnePix(context: Context) {
        val floatWinIntent = Intent(context, FloatingService::class.java)
        context.startService(floatWinIntent)
    }

    private fun keepalive(context: Application) {}

    companion object {
        var instance: KeepAliveManger? = null
            get() {
                if (field == null) {
                    synchronized(KeepAliveManger::class.java) {
                        if (field == null) {
                            field = KeepAliveManger()
                        }
                    }
                }
                return field
            }
            private set
    }
}