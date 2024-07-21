package im.niu.corelib.manager

import android.content.Context
import im.niu.corelib.events.EventType
import im.niu.corelib.events.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AppLimitManager {
    constructor(context: Context) {
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: MessageEvent) {
        if(event.type== EventType.TIME_LIMIT){
            refreshData()
        }
    }

    private fun refreshData(){
        
    }

    companion object {
        const val TAG = "LimitManager"
    }
}