package com.niu.protect.mm

import android.content.Context
import com.niu.protect.manager.DeviceIdManager
import com.niu.protect.network.StudentBaseUrl
import com.niu.protect.tools.ILog
import im.niu.corelib.App
import im.niu.corelib.manager.AppDataManager
import im.niu.corelib.manager.AppLimitManager
import im.niu.corelib.net.WebSocketManager
import org.litepal.LitePal

/**
 * corelib protobuf 管控栈的启动器（方案 B）。
 *
 * corelib 自带完整的 protobuf WebSocket + AppLimitManager 拦截引擎，但原来靠
 * [im.niu.corelib.App] 作为 Application 才会初始化。本类用我们的设备身份
 * (device_id + device_token) 填充 App 的 companion 单例，从而复用整套引擎。
 *
 * - 仅在设备已绑定时启动（未绑定无凭证，WS 会被服务端拒绝）。
 * - 无障碍服务通过 [isForbid] / [isAllow] 查询管控结果。
 */
object MmControl {

    private const val TAG = "MmControl"
    private var started = false

    /** 解析 wss://ws.xinyu126.com:9090/monitor/ 里的 host/port/secure */
    private data class Endpoint(val host: String, val port: Int, val secure: Boolean)

    private fun parseEndpoint(): Endpoint {
        return try {
            val uri = java.net.URI(StudentBaseUrl.WEBSOCKET_URI)
            val secure = uri.scheme == "wss"
            val port = if (uri.port > 0) uri.port else if (secure) 443 else 80
            Endpoint(uri.host, port, secure)
        } catch (e: Exception) {
            ILog.d(TAG, "parse ws endpoint error: ${e.message}")
            Endpoint("ws.xinyu126.com", 9090, true)
        }
    }

    @Synchronized
    @JvmStatic
    fun start(context: Context) {
        if (started) return
        if (!DeviceIdManager.getInstance().isBound) {
            ILog.d(TAG, "device not bound, skip mm control start")
            return
        }
        try {
            LitePal.initialize(context.applicationContext)
            LitePal.getDatabase()

            val ep = parseEndpoint()
            val deviceId = DeviceIdManager.getInstance().deviceId
            val token = DeviceIdManager.getInstance().deviceToken

            App.appLimit = AppLimitManager()
            App.appManager = AppDataManager(context.applicationContext)
            App.webSocketManager = WebSocketManager(ep.host, ep.port, deviceId, token, ep.secure)
            App.webSocketManager.syncData()

            started = true
            ILog.d(TAG, "mm control started: ${ep.host}:${ep.port} secure=${ep.secure} uid=$deviceId")
        } catch (e: Throwable) {
            ILog.d(TAG, "mm control start failed: ${e.message}")
        }
    }

    @JvmStatic
    fun isStarted(): Boolean = started

    /** 是否禁止该应用（true = 拦截/踢出） */
    @JvmStatic
    fun isForbid(packageName: String, event: android.view.accessibility.AccessibilityEvent): Boolean {
        if (!started) return false
        return try {
            App.appLimit.isForbid(packageName, event)
        } catch (e: Throwable) {
            ILog.d(TAG, "isForbid error: ${e.message}")
            false
        }
    }

    /** 是否明确允许该应用（true = 放行，跳过后续检测） */
    @JvmStatic
    fun isAllow(packageName: String, event: android.view.accessibility.AccessibilityEvent): Boolean {
        if (!started) return false
        return try {
            App.appLimit.isAllow(packageName, event)
        } catch (e: Throwable) {
            ILog.d(TAG, "isAllow error: ${e.message}")
            false
        }
    }

    /** 是否处于白名单模式（配置了至少一个白名单 app） */
    @JvmStatic
    fun hasWhiteList(): Boolean {
        if (!started) return false
        return try {
            App.appLimit.hasWhiteList()
        } catch (e: Throwable) {
            false
        }
    }
}
