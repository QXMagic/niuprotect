package im.niu.corelib.net

import com.tencent.mmkv.MMKV
import im.niu.corelib.utils.ILog
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.UUID

class WebSocketManager {
    private val TAG:String = "WebSocket"
    private var client:WebSClient
    private var enable = false

    fun isEnable():Boolean{
        if(!enable){
            client.connect()
        }
        return enable
    }

    inner class WebSClient : WebSocketClient,IMessageSender {
        constructor(serverUri: URI?) : super(serverUri)
        constructor(serverUri: URI?, protocolDraft: Draft?) : super(serverUri, protocolDraft)

        override fun onOpen(handshakedata: ServerHandshake) {
            ILog.d(TAG, "getHttpStatus:" + handshakedata.httpStatus.toInt()+",StatusMessage:" + handshakedata.httpStatusMessage)
            enable = true
        }

        override fun onMessage(msg: String) {
            ILog.d(TAG, "rec msg :$msg")
            dispatchMessage(msg)
        }

        override fun onClose(code: Int, reason: String, remote: Boolean) {
            ILog.d(TAG, "onClose:$code,reason:$reason remote:$remote")
            enable = false
        }

        override fun onError(ex: Exception) {
            ILog.d(TAG, "onError--" + ex.message)
            enable = false
        }
    }

    fun reconnect(){
        if(client.isOpen){
            client.close()
        }
        client.connect()
    }

    constructor(ip:String,port:Int){
        val kv = MMKV.defaultMMKV();
        var uuid = kv.getString("uuid","");
        if(uuid.isNullOrBlank()){
            uuid = UUID.randomUUID().toString()
            kv.putString("uuid",uuid)
        }
        client = WebSClient(URI("ws://$ip:$port/monitor/$uuid"))
        client.connect()
    }

    private fun dispatchMessage(msg:String){

    }

    public fun sendMessage(msg:IMessage){
        ILog.e(TAG, "send msg--${msg.type()}")
        if(enable && client.isOpen){
            msg.flush(client)
        }else{
            ILog.e(TAG,"socket is closed $enable , open?${client.isOpen}")
        }
    }

}

