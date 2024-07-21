package im.niu.corelib.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.text.TextUtils
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class NiuUtil {

    companion object {
        private val TAG = "Util"
        private fun _isServiceRunning(context: Context, serviceName: String): Boolean {
            if (TextUtils.isEmpty(serviceName)) {
                return false
            }
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val infos = activityManager.getRunningServices(200)
            for (info in infos) {
                ILog.d(TAG, "serviceName: " + info.service.className)
                if (TextUtils.equals(info.service.className, serviceName)) {
                    return true
                }
            }
            return false
        }

        fun isAppRunning(context: Context, packageName: String): Boolean {
            if (TextUtils.isEmpty(packageName)) {
                return false
            }
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val infos = activityManager.runningAppProcesses
            for (info in infos) {
                if (TextUtils.equals(info.processName, packageName)) {
                    return true
                }
            }
            return false
        }

        fun isServiceRunning(context: Context,serviceName:String):Boolean{
            val run = _isServiceRunning(context,serviceName)
            ILog.d(TAG,"isServiceRunning:$run")
            if(run){
                return true
            }
            val serviceIntent = Intent()
            serviceIntent.setPackage(context.packageName)
            val resolveInfos: List<ResolveInfo> =
                context.packageManager.queryIntentServices(serviceIntent, 0)
            for (resolveInfo in resolveInfos) {
                Log.d(TAG, "Intent service name: " + resolveInfo.serviceInfo)
                if(serviceName == resolveInfo.serviceInfo.name){
                    ILog.d(TAG,"isServiceRunning:true")
                    return true
                }
            }
            ILog.d(TAG,"isServiceRunning:false")
            return false
        }

        fun timeToDayStart(timest: Long): Long {
            val format = SimpleDateFormat("yyyy-MM-dd")
            var date = Date(timest)
            val dateStr = format.format(date)
            val dateStr2 = "$dateStr 00:00:00"
            val format2 =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            try {
                date = format2.parse(dateStr2)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            ILog.d(TAG,"date start is $date")
            return date.time
        }

        private val dateFormat = SimpleDateFormat("M-d-yyyy")

        const val DAY_IN_MILLIS = (24 * 60 * 60 * 1000).toLong()
        @JvmStatic
        /*
         * 将时间戳转换为时间
         */
        fun stampToDate(stamp: String): String? {
            //String to long
            var time:Long = try {
                stamp.toLong()
            } catch (e: NumberFormatException) {
                0L
            }
            return stampToDate(time)
        }
        @JvmStatic
        fun stampToDate(stamp: Long): String? {
            val res: String
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = Date(stamp)
            res = simpleDateFormat.format(date)
            return res
        }

        //获取今日某时间的时间戳
        fun getTodayStartStamp(hour: Int, minute: Int, second: Int): Long {
            val cal: Calendar = Calendar.getInstance()
            cal.setTimeInMillis(System.currentTimeMillis())
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            cal.set(Calendar.SECOND, second)
            val todayStamp: Long = cal.getTimeInMillis()
            Log.i(
                "Wingbu",
                " DateTransUtils-getTodayStartStamp()  获取当日" + hour + ":" + minute + ":" + second + "的时间戳 :" + todayStamp
            )
            return todayStamp
        }

        @JvmStatic
        //获取当日00:00:00的时间戳,东八区则为早上八点
        fun getZeroClockTimestamp(time: Long): Long {
            var currentStamp = time
            currentStamp -= currentStamp % DAY_IN_MILLIS
            ILog.i(
                TAG,
                " DateTransUtils-getZeroClockTimestamp()  获取当日00:00:00的时间戳,东八区则为早上八点 :$currentStamp"
            )
            return currentStamp
        }

        //获取最近7天的日期,用于查询这7天的系统数据
        fun getSearchDays(): ArrayList<String>? {
            val dayList = ArrayList<String>()
            for (i in 0..6) {
                dayList.add(getDateString(i))
            }
            return dayList
        }

        //获取dayNumber天前，当天的日期字符串
        fun getDateString(dayNumber: Int): String {
            val time = System.currentTimeMillis() - dayNumber * DAY_IN_MILLIS
            Log.i(
                "Wingbu",
                " DateTransUtils-getDateString()  获取查询的日期 :" + dateFormat.format(time)
            )
            return dateFormat.format(time)
        }




    }
}