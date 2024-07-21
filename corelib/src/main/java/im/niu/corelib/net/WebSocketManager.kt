package im.niu.corelib.net

import android.os.Handler
import android.os.Looper
import com.google.protobuf.Message
import com.tencent.mmkv.MMKV
import im.niu.corelib.data.TimeSetting
import im.niu.corelib.net.handle.IMessageHandle
import im.niu.corelib.net.handle.TimeLimitHandle
import im.niu.corelib.utils.ILog
import im.niu.data.Userinfo
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft
import org.java_websocket.handshake.ServerHandshake
import org.litepal.LitePal
import java.net.URI
import java.nio.ByteBuffer
import java.util.UUID

class WebSocketManager {
    private val TAG:String = "WebSocket"
    private var client:WebSClient
    private var enable = false
    private var connecting = false
    private var uri:URI;
    private var delayTime:Long = 5000L;
    private var handerMap:HashMap<String,IMessageHandle> = HashMap()

    constructor(ip:String,port:Int){
        val kv = MMKV.defaultMMKV();
        var uuid = kv.getString("uuid","");
        if(uuid.isNullOrBlank()){
            uuid = UUID.randomUUID().toString()
            kv.putString("uuid",uuid)
        }
        var msgHandle = TimeLimitHandle()
        handerMap[msgHandle.getMessageName()] = msgHandle
        connecting = true
        uri = URI("ws://$ip:$port/monitor/$uuid")
        client = WebSClient(uri)
        client.connect()
    }

    fun isEnable():Boolean{
        return enable
    }

    inner class WebSClient : WebSocketClient,IMessageSender {
        constructor(serverUri: URI?) : super(serverUri)
        constructor(serverUri: URI?, protocolDraft: Draft?) : super(serverUri, protocolDraft)

        override fun onOpen(handshakedata: ServerHandshake) {
            ILog.d(TAG, "Socket connect success,StatusCode:" + handshakedata.httpStatus.toInt()+",StatusMessage:" + handshakedata.httpStatusMessage)
            enable = true
            connecting = false
            delayTime = 5000L
            syncData()
        }

        override fun onMessage(bytes: ByteBuffer) {
            super.onMessage(bytes)
            val wrapper = Userinfo.Wrapper.parseFrom(bytes)
//            var msg = Userinfo.getDescriptor().findMessageTypeByName(wraper.name)
            ILog.i(TAG,"rec msg type:${wrapper.name} ")
            if(handerMap.containsKey(wrapper.name)){
                handerMap[wrapper.name]?.onMessage(wrapper.data)
            }
        }
        override fun onMessage(msg: String) {
            ILog.d(TAG, "rec msg :$msg")

            dispatchMessage(msg)
        }

        override fun onClose(code: Int, reason: String, remote: Boolean) {
            ILog.d(TAG, "onClose:$code,reason:$reason remote:$remote")
            enable = false
            connecting = false
            tryReconnect()
        }

        override fun onError(ex: Exception) {
            ILog.d(TAG, "onError--" + ex.message)
            enable = false
            connecting = false
            tryReconnect()
        }
    }

    private fun reconnect(){
        if(connecting){
            return
        }
        if(client.isOpen && enable){
            return
        }
        connecting = true
        client = WebSClient(uri)
        client.connect()
    }



    private fun dispatchMessage(msg:String){

    }


    private fun sendMessage(msg:IMessage):Boolean{
        ILog.e(TAG, "send msg--${msg.type()}")
        if(enable && client.isOpen){
            msg.flush(client)
            return true
        }else{
            ILog.e(TAG,"socket is closed $enable , open?${client.isOpen}")
            reconnect()
        }
        return false
    }

    fun sendMessage(msg: Message):Boolean {
        val wraper = MessageWraper()
        wraper.data = msg
        return sendMessage(wraper)
    }

    fun tryReconnect(){
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            reconnect()
        }
        handler.postDelayed(runnable, delayTime)
        if(delayTime > 60000){
            delayTime = 60000
        }else{
            delayTime += 2000
        }
    }

    fun syncData() {
        Thread {
            while (true) {
                Thread.sleep(1000)
                if (client.isOpen) {
                    val msg = MessageWraper()
                    var setting = LitePal.select("version").order("version desc").find(TimeSetting::class.java)
                    var version = if(setting.isEmpty())0 else setting[0].version
                    var data = Userinfo.SyncData.newBuilder().setType(1).setVersion(version).build()
                    msg.data = data
                    msg.flush(client)
                    break
                }
            }
        }.start()
    }
}

