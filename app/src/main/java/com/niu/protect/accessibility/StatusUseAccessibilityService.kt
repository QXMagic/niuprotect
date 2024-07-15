package com.niu.protect.accessibility

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.niu.protect.BuildConfig
import com.niu.protect.Constant
import com.niu.protect.accessibility.auto.app.AppActivityTool
import com.niu.protect.accessibility.auto.service.BaseAccessibility
import com.niu.protect.core.Constants
import com.niu.protect.lib.receiver.BroadcastManager
import com.niu.protect.manager.AutoSettingManager
import com.niu.protect.manager.KeepAliveManger
import com.niu.protect.manager.StudentMainController
import com.niu.protect.manager.SystemBlackAppListManager
import com.niu.protect.manager.SystemWhiteAppListManager
import com.niu.protect.manager.TempOutControlManager
import com.niu.protect.manager.UploadAppManager
import com.niu.protect.manager.UserProtectManager
import com.niu.protect.manager.UserWhiteAppListManager
import com.niu.protect.manager.WebSocketManager
import com.niu.protect.manager.XcxControlManager
import com.niu.protect.model.eventbus.EventScreenOnOrOff
import com.niu.protect.repository.ControllerStatusRepository
import com.niu.protect.tools.ILog
import com.niu.protect.tools.RomUtil
import com.niu.protect.tools.SystemUtil
import com.niu.protect.tools.Tools
import com.niu.protect.ui.login.SplashActivity
import com.niu.protect.ui.main.DesktopActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Calendar
import java.util.Locale

class StatusUseAccessibilityService : BaseAccessibility() {
    var brand: String? = null

