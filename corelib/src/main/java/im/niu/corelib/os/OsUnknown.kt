package im.niu.corelib.os

class OsUnknown(private var name:String?) : OperateSystem(){
    override fun getName():String {
        return "unknown os: $name"
    }

    override fun onSettingAppFilter() {

    }

}