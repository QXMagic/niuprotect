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
        // 仅在主进程启动：无障碍服务在主进程，且避免保活(:remote)进程另开一条同 uid 的 WS
        // 导致 python clients[uid] 相互覆盖、下行响应被路由到另一条连接。
        val proc = try { android.app.Application.getProcessName() } catch (e: Throwable) { "" }
        if (proc.isNotEmpty() && proc != context.packageName) {
            ILog.d(TAG, "skip mm control in process $proc")
            return
        }
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

            val appCtx = context.applicationContext
            App.appLimit = AppLimitManager()
            App.appManager = AppDataManager(appCtx)
            App.webSocketManager = WebSocketManager(ep.host, ep.port, deviceId, token, ep.secure)
            App.webSocketManager.syncData()

            started = true
            ILog.d(TAG, "mm control started: ${ep.host}:${ep.port} secure=${ep.secure} uid=$deviceId")

            reportApps(appCtx)
        } catch (e: Throwable) {
            ILog.d(TAG, "mm control start failed: ${e.message}")
        }
    }

    /**
     * WS 连上后上报已装应用列表（写入服务端 la_mobile_appinfo，家长端才有 app 可管控）。
     * 后台线程等待连接就绪（最多约 30s），再调 corelib 的 pushAppList。
     */
    private fun reportApps(appCtx: Context) {
        Thread {
            try {
                var waited = 0
                while (waited < 30000 && !App.webSocketManager.isEnable()) {
                    Thread.sleep(1000)
                    waited += 1000
                }
                if (App.webSocketManager.isEnable()) {
                    App.appManager.pushAppList(appCtx)
                    ILog.d(TAG, "pushAppList done")
                } else {
                    ILog.d(TAG, "pushAppList skipped: ws not connected")
                }
            } catch (e: Throwable) {
                ILog.d(TAG, "reportApps error: ${e.message}")
            }
        }.start()
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
