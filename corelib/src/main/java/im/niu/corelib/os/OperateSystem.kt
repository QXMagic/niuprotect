package im.niu.corelib.os

import im.niu.corelib.utils.ILog
import im.niu.corelib.utils.RomUtil

abstract class OperateSystem {

    open fun getName():String{
        return name
    }
    fun getVersion(): String? {
        return RomUtil.version
    }

    fun getAndroidVersion():String{
        var vs = RomUtil.getProp("ro.build.version.sdk")
        return vs?:"0"
    }

    abstract fun onSettingAppFilter()

    companion object {
        private var os: OperateSystem? = null
        protected var name:String = "unknown"
        fun getOsSetting(): OperateSystem {
            if (os==null) {
                name = RomUtil.name?:name
                if (RomUtil.isMiui) {
                    os = OsMIUI()
                }else if (RomUtil.isRealMe){
                    os = OsRealMe()
                }else{
                    os = OsUnknown(RomUtil.name)
                }
            }
            ILog.d("OS","OS:"+os!!.getName()+",version="+os!!.getVersion())
            return os!!
        }
    }
}
