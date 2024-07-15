package im.niu.corelib.net

import java.nio.ByteBuffer

interface IMessageSender {
    fun send(msg:String)
    fun send(data:ByteArray)
    fun send(data: ByteBuffer)
}