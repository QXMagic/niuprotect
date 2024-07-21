package im.niu.corelib.net.handle

import com.google.protobuf.ByteString


interface IMessageHandle {

    /**
     * 处理接收到的消息。
     *
     * 当有新的消息到达时，此函数将被调用。消息以ByteString的形式传递，可以是任何二进制数据，
     * 例如文本消息、图像消息等。函数的目的是为了处理这些消息，具体的处理逻辑取决于应用程序的实现。
     *
     * @param bytes 接收到的消息，如果消息为空，则参数为null。
     */
    fun onMessage(bytes: ByteString?)

    fun getMessageName():String
}