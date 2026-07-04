package com.niu.protect.manager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import com.google.gson.Gson
import com.niu.protect.core.Constants
import com.niu.protect.model.EventMessageModel
import com.niu.protect.model.UserInfo
import com.niu.protect.model.WebsocketMessage
import com.niu.protect.model.eventbus.EventParentChangeBindMode
import com.niu.protect.network.StudentBaseUrl
import com.niu.protect.tools.ILog
import com.niu.protect.tools.apk.ApkTools
import com.niu.protect.websocket.BaseWebSocketEvent
import org.greenrobot.eventbus.EventBus
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.net.URISyntaxException

class WebSocketManager private constructor(){
    lateinit var cc: WebSClient
    var mContext: Context? = null
    var mGson: Gson? = null
    var userId: String? = null
    var packageName = ""
    private var mHandler: Handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            if (msg.what == TAG_MSG_SEND_MSG) {
                if (!cc.isClosed) {
                    cc.send(msg.obj as String)
                    ILog.d(TAG, "receive message:" + msg.obj)
                } else {
                    ILog.d(TAG, "socket client is closed")
                }
            }
        }
    }

    fun start() {
        mContext = Constants.MainInstance.getContext()
        userId = DeviceIdManager.getInstance().deviceId
        val uri = DeviceIdManager.getInstance().buildWebSocketUri()
        ILog.d(TAG, "-uri-$uri")
        try {
            val webSClient = WebSClient(URI(uri))
            cc = webSClient
            webSClient.close()
            cc.connect()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    private fun getUserId() {
        userId = DeviceIdManager.getInstance().deviceId
    }

    fun reconnect() {
        val webSClient = cc
        if (webSClient.isClosed) {
            try {
                cc.reconnect()
                ILog.d(TAG, "-reconnect---true")
            } catch (e: Exception) {
                ILog.d(TAG, "-reconnect---Exception")
            }
        }
    }

    private fun close() {
        val webSClient = cc
        webSClient.close()
        ILog.d(TAG, "-close-")
    }

    inner class WebSClient : WebSocketClient {
        constructor(serverUri: URI?) : super(serverUri)
        constructor(serverUri: URI?, protocolDraft: Draft?) : super(serverUri, protocolDraft)

        override fun onOpen(handshakedata: ServerHandshake) {
            ILog.d(TAG, "StatusMessage---" + handshakedata.httpStatusMessage)
            ILog.d(TAG, "getHttpStatus---" + handshakedata.httpStatus.toInt())
        }

        override fun onMessage(msg: String) {
            ILog.d(TAG, "rec msg --$msg")
            dealMsg(msg)
        }

        override fun onClose(code: Int, reason: String, remote: Boolean) {
            ILog.d(TAG, "onClose--$code---reason--$reason")
        }

        override fun onError(ex: Exception) {
            ILog.d(TAG, "onError--" + ex.message)
        }
    }

    fun dealMsg(msg: String) {
        if (!TextUtils.isEmpty(msg) && msg != "conn_success") {
            val mUmengCustomMsg =
                Gson().fromJson(msg, WebsocketMessage::class.java) as WebsocketMessage
            val type = mUmengCustomMsg.operateType
            ILog.d(TAG, "onMessage--orderorder ==$type")
            if (type == 12) {
//                this.mContext.startService(new Intent(this.mContext, LocationTraceService.class));
                return
            }
            if (type == 16 || type == 17) {
                mHandler.post {
                    EventBus.getDefault().post(EventParentChangeBindMode(""))
                    StudentMainController.getInstance().getUserWhiteApp()
                }
            } else if (type == 18) {
                mHandler.post { UserInfoManager.getInstance().refreshUserInfo(mContext) { } }
                //            } else if (type == 21) {
//                this.mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        OpenSettingApp.checkShowIcon(mContext, 1);
//                    }
//                });
//            } else if (type == 20) {
//                this.mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        OpenSettingApp.checkShowIcon(mContext, 2);
//                    }
//                });
            } else if (type == 22) {
                mHandler.post { TempOutControlManager.getInstance().requestOutControl(mContext) }
            } else if (type == 23) {
                mHandler.post { XcxControlManager.getInstance().requestSmallProgramlist(mContext) }
            } else {
                mHandler.post {
                    EventBus.getDefault().post(EventParentChangeBindMode(""))
                    StudentMainController.getInstance().requestMainControl()
                }
            }
            mHandler.obtainMessage(TAG_MSG_SEND_MSG, mUmengCustomMsg.memberId).sendToTarget()
        }
    }

    fun sendMessage(msg: String?) {
        val webSClient = cc
        if (!webSClient.isClosed) {
            cc.send(msg)
        }
    }

    fun sendMessageEventEntry(eventType: Int, o: Any?) {
        val webSocketEvent = BaseWebSocketEvent()
        webSocketEvent.type = eventType
        webSocketEvent.data = o
        val msg = Gson().toJson(webSocketEvent)
        ILog.d(TAG, "msg: $msg")
        sendMessage(msg)
    }

    fun sendEventMessage(eventType: String?, evenPackageName: String) {
        val appName: String
        if (TextUtils.isEmpty(evenPackageName)) {
            ILog.log("evenPackageName  is empty")
        } else if (packageName == evenPackageName) {
        } else {
            packageName = evenPackageName
            val webSClient = cc
            if (webSClient.isOpen) {
                if (TextUtils.isEmpty(userId)) {
                    getUserId()
                }
                if (!TextUtils.isEmpty(userId)) {
                    if (mGson == null) {
                        mGson = Gson()
                    }
                    if (evenPackageName.contains("launcher")) {
                        appName = "系统桌面"
                    } else {
                        val appInfo = ApkTools.getAPPInfoByPackageName(mContext, evenPackageName)
                        if (appInfo == null) {
                            appName = evenPackageName
                        } else {
                            appName = appInfo.appName.toString()
                        }
                    }
                    val eventMessage = "您的孩子正在使用$appName"
                    val mEventMessageModel = EventMessageModel(
                        userId,
                        eventType,
                        eventMessage,
                        System.currentTimeMillis(),
                        evenPackageName
                    )
                    val sendMessage = mGson!!.toJson(mEventMessageModel)
                    ILog.d("websockets", "sendMessage:$sendMessage")
                    cc.send(sendMessage)
                    return
                }
                ILog.log("websockets userId is empty")
            }
        }
    }

    fun sendScreenEventMessage(eventType: String?, eventName: String) {
        val webSClient = cc
        if (webSClient.isOpen) {
            if (TextUtils.isEmpty(userId)) {
                getUserId()
            }
            if (!TextUtils.isEmpty(userId)) {
                if (mGson == null) {
                    mGson = Gson()
                }
                val str = userId
                val mEventMessageModel = EventMessageModel(
                    str,
                    eventType,
                    "您的孩子$eventName",
                    System.currentTimeMillis(),
                    ""
                )
                val sendMessage = mGson!!.toJson(mEventMessageModel)
                ILog.d("websockets", "sendMessage:$sendMessage")
                cc.send(sendMessage)
                return
            }
            ILog.log("websockets userId is empty")
        }
    }

    fun onDestroy() {
        close()
    }

    companion object {
        private const val TAG = "WebSClient"
        const val TAG_MSG_SEND_MSG = 2
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var instance: WebSocketManager = WebSocketManager()
            private set
    }
}