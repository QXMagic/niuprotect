package im.niu.corelib.net.handle

import com.google.protobuf.ByteString
import im.niu.corelib.data.AppSetting
import im.niu.corelib.data.TimeSetting
import im.niu.data.Userinfo
import org.litepal.LitePal
import org.litepal.extension.delete

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
            data.type = type
            data.version = version
            var old = LitePal.find(AppSetting::class.java,data.id)
            if(old!=null){
                if(type == TimeSetting.TYPE_DELETE){
                    LitePal.delete(AppSetting::class.java,data.id)
                }else{
                    data.update(data.id)
                }
            }else {
                data.save()
            }
        }
    }

    override fun getMessageName(): String {
        return Userinfo.AppSetting.getDescriptor().fullName
    }
}