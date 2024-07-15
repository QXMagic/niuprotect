package com.niu.protect

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.multidex.MultiDex
import com.niu.protect.accessibility.auto.bean.PageInfoModel
import com.niu.protect.accessibility.auto.device.info.DeviceAccessiFactory
import com.niu.protect.core.Constants
import com.niu.protect.core.IGlobalInstance
import com.niu.protect.manager.KeepAliveManger
import com.niu.protect.third.bugly.BuglyTools
//import com.niu.protect.third.umeng.UMengManager
import com.niu.protect.tools.ILog
import com.niu.protect.ui.main.MainActivity
import com.tencent.mmkv.MMKV
import java.util.concurrent.atomic.AtomicInteger

class BabyApplication : Application(), IGlobalInstance {
    @JvmField
    var autoSettingSteps:MutableList<PageInfoModel>? = null
    private val mSequenceGenerator = AtomicInteger()
    private var notification: Notification? = null
    var mContext: Context? = null
    private var itemInfos: ArrayList<Any?> = ArrayList()
    @JvmField
    var trackConf: SharedPreferences? = null
    @JvmField
    var entityName = "BBA"
    @JvmField
    var isRegisterReceiver = false
    @JvmField
    var isTraceStarted = false
    @JvmField
    var isGatherStarted = false

    private var appChannel = "main"
    override fun onCreate() {
        super.onCreate()
        ILog.d(TAG,"app start")
        MMKV.initialize(this)
        instance = this
        mContext = this
        Constants.MainInstance = this
        getAppChannel()
        KeepAliveManger.instance?.keepAliveByTowService(this)
        init()
        autoSettingSteps = DeviceAccessiFactory.createDeviceInfo(instance)
    }

    fun setEntityName(entityName: String) {
        this.entityName = entityName
        initTrack()
    }

    fun initTrack() {
        trackConf = getSharedPreferences("track_conf", 0)

        initView()
        initNotification()

        clearTraceStatus()
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    private fun init() {
        ILog.d(TAG,"channel $appChannel")
//        UMengManager.preInit(this, appChannel)
        BuglyTools.initBugly(applicationContext)
        androiodScreenProperty
    }

    private val androiodScreenProperty: Unit
        get() {
//            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
//            wm.defaultDisplay.getMetrics(dm)
            width = dm.widthPixels
            height = dm.heightPixels
//            val f = dm.density
//            val i = dm.densityDpi
        }


    private fun initView() {
        screenSize
    }

    private fun initNotification() {

        val notificationIntent = Intent(this, MainActivity::class.java)
        val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel("trace", "trace_channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)
        val builder = Notification.Builder(this,"trace")
        builder
            .setContentIntent(
            PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
            .setLargeIcon(icon).setContentTitle(Constant.APP_NAME).setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("服务正在运行...").setWhen(
            System.currentTimeMillis()
        )
        builder.setChannelId("trace")
        notification = builder.build()
//        notification = build
//        build.defaults = 1
    }

    private val screenSize: Unit
        get() {
            val dm = resources.displayMetrics
            screenHeight = dm.heightPixels
            screenWidth = dm.widthPixels
        }

    private fun clearTraceStatus() {
        if (trackConf!!.contains("is_trace_started") || trackConf!!.contains("is_gather_started")) {
            val editor = trackConf!!.edit()
            editor.remove("is_trace_started")
            editor.remove("is_gather_started")
            editor.apply()
        }
    }

//    fun initRequest(request: BaseRequest) {
//        request.setTag(tag)
//        request.setServiceId(serviceId)
//    }

    val tag: Int
        get() = mSequenceGenerator.incrementAndGet()

    fun clear() {
        itemInfos.clear()
    }

    private fun getAppChannel() {
        val packageManager = packageManager
        try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)
            if (applicationInfo.metaData != null) {
                appChannel = applicationInfo.metaData.getString("channel").toString();
                ILog.d(TAG,"channel get $appChannel")
            }
        } catch (e: PackageManager.NameNotFoundException) {
            ILog.d(TAG,"channel get NameNotFoundException")
            e.printStackTrace()
        }
    }

    override fun getContext(): Context {
        return this.mContext!!;
    }

    companion object {
        @JvmStatic
        var instance: BabyApplication? = null
            private set
        var width = 0
        private const val TAG = "BabyApplication"
        @JvmField
        var height = 1000
        @JvmField
        var screenWidth = 0
        @JvmField
        var screenHeight = 0
    }


}