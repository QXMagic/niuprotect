package im.niu.corelib.manager

import android.app.usage.UsageEvents
import android.content.ContentValues
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.TextUtils
import com.google.protobuf.ByteString
import im.niu.corelib.App
import im.niu.corelib.Constants
import im.niu.corelib.data.AppInfo
import im.niu.corelib.events.MessageEvent
import im.niu.corelib.utils.EventUtils
import im.niu.corelib.utils.ILog
//import im.niu.corelib.data.OneTimeDetails
//import im.niu.corelib.data.PackageInfo
import im.niu.corelib.utils.NiuUtil.Companion.timeToDayStart
import im.niu.data.Userinfo
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.find
import org.litepal.extension.update
import java.io.ByteArrayOutputStream

/**
 * 主要的数据操作的类
 *
 *
 */
class AppDataManager() {

    private val packagesMap: HashMap<String,Boolean> = HashMap()
    private var mStartTime: Long = 0
//    private lateinit var mContext: Context


    constructor(context: Context) : this() {
//        this.mContext = context
        mStartTime = context.getSharedPreferences(Constants.SharedData_NAME, Context.MODE_PRIVATE).getLong("startTime", 0)
        if(mStartTime<1){
            mStartTime = timeToDayStart(System.currentTimeMillis())
        }
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onMessage(msg:MessageEvent){

    }

    /** 发送心跳 Ping（带电量/屏幕状态），服务端据此刷新在线/电量/最后在线时间 */
    fun pushPing(battery: Int, screen: Int) {
        val ping = Userinfo.Ping.newBuilder().setBattery(battery).setScreen(screen).build()
        App.webSocketManager.sendMessage(ping)
    }

    fun pushUsageEvent(mContext:Context){
        val now = System.currentTimeMillis()
        val mEventList = getEventList(mContext)
        val usageEventList = Userinfo.UsageEventList.newBuilder()
        for (event: UsageEvents.Event in mEventList){
            val msg = Userinfo.UsageEvent.newBuilder().setEventType(event.eventType)
                .setPackageName(event.packageName).setTimeStamp(event.timeStamp)
                .setClassName(event.className).build()
            usageEventList.addUsageEvent(msg)
        }
        if(App.webSocketManager.sendMessage(usageEventList.build())){
            mStartTime = now
            mContext.getSharedPreferences(Constants.SharedData_NAME, Context.MODE_PRIVATE).edit().putLong("startTime", mStartTime).apply()
        }
    }

    //从系统中获取event数据
    private fun getEventList(mContext:Context): ArrayList<UsageEvents.Event> {
        val endTime = System.currentTimeMillis()
        return EventUtils.getEventList(mContext, mStartTime, endTime)
    }

    /**
     * 上报"今日每个应用的前台使用时长"（用于家长端使用统计）。
     * queryAndAggregateUsageStats 直接给出 totalTimeInForeground，已过滤系统应用。
     */
    fun pushAppUseInfo(mContext: Context) {
        val now = System.currentTimeMillis()
        val dayStart = timeToDayStart(now)
        val list = EventUtils.getUsageList(mContext, dayStart, now)
        val builder = Userinfo.AppUseInfoList.newBuilder()
        for (s in list) {
            builder.addAppUseInfo(
                Userinfo.AppUseInfo.newBuilder()
                    .setPackageName(s.packageName)
                    .setFirstTimeStamp(s.firstTimeStamp)
                    .setLastTimeStamp(s.lastTimeStamp)
                    .setLastTimeUsed(s.lastTimeUsed)
                    .setTotalTimeInForeground(s.totalTimeInForeground)
                    .setTotalTimeVisible(s.totalTimeInForeground)
                    .build()
            )
        }
        ILog.d(TAG, "pushAppUseInfo: apps=${builder.appUseInfoCount}")
        if (builder.appUseInfoCount > 0) {
            App.webSocketManager.sendMessage(builder.build())
        }
    }

    //判断app是否为系统qpp
    fun isSystemApp(context: Context, packageName: String?): Boolean {
        if (TextUtils.isEmpty(packageName)){
            return false
        }
        if(packagesMap.containsKey(packageName)){
            return packagesMap[packageName]!!
        }
        var rs = false
        try {
            val pm = context.packageManager
            val ai = pm.getApplicationInfo(packageName!!, 0)
            if(ai.flags and ApplicationInfo.FLAG_SYSTEM != 0){
                rs = true
            }
        } catch (_: PackageManager.NameNotFoundException) {
        }
        packagesMap[packageName!!] = rs
        return rs
    }


    fun pushAppList(mContext:Context){
        val pm = mContext.packageManager
        val list =pm.getInstalledApplications(0)
        Thread {
            val version= System.currentTimeMillis()
            var sysCount = 0
            var sentCount = 0
            ILog.d(TAG, "pushAppList: installed total=${list.size}")
            for (i in list.indices){
                val app = list[i]
                if(isSystemApp(mContext,app.packageName)){
                    sysCount++
                    continue
                }
                // 跳过管控 App 自身（用真实运行包名，Constants.APPLICATION_ID 是库默认值不可靠）
                if(app.packageName == mContext.packageName){
                    continue
                }

                val result = LitePal.where("packageName=?",app.packageName).find<AppInfo>()
                //忽略已存在的
                if(result.isNotEmpty()){
                    val content = ContentValues()
                    content.put("versionCode",version)
                    LitePal.update(AppInfo::class.java,content, result.first().getId())
                    continue
                }
                ILog.d(TAG,"installed app:"+app.name+","+app.loadLabel(pm).toString())
                val appInfo = AppInfo()
                appInfo.packageName = app.packageName
                appInfo.appName = app.loadLabel(pm).toString()
                appInfo.name = if (TextUtils.isEmpty(app.name)) app.packageName else app.name
                appInfo.category = app.category
                appInfo.icon = app.loadIcon(pm)

                var message = Userinfo.AppInfo.newBuilder().setAppName(appInfo.appName).setType(appInfo.category).setName(appInfo.name).setPackageName(appInfo.packageName);
                val drawable: Drawable? = appInfo.icon
                if (drawable != null) {
                    val bitmap: Bitmap = drawableToBitmap(drawable)
                    val byteArray= bitmap2Bytes(bitmap)
                    message = message.setIcon(ByteString.copyFrom(byteArray))
                }
                if(App.webSocketManager.sendMessage(message.build())){
                    sentCount++
                    appInfo.save()
                }
            }
            ILog.d(TAG, "pushAppList: total=${list.size} sys=$sysCount sent=$sentCount")
            val delete = LitePal.where("versionCode<?", version.toString()).find(AppInfo::class.java)
            if (delete.isEmpty()){
                var message = Userinfo.RemoveApp.newBuilder()
                for (appInfo: AppInfo in delete){
                    message = message.addPackageName(appInfo.packageName)
                }
                if(App.webSocketManager.sendMessage(message.build())){
                    LitePal.deleteAll<AppInfo>("versionCode<?", version.toString())
                }
            }
        }.start()
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }

    private fun bitmap2Bytes(bm: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }

    companion object {
        const val TAG = "AppManager"
    }
}