    @Volatile
    var event: AccessibilityEvent? = null
    private var remenberTIme: Long = 0
    var roomIsVivo = false
    var roomIsHuawei = false
    var roomIsOppo = false
    private val TAG = "AccessibilityService"
    private val appName = Constant.APP_NAME
    var backClickInfos =
        arrayOf("[Breeno]", "[卸载" + appName + "]", "卸载" + appName, "手机管家", "概览")
    var banWindows = arrayOf("[快捷中心,", "[卸载" + appName + "]", "卸载 " + appName, "概览")
    var blackPackageNames = arrayOf("com.huawei.intelligent")
    var eventEditDesks: ArrayList<Any?> = ArrayList()
    var isStartControl = false
    var changeTime = System.currentTimeMillis()
    var packageNameLastTime = ""
    var isConnect = false
    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            ILog.d("mHandler--", "mHandler  packageNameLastTime:$isStartControl")
            if (isStartControl) {
                ILog.d("mHandler--", "mHandler" + rootInActiveWindow.packageName as Any)
                val packName =
                    if (rootInActiveWindow.packageName != null) rootInActiveWindow.packageName.toString() else null
                checkBalckAppAndUseTime(packName)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
        BroadcastManager.registerShutDownReciver(this)
        roomIsVivo = RomUtil.isVivo || RomUtil.isFlyme
        roomIsHuawei = RomUtil.isHuawei
        roomIsOppo = RomUtil.isOppo
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventScreenOnOrOff?) {
        var info: AccessibilityNodeInfo
        ILog.d("onMessageEvent--", "onMessageEvent  isStartControl: + $isStartControl")
        rootInActiveWindow.also { info=it }
        if (isStartControl && rootInActiveWindow!=null) {
            info = rootInActiveWindow
            if(info.packageName != null) {
                val packageName = info.packageName.toString()
                checkBalckAppAndUseTime(packageName)
            }
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        ILog.d(TAG, "eventType:$event.eventType  --roomIsVivo--- $roomIsVivo")
        ILog.d(TAG, "eventType text:$event.text")
        ILog.d(TAG, "eventType getPackageName:" + event.packageName as Any)
        ILog.d(TAG, "eventType classname:" + event.className as Any?)
        isStartControl =
            UserProtectManager.getInstance().protectStatus != UserProtectManager.STATUS_OUT_OFF_PROTECT && AutoSettingManager.isSettingFinish
        if (isStartControl) {
            if (TempOutControlManager.getInstance().getTempOutTime(this)) {
                return
            }
            luncherEvent(event)
            this.event = event
            return
        }
        openAccessibilityEvent(event)
    }

    private fun luncherEvent(event: AccessibilityEvent) {
        val eventType = event.eventType
        var className = ""
        if (event.packageName == null) {
            return
        }
        if (event.className != null) {
            className = event.className.toString()
        }
        val packagetName = event.packageName.toString()
        if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED
            || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            || eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
//            if (eventType == 1 || eventType == 32 || eventType == 4096) {
            WebSocketManager.instance.reconnect()
            if (packagetName == "com.coloros.oppoguardelf" || packagetName.contains("com.huawei.systemmanager.power")) {
                goBack()
                return
            }
            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && !TextUtils.isEmpty(packagetName)) {
                if (roomIsVivo && packagetName == "com.vivo.upslide" && event.text.size == 0) {
                    return
                }
                ILog.d(TAG, "packageName:$packagetName")
                val webSocketManager = WebSocketManager.instance
                webSocketManager.sendEventMessage(eventType.toString()  , packagetName)
            }
            if (roomIsVivo) {
                if (packagetName == "com.android.launcher" && eventType == 4096 && event.text.size == 0 && className == "androidx.recyclerview.widget.RecyclerView") {
                    goBack()
                }
                if (packagetName == "com.heytap.quicksearchbox") {
                    goBack()
                }
            }
            if (packagetName == Constants.ANDROID || packagetName.contains("input") || packagetName.contains(
                    "input_huawei"
                ) || packagetName.contains("UniHomeLauncher") || packagetName.contains("password") || packagetName.contains(
                    "keyboard"
                ) || packagetName.contains("sogouoem") || packagetName == BuildConfig.APPLICATION_ID || packagetName == "com.android.launcher" || packagetName == "com.android.mms" || packagetName == "com.android.systemUi"
            ) {
                return
            }
            if (packagetName == "com.hihonor.systemmanager" && event.text.toString()
                    .contains("应用锁")
            ) {
                return
            }
            if (packagetName == "com.coloros.assistantscreen" || packagetName == "com.vivo.hiboard" || packagetName == "com.huawei.intelligent") {
                ILog.d(this.TAG, "负一屏幕返回 goBack()---")
                goBack()
                return
            }
        }
        if (eventType == AccessibilityEvent.TYPE_VIEW_LONG_CLICKED && packagetName.contains("launcher") && event.text.toString().contains(
                appName
            )
        ) {
            ILog.d(this.TAG, "长按防止卸载返回 goBack()---")
            goBack()
        } else if (packagetName == "com.ss.android.ugc.aweme" && eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            checkBalckAppAndUseTime(packagetName)
        } else {
            if (roomIsVivo) {
                if (packagetName.contains("launcher") && eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                    if (findById(
                            "com.oppo.launcher:id/btn_remove",
                            0
                        ) || findById(
                            "com.bbk.launcher2:id/uninstall_drop_target",
                            0
                        ) || findById(
                            "com.android.launcher:id/title",
                            0
                        ) || findClickById("com.android.launcher:id/btn_remove", 0)
                    ) {
                        if (event.text.toString() != "快捷方式") {
                            ILog.d("", "---防止卸载-")
                            goBack()
                            return
                        }
                        return
                    } else if (!TextUtils.isEmpty(event.text.toString())) {
                        val text = event.text.toString()
                        if (text.contains("最近用过的应用")) {
                            ILog.d(this.TAG, "最近用过的应用--")
                            goBack()
                            return
                        } else if (text.contains("卸载") && className == "android.app.AlertDialog") {
                            ILog.d(this.TAG, "卸载--")
                            goBack()
                            return
                        }
                    }
                }
                if (!roomIsOppo && (findById(
                        "com.bbk.launcher2:id/tv_drag_rearrange",
                        0
                    ) || findById("com.android.launcher:id/btn_remove", 0))
                ) {
                    ILog.d(this.TAG, "防卸载--")
                    goBack()
                    return
                }
            }
            if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
                if (roomIsVivo && event.text.toString().contains("交互池")) {
                    ILog.d(this.TAG, "交互池--")
                    return
                }
                if (packagetName.contains("com.android") || packagetName.contains("com.bbk") || packagetName.contains(
                        "com.huawei"
                    )
                ) {
                    var i = 0
                    while (true) {
                        if (i >= backClickInfos.size) {
                            break
                        } else if (!event.text.toString().contains(backClickInfos[i])) {
                            i++
                        } else {
                            ILog.d(this.TAG, "返回关键字--")
                            goBack()
                            break
                        }
                    }
                }
                if (packagetName.contains("com.oppo.launcher") && event.text.contains("清除")) {
                    val intent = Intent(this, SplashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    return
                }
            }
            if (packagetName == "com.android.settings" && eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                val eventTexts = event.text
                val eventText = eventTexts.toString()
                val str = this.TAG
                ILog.d(
                    str,
                    "Setting info$eventTexts---packagetName---$packagetName--className-$className"
                )
                if (eventText.contains("移动") || eventText.contains("蓝牙") || eventText.contains("WLAN") || eventText.contains(
                        "通话"
                    ) || eventText.contains("网络详情") || eventText.contains("输入密码") || eventText.contains(
                        "加密"
                    ) || eventText.contains("•")
                ) {
                    return
                }
                if (!TextUtils.isEmpty(className) && className.contains("Wifi")) {
                    return
                }
                if (roomIsHuawei) {
                    if (className == "com.android.settings.HWSettings") {
                        gotoDefindSetting()
                        return
                    }
                    return
                }
                gotoDefindSetting()
                return
            }
            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                val txt = event.text.toString()
                for (ban:String in banWindows){
                    if(txt.contains(ban)){
                        goBack();
                    }
                }
                if (packagetName == "com.oplus.battery") {
                    goBack()
                    return
                }
            }
            if (eventType == 1 || eventType == 32 || eventType == 4096 || eventType == 2048) {
                if (eventType == 2048) {
                    val nowTime = System.currentTimeMillis()
                    if (nowTime - remenberTIme < 4000) {
                        remenberTIme = System.currentTimeMillis()
                        return
                    }
                    remenberTIme = System.currentTimeMillis()
                }
                if (packageNameLastTime != packagetName) {
                    UploadAppManager.getInstance(this)?.upNewInstallAPP(packagetName)
                }
                checkBalckAppAndUseTime(packagetName)
                changeTime = System.currentTimeMillis()
                packageNameLastTime = packagetName
                if (packagetName != "com.tencent.mm" || event.className == null) {
                    return
                }
                val isLimit = XcxControlManager.getInstance()
                    .checkXcxInControl(this, event.className.toString())
                if (isLimit) {
                    goBack()
                }
            }
        }
    }

    @Synchronized
    fun checkBalckAppAndUseTime(packageName: String?) {
        if (packageName != null) {
            if (packageName != BuildConfig.APPLICATION_ID && packageName != "com.oplus.wirelesssettings" && packageName != "com.android.launcher.Launcher") {
                if (UserWhiteAppListManager.getInstance().isWhiteApp(this, packageName)) {
                    return
                }
                if (SystemWhiteAppListManager.getInstance().systemWhiteApp(this, packageName)) {
                    return
                }
                if (SystemBlackAppListManager.getInstance().systemWhiteApp(this, packageName)) {
                    goBack()
                    return
                } else if ((packageName.contains("com.oppo") || packageName.contains("com.android") || packageName.contains(
                        "coloros"
                    ) || packageName.contains("com.bbk") || packageName == BuildConfig.APPLICATION_ID || packageName == "com.baidu.input_vivo" || packageName.contains(
                        "contacts"
                    ) || packageName.contains("deskclock") || packageName.contains("camera") || packageName.contains(
                        "com.vivo"
                    ) || packageName.contains("com.huawei") || packageName.contains("launcher") || packageName.contains(
                        "com.hihonor"
                    ) || packageName.contains("com.jiankong.jia")) && !packageName.lowercase(
                        Locale.getDefault()
                    ).contains("browser") && !packageName.lowercase(Locale.getDefault())
                        .contains("video") && !packageName.lowercase(
                        Locale.getDefault()
                    ).contains("music") && !packageName.lowercase(Locale.getDefault())
                        .contains("store") && !packageName.lowercase(
                        Locale.getDefault()
                    ).contains("chrome") && !packageName.lowercase(Locale.getDefault())
                        .contains("market") && !packageName.lowercase(
                        Locale.getDefault()
                    ).contains("community") && !packageName.lowercase(Locale.getDefault())
                        .contains("phonemanager") && !packageName.lowercase(
                        Locale.getDefault()
                    ).contains("minigamecenter") && !packageName.lowercase(
                        Locale.getDefault()
                    ).contains("hwvplayer")
                ) {
                    return
                } else {
                    checkCanUse(packageName)
                }
            }
            return
        }
        ILog.d(this.TAG, "method   checkBalckAppAndUseTime package is null ")
    }

    private fun checkCanUse(packageName: String) {
        val isCantNotUse = StudentMainController.getInstance().checkNotUseTime(this)
        if (isCantNotUse) {
            val str = this.TAG
            ILog.d(str, "can't not use:$packageName")
            if (packageName.lowercase(Locale.getDefault())
                    .contains("browser") || packageName.lowercase(
                    Locale.getDefault()
                ).contains("video") || packageName.lowercase(Locale.getDefault())
                    .contains("store") || packageName.lowercase(
                    Locale.getDefault()
                ).contains("music") || packageName.lowercase(Locale.getDefault())
                    .contains("market") || packageName.lowercase(
                    Locale.getDefault()
                ).contains("community") || packageName.lowercase(Locale.getDefault())
                    .contains("chrome") || packageName.uppercase(
                    Locale.getDefault()
                ).contains("hwvplayer")
            ) {
                goBack()
            }
            if (!packageName.contains("com.oppo") && !packageName.contains("com.android") && !packageName.contains(
                    "coloros"
                ) && !packageName.contains("com.bbk") && !packageName.contains("launcher")
            ) {
                goBack()
                return
            }
            return
        }
        val isCannotUseApp = StudentMainController.getInstance().appCanUse(packageName)
        if (isCannotUseApp) {
            ILog.d(TAG, "can't use---$packageName")
            goBack()
            return
        }
        ILog.d(TAG, "can use---$packageName")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        ILog.d(TAG, "---onServiceConnected--------")
        mService = this
        brand = SystemUtil.getDeviceBrand()
        ILog.d(TAG, Tools.getAutoSet(this).toString() + "getAutoSet")
        try {
            Thread.sleep(500L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        if (Tools.getAutoSet(this) == 1) {
            Tools.saveAutoSet(this, 0)
            AppActivityTool.backApp(this)
        }
        ControllerStatusRepository.getInstance().requestControlStatus(this, 1)
        BroadcastManager.sendAccessibilityStart(this)
        isConnect = true
        KeepAliveManger.instance?.keepAliveByTowService(this)
    }

    override fun onInterrupt() {
        mService = null
        isStartControl = false
        isConnect = false
        ControllerStatusRepository.getInstance().requestControlStatus(this, 0)
        ILog.d(TAG, "-------onInterrupt---------")
    }

    private fun gotoDefindSetting() {
        ILog.d(TAG, "check rom " + RomUtil.isVivo)
        if (RomUtil.isVivo) {
            val version = Build.VERSION.SDK_INT
            if (version >= 29) {
                AppActivityTool.showNewSetActivy(this)
            } else {
                AppActivityTool.showSetActivy(this)
            }
        } else if (RomUtil.isOppo) {
            val version2 = Build.VERSION.SDK_INT
            if (version2 >= 30) {
                AppActivityTool.showNewSetActivy(this)
            } else {
                AppActivityTool.showSetActivy(this)
            }
        } else if (RomUtil.isHuawei) {
            val version3 = Build.VERSION.SDK_INT
            if (version3 >= 30) {
                AppActivityTool.showNewSetActivy(this)
            } else {
                AppActivityTool.showSetActivy(this)
            }
        }
    }

    private fun goBack() {
        ILog.d(TAG, "goBack")
        performGlobalAction(
            GLOBAL_ACTION_BACK)
        try {
            Thread.sleep(50L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        performGlobalAction(GLOBAL_ACTION_BACK)
        try {
            Thread.sleep(100L)
        } catch (e2: InterruptedException) {
            e2.printStackTrace()
        }
        performGlobalAction(GLOBAL_ACTION_BACK)
        try {
            Thread.sleep(200L)
        } catch (e3: InterruptedException) {
            e3.printStackTrace()
        }
        performGlobalAction(GLOBAL_ACTION_HOME)
    }

    fun goBackHome() {
        Log.e(TAG, "111112")
        performGlobalAction(GLOBAL_ACTION_HOME)
    }

    fun islock(): Boolean {
        val lct = Tools.getLocTask(this)
        if (lct == 1) {
            performGlobalAction(GLOBAL_ACTION_RECENTS)
            Tools.saveLocTask(this, 2)
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        mService = null
        ILog.d(TAG, "----------------")
        EventBus.getDefault().unregister(this)
        BroadcastManager.unRegisterShutDownReciver(this)
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onMessageEvent(event:Message?) {
        Log.d(TAG, "event -----")
        clickMenu()
    }

    fun clickMenu() {
        try {
            Thread.sleep(200L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        performGlobalAction(GLOBAL_ACTION_RECENTS)
    }

    private fun startActivity() {
        val intent = Intent(this, DesktopActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val now = Calendar.getInstance().timeInMillis
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, now, pendingIntent)
    }

    companion object {
        var mService: StatusUseAccessibilityService? = null
        var step = 0
    }
}