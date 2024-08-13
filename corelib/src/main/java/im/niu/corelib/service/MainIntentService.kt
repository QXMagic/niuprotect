package im.niu.corelib.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.BatteryManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.core.app.NotificationCompat
import im.niu.corelib.App
import im.niu.corelib.Constants
import im.niu.corelib.R
import im.niu.corelib.events.EventType
import im.niu.corelib.events.MessageEvent
import im.niu.corelib.utils.ILog
import im.niu.data.Userinfo
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


const val CHANNEL_ID = "main_service_chanel_id"
const val CHANNEL_NAME = "Main_Chanel"


class MainIntentService : Service() {

    inner class MyHandler(looper: Looper) : Handler(looper){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                1 -> {
                    val reply = Message.obtain(null, 2)
                    reply.obj = "hello"
                    try {
                        msg.replyTo.send(reply)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private val tag: String = "MainService"
    private val notifyId: Int = 101
    private lateinit var handler:MyHandler
    private lateinit var mMessenger: Messenger

    override fun onBind(intent: Intent?): IBinder? {
        ILog.d(tag,"MainIntentService onBind")
        return mMessenger.binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ILog.d(tag,"MainIntentService onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        ILog.e(tag,"main service destroy")
        try {
            handler.removeCallbacksAndMessages(null)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onCreate() {
        super.onCreate()
        ILog.d(tag,"MainIntentService onCreate")
        EventBus.getDefault().register(this)
        handler = MyHandler(baseContext.mainLooper)
        mMessenger = Messenger(handler)
        try {
            handler.postDelayed({
                val reply = Message.obtain(null, 2)
                reply.obj = "hello"
                try {
                    mMessenger.send(reply)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }, 1000)
        }catch (e: Exception){
            e.printStackTrace()
        }

        registerNotificationChannel()
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
        mBuilder.setSmallIcon(R.mipmap.ico)
        mBuilder.setContentTitle(Constants.APP_NAME)
        mBuilder.setContentText("正在守护")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(this.notifyId, mBuilder.build(), ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION)
        }else{
            startForeground(this.notifyId, mBuilder.build())
        }
        keepNetworkAlive();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: MessageEvent) {
        if(event.type == EventType.TIME_LIMIT){

        }
    }

    private fun registerNotificationChannel() {
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = mNotificationManager.getNotificationChannel(CHANNEL_ID)
        if (notificationChannel == null) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableLights(false)
            channel.lightColor = 0
            channel.lockscreenVisibility = 1
            mNotificationManager.createNotificationChannel(channel)
        }
    }

    private fun keepNetworkAlive() {
        Thread {
            while (true) {
                App.appManager.pushAppList(applicationContext)
                sendScreenMsg()
                getAppStates()
                try {
                    Thread.sleep(1000 * 30 )
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    private fun network(){
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        val networkRequest = builder.build()

        connectivityManager.registerNetworkCallback(networkRequest, object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                // 网络变得可用
                Log.d("NetworkCallback", "Network available")
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                // 网络变得不可用
                Log.d("NetworkCallback", "Network lost")
            } // 可以添加更多回调方法以响应不同类型的网络变化

            override fun onUnavailable() {
                super.onUnavailable()
            }
        })
    }

    open fun sendScreenMsg() {
        val manager = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager
        val battery = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

        var ping = Userinfo.Ping.newBuilder().setBattery(battery).setScreen(1).build()
        App.webSocketManager.sendMessage(ping)

    }

    fun getAppStates(){
//        val usm = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
//        val time = System.currentTimeMillis()
//        val appList =
//            usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, NiuUtil.timeToDayStart(time), time)
//        var applist = Userinfo.AppUseInfoList.newBuilder()
//        if (appList != null && appList.size > 0) {
//            for (usageStats in appList) {
//                // 使用usageStats.getPackageName()获取包名
//                // 使用usageStats.getTotalTimeInForeground()获取前台总时间
//               if(usageStats.totalTimeInForeground== 0L){
//                   continue
//               }
//                Log.d(
//                    "AppUsage",
//                    "Package: " + usageStats.packageName + "\tForeground Time: " + usageStats.totalTimeInForeground
//                )
//                var appUserINfo = Userinfo.AppUseInfo.newBuilder()
//                    .setFirstTimeStamp(usageStats.firstTimeStamp)
//                    .setLastTimeStamp(usageStats.lastTimeStamp)
//                    .setPackageName(usageStats.packageName)
//                    .setTotalTimeInForeground(usageStats.totalTimeInForeground)
//                    .setLastTimeUsed(usageStats.lastTimeUsed)
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    appUserINfo.totalTimeVisible = usageStats.totalTimeVisible
//                }
//                applist.addAppUseInfo(appUserINfo.build())
//            }
//            App.webSocketManager.sendMessage(applist.build())
//        }
        App.appManager.pushUsageEvent(applicationContext)
    }

}