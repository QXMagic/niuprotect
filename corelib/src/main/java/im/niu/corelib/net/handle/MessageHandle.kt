package im.niu.corelib.net.handle

import android.os.Handler
import android.os.Looper
import im.niu.corelib.events.MessageEvent
import im.niu.corelib.utils.ILog
import org.greenrobot.eventbus.EventBus

abstract class MessageHandle:IMessageHandle {

    private var dataIdx = 0
    private var postIdx = 0
    private lateinit var event: MessageEvent
    protected fun delayEvent(event: MessageEvent){
        this.event = event
        if(dataIdx>0){
            dataIdx++
            return
        }
        dataIdx = 1
        postEvent()
    }
    private fun postEvent(){
        if(postIdx==dataIdx){
            ILog.d("MessageHandle","post Message")
            EventBus.getDefault().post(event)
            dataIdx = 0
            postIdx = 0
            return
        }
        postIdx = dataIdx
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            postEvent()
        }
        handler.postDelayed(runnable, 1000)
        ILog.d("MessageHandle","delay 1000ms")
    }
}