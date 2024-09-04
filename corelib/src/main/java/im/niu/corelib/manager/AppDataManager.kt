package im.niu.corelib.manager

import android.app.usage.UsageEvents
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
import org.litepal.extension.find
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
        var pm = mContext.packageManager
        var list =pm.getInstalledApplications(0)
        Thread {
            for (i in list.indices){
                var app = list[i]
                if(isSystemApp(mContext,app.packageName)){
                    continue
                }
                if(app.packageName.equals(Constants.APPLICATION_ID)){
                    continue
                }

                var result = LitePal.where("packageName=?",app.packageName).find<AppInfo>()
                //忽略已存在的
                if(result.isNotEmpty()){
                    continue
                }
                ILog.d(TAG,"installed app:"+app.name+","+app.loadLabel(pm).toString())
                var appInfo = AppInfo()
                appInfo.packageName = app.packageName
                appInfo.appName = app.loadLabel(pm).toString()
                appInfo.name = if (TextUtils.isEmpty(app.name)) app.packageName else app.name
                appInfo.category = app.category
                appInfo.icon = app.loadIcon(pm)

                var message = Userinfo.AppInfo.newBuilder().setAppName(appInfo.appName).setType(appInfo.category).setName(appInfo.name).setPackageName(appInfo.packageName);
                val drawable: Drawable? = appInfo.icon
                if (drawable != null) {
                    val bitmap: Bitmap = drawableToBitmap(drawable)
                    var byteArray= bitmap2Bytes(bitmap)
                    message = message.setIcon(ByteString.copyFrom(byteArray))
                }
                if(App.webSocketManager.sendMessage(message.build())){
                    appInfo.save()
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