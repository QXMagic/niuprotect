package im.niu.corelib.net.handle

import com.google.protobuf.ByteString
import im.niu.corelib.data.AppSetting
import im.niu.corelib.data.TimeSetting
import im.niu.data.Userinfo
import org.litepal.LitePal

class AppSettingHandle : IMessageHandle{
    override fun onMessage(bytes: ByteString?) {
        if(bytes!=null){
            var setting = Userinfo.AppSetting.parseFrom(bytes)
            var type = setting.type
            var version = setting.version
            var timeLimit = setting.timeLimit
            var packageName = setting.packageName
            var data = AppSetting()
            data.id = setting.id
            data.packageName = packageName
            data.timeLimit = timeLimit
            LitePal.find(TimeSetting::class.java,data.id)
            data.save()
        }
    }

    override fun getMessageName(): String {
        return Userinfo.AppSetting.getDescriptor().fullName
    }
}