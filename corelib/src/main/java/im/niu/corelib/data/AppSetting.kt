package im.niu.corelib.data

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

class AppSetting : LitePalSupport() {
    @Column(unique = true, nullable = false)
    var id:Long=0
    var packageName: String?=null
    var type:Int = TYPE_DEFAULT
    var timeLimit:Long = 0
    var version:Int = 0
    var timeStart:Int=0
    var timeEnd:Int=0
    @Column(ignore = true)
    var timeSetting:TimeSetting?=null
    companion object {
        const val TYPE_DEFAULT = 0
        const val TYPE_WHITE = 1
        const val TYPE_BLACK = 2
        const val TYPE_TIME = 3
        const val TYPE_DELETE = 4
    }
}