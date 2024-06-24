package com.niu.protect

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.multidex.MultiDex
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baidu.trace.LBSTraceClient
import com.baidu.trace.Trace
import com.baidu.trace.api.entity.LocRequest
import com.baidu.trace.api.entity.OnEntityListener
import com.baidu.trace.api.track.LatestPointRequest
import com.baidu.trace.api.track.OnTrackListener
import com.baidu.trace.model.BaseRequest
import com.baidu.trace.model.OnCustomAttributeListener
import com.baidu.trace.model.ProcessOption
import com.niu.protect.accessibility.auto.bean.PageInfoModel
import com.niu.protect.accessibility.auto.device.info.DeviceAccessiFactory
import com.niu.protect.lib.Constants
import com.niu.protect.lib.IGlobalInstance
import com.niu.protect.manager.KeepAliveManger
import com.niu.protect.map.maputil.CommonUtil
import com.niu.protect.map.model.ItemInfo
import com.niu.protect.network.NetCheckUtil
import com.niu.protect.third.bugly.BuglyTools
import com.niu.protect.third.umeng.UMengManager
import com.niu.protect.tools.ILog
import com.niu.protect.ui.map.TracingActivity
import com.tencent.mmkv.MMKV
import java.util.concurrent.atomic.AtomicInteger

class BabyApplication : Application(), IGlobalInstance {
    @JvmField
    var autoSettingSteps:MutableList<PageInfoModel>? = null
    private val mSequenceGenerator = AtomicInteger()
    private var locRequest: LocRequest? = null
    private var notification: Notification? = null
    var mContext: Context? = null
    private var itemInfos: ArrayList<Any?> = ArrayList()
    @JvmField
    var trackConf: SharedPreferences? = null
    @JvmField
    var mClient: LBSTraceClient? = null
    @JvmField
    var mTrace: Trace? = null
    var serviceId: Long = 232360
    @JvmField
    var entityName = CommonUtil.ENTITY_NAME
    @JvmField
    var isRegisterReceiver = false
    @JvmField
    var isTraceStarted = false
    @JvmField
    var isGatherStarted = false

    var appChannel = "main"
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        instance = this
        mContext = this
        Constants.MainInstance = this
        getAppChannel()
        KeepAliveManger.getInstance().keepAliveByTowService(this)
        init()
        autoSettingSteps = DeviceAccessiFactory.createDeviceInfo(instance)
    }

    fun setEntityName(entityName: String) {
        this.entityName = entityName
        initTrack()
    }

    fun initTrack() {
        LBSTraceClient.setAgreePrivacy(mContext, true)
        if ("com.baidu.track:remote" == CommonUtil.getCurProcessName(mContext)) {
            return
        }
        initView()
        initNotification()
        try {
            mClient = LBSTraceClient(mContext)
        } catch (e: Exception) {
            e.message
            ILog.d("LBSTraceClient", e.message)
        }
        val trace = Trace(serviceId, entityName)
        mTrace = trace
        trace.notification = notification
        val sb = StringBuilder()
        sb.append(mClient != null)
        sb.append("")
        ILog.d("LBSTraceClient", sb.toString())
        trackConf = getSharedPreferences("track_conf", 0)
        locRequest = LocRequest(serviceId)
        val lBSTraceClient = mClient
        lBSTraceClient?.setOnCustomAttributeListener(object : OnCustomAttributeListener {
            override fun onTrackAttributeCallback(): Map<String, String> {
                val map: MutableMap<String, String> = HashMap()
                map["key1"] = "value1"
                map["key2"] = "value2"
                return map
            }

            override fun onTrackAttributeCallback(locTime: Long): Map<String, String> {
                val printStream = System.out
                printStream.println("onTrackAttributeCallback, locTime : $locTime")
                val map: MutableMap<String, String> = HashMap()
                map["key1"] = "value1"
                map["key2"] = "value2"
                return map
            }
        })
        clearTraceStatus()
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    private fun init() {
        SDKInitializer.setAgreePrivacy(this, true)
        SDKInitializer.setCoordType(CoordType.BD09LL)
        ILog.d("channel", appChannel)
        UMengManager.preInit(this, appChannel)
        BuglyTools.initBugly(applicationContext)
        androiodScreenProperty
    }

    private val androiodScreenProperty: Unit
        get() {
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            width = dm.widthPixels
            height = dm.heightPixels
//            val f = dm.density
//            val i = dm.densityDpi
        }

    fun getCurrentLocation(entityListener: OnEntityListener?, trackListener: OnTrackListener?) {
        if (mClient == null) {
            return
        }
        if (NetCheckUtil.isNetworkAvailable(mContext) && trackConf!!.contains("is_trace_started") && trackConf!!.contains(
                "is_gather_started"
            ) && trackConf!!.getBoolean(
                "is_trace_started",
                false
            ) && trackConf!!.getBoolean("is_gather_started", false)
        ) {
            val request = LatestPointRequest(tag, serviceId, entityName)
            val processOption = ProcessOption()
            processOption.isNeedDenoise = true
            processOption.radiusThreshold = 100
            request.processOption = processOption
            mClient!!.queryLatestPoint(request, trackListener)
            return
        }
        mClient!!.queryRealTimeLoc(locRequest, entityListener)
    }

    private fun initView() {
        screenSize
    }

    private fun initNotification() {

        val notificationIntent = Intent(this, TracingActivity::class.java)
        val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        var notificationChannel = NotificationChannel("trace", "trace_channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)
        val builder = Notification.Builder(this,"trace")
        builder.setContentIntent(
            PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
        ).setLargeIcon(icon).setContentTitle(Constant.APP_NAME).setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("服务正在运行...").setWhen(
            System.currentTimeMillis()
        )
//        builder.setChannelId("trace")
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

    fun initRequest(request: BaseRequest) {
        request.setTag(tag)
        request.setServiceId(serviceId)
    }

    val tag: Int
        get() = mSequenceGenerator.incrementAndGet()

    fun clear() {
        itemInfos.clear()
    }

    private fun getAppChannel() {
        val packageManager = packageManager
        try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 128)
            if (applicationInfo.metaData != null) {
                appChannel = applicationInfo.metaData.getString("channel").toString();
                ILog.d("channel get", appChannel)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            ILog.d("channel get", "NameNotFoundException")
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
        private val TAG = BabyApplication::class.java.name
        @JvmField
        var height = 1000
        @JvmField
        var screenWidth = 0
        @JvmField
        var screenHeight = 0
    }


}