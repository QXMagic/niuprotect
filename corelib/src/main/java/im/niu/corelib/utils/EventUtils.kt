package im.niu.corelib.utils

import android.app.usage.UsageEvents
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import im.niu.corelib.App
import im.niu.corelib.Constants
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 获取系统的数据，包括event和Usage
 *
 *
 * .
 */
object EventUtils {
    const val TAG = "EventUtils"
    private val dateFormat = SimpleDateFormat("M-d-yyyy HH:mm:ss", Locale.CHINA)

    /**
     * 获取指定时间范围内的应用使用事件列表。
     * 该方法通过查询UsageStatsManager来获取在指定时间范围内发生的暂停和恢复事件。
     * 这些事件可以用于分析用户在不同应用之间的行为切换。
     *
     * @param context 上下文对象，用于获取UsageStatsManager服务。
     * @param startTime 查询范围的起始时间，以毫秒为单位。
     * @param endTime 查询范围的结束时间，以毫秒为单位。
     * @return 包含指定时间范围内应用暂停和恢复事件的列表。
     */
    fun getEventList(
        context: Context,
        startTime: Long,
        endTime: Long
    ): ArrayList<UsageEvents.Event> {
        val mEventList = ArrayList<UsageEvents.Event>()
        Log.i(TAG, " Range start:" + dateFormat.format(startTime)  +" to "+ dateFormat.format(endTime))
        val mUsmManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val events = mUsmManager.queryEvents(startTime, endTime)
        while (events.hasNextEvent()) {
            val e = UsageEvents.Event()
            events.getNextEvent(e)
            if (e.eventType == UsageEvents.Event.ACTIVITY_PAUSED || e.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
                Log.i(
                    TAG,
                    " EventUtils-getEventList()  " + e.timeStamp + "   event:" + e.className + "   type = " + e.eventType
                )
                if (App.appManager.isSystemApp(context, e.packageName)) {
                    continue
                }
                if(Constants.APPLICATION_ID == e.packageName){
                    continue
                }
                mEventList.add(e)
            }
        }
        return mEventList
    }

    fun getUsageList(context: Context, startTime: Long, endTime: Long): ArrayList<UsageStats> {
        Log.i(TAG, " EventUtils-getUsageList()   Range start:" + dateFormat.format(startTime))
        Log.i(TAG, " EventUtils-getUsageList()   Range end:" + dateFormat.format(endTime))
        val list = ArrayList<UsageStats>()
        val mUsmManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val map = mUsmManager.queryAndAggregateUsageStats(startTime, endTime)
        for ((_, stats) in map) {
            if(App.appManager.isSystemApp(context, stats.packageName)){
                continue
            }
            // 排除管控 App 自身（用真实运行包名，Constants.APPLICATION_ID 是库默认值不可靠）
            if(context.packageName == stats.packageName){
                continue
            }
            if (stats.totalTimeInForeground > 0) {
                list.add(stats)
                Log.i(
                    TAG,
                    " EventUtils-getUsageList()   stats:" + stats.packageName + "   TotalTimeInForeground = " + stats.totalTimeInForeground
                )
            }
        }
        return list
    }
}