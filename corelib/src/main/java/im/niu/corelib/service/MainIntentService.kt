package im.niu.corelib.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import androidx.core.app.NotificationCompat
import im.niu.corelib.App
import im.niu.corelib.R
import im.niu.corelib.utils.ILog

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

    private val tag: String = "MainIntentService"
    private val notifyId: Int = 101
    private lateinit var handler:MyHandler
    private lateinit var mMessenger: Messenger

    override fun onBind(intent: Intent?): IBinder? {
        ILog.d(tag,"MainIntentService onBind")
        return mMessenger.binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            handler.removeCallbacksAndMessages(null)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onCreate() {
        super.onCreate()
        ILog.d(tag,"MainIntentService onCreate")
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
        mBuilder.setContentTitle(App.APP_NAME)
        mBuilder.setContentText("正在守护")
        startForeground(this.notifyId, mBuilder.build())

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

}