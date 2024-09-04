package im.niu.corelib.net.handle

import com.google.protobuf.ByteString
import im.niu.corelib.data.TimeSetting
import im.niu.corelib.events.RefreshDataEvent
import im.niu.data.Userinfo
import org.litepal.LitePal

class TimeLimitHandle : MessageHandle() {
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
                if(type == TimeSetting.TYPE_DELETE){
                    LitePal.delete(TimeSetting::class.java,data.id)
                }else{
                    data.update(data.id)
                }
            }else {
                data.save()
            }
            delayEvent(RefreshDataEvent())
        }
    }

    override fun getMessageName(): String {
        return Userinfo.TimeLimit.getDescriptor().fullName
    }

}