package im.niu.corelib.net.handle

import android.os.Handler
import android.os.Looper
import com.google.protobuf.ByteString
import im.niu.corelib.data.TimeSetting
import im.niu.corelib.events.RefreshDataEvent
import im.niu.data.Userinfo
import org.greenrobot.eventbus.EventBus
import org.litepal.LitePal

class TimeLimitHandle : IMessageHandle{
    private var postIdx=0
    private var dataIdx = 0
    override fun onMessage(bytes: ByteString?) {
        if(bytes!=null){
            val setting = Userinfo.TimeLimit.parseFrom(bytes)
            val type = setting.type
            val timeLimit = setting.limit
            val startTime = setting.timeStart
            val endTime = setting.timeEnd
            val data = TimeSetting()
            data.id = setting.id
            data.timeLimit = timeLimit
            data.startTime = startTime
            data.endTime = endTime
            data.type = type
            data.version = setting.version
            val old = LitePal.find(TimeSetting::class.java,data.id)
            if(old!=null){
                data.update(data.id)
                if(type == TimeSetting.TYPE_DELETE){
                    LitePal.delete(TimeSetting::class.java,data.id)
                }
            }else {
                data.save()
            }
            val handler = Handler(Looper.getMainLooper())
            val runnable = Runnable {
                postEvent()
            }
            dataIdx += 1
            handler.postDelayed(runnable, 1000)
        }
    }

    override fun getMessageName(): String {
        return Userinfo.TimeLimit.getDescriptor().fullName
    }

    private fun postEvent(){
        postIdx++
        if(postIdx==dataIdx){
            EventBus.getDefault().post(RefreshDataEvent())
        }
    }
}