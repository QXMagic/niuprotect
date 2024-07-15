package im.niu.corelib.net

interface IMessage {
    fun type():Int
    fun toMessage():String
    fun flush(sender:IMessageSender)
}