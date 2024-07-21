package im.niu.corelib.data

import android.app.usage.UsageEvents
import im.niu.corelib.utils.NiuUtil

/**
 *
 */
class OneTimeDetails(@JvmField var pkgName: String, @JvmField var useTime: Long, private var eventList: ArrayList<UsageEvents.Event>) {

    val startTime: String?
        get() {
            var startTime: String? = null
            if (eventList.size > 0) {
                startTime = NiuUtil.stampToDate(eventList[0].timeStamp)
            }
            return startTime
        }
    val stopTime: String?
        get() {
            var stopTime: String? = null
            if (eventList.size > 0) {
                stopTime =
                    NiuUtil.stampToDate(eventList[eventList.size - 1].timeStamp)
            }
            return stopTime
        }
}