package im.niu.corelib.net.handle

import com.google.protobuf.ByteString
import im.niu.corelib.data.AppSetting
import im.niu.corelib.data.TimeSetting
import im.niu.corelib.events.RefreshDataEvent
import im.niu.data.Userinfo
import org.greenrobot.eventbus.EventBus
import org.litepal.LitePal
import org.litepal.extension.delete

class AppSettingHandle : IMessageHandle{
    override fun onMessage(bytes: ByteString?) {
        if(bytes!=null){
            val setting = Userinfo.AppSetting.parseFrom(bytes)
            val type = setting.type
            val version = setting.version
            val timeLimit = setting.timeLimit
            val packageName = setting.packageName
            val data = AppSetting()
            data.id = setting.id
            data.packageName = packageName
            data.timeLimit = timeLimit
            data.type = type
            data.version = version
            val old = LitePal.find(AppSetting::class.java,data.id)
            if(old!=null){
                if(type == TimeSetting.TYPE_DELETE){
                    LitePal.delete(AppSetting::class.java,data.id)
                }else{
                    data.update(data.id)
                }
            }else {
                data.save()
            }
            EventBus.getDefault().post(RefreshDataEvent())
        }
    }

    override fun getMessageName(): String {
        return Userinfo.AppSetting.getDescriptor().fullName
    }
}