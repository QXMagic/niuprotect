package im.niu.corelib.net

import com.google.protobuf.Message
import im.niu.corelib.utils.ILog
import im.niu.data.Userinfo

class MessageWraper:IMessage {
    val TAG = MessageWraper::class.java.simpleName
    var data:Message? = null
    var type:Int = 0

    override fun type(): Int {
        return type
    }

    override fun toMessage(): String {
        TODO("Not yet implemented")
    }

    override fun flush(sender: IMessageSender) {
        ILog.d(TAG,"type is $type")
        if(data!=null){
            var arr = Userinfo.Wrapper.newBuilder().setName(data!!.descriptorForType.name).setData(data!!.toByteString()).build().toByteArray()
//            var arr = data!!.toByteArray()
            ILog.d(TAG,arr.toString())
            sender.send(arr)
        }
    }
}
