package im.niu.corelib.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.text.TextUtils
import android.view.accessibility.AccessibilityEvent
import im.niu.corelib.Constants
import im.niu.corelib.events.MessageEvent
import im.niu.corelib.manager.BroadcastManager
import im.niu.corelib.receiver.MainReceiver
import im.niu.corelib.utils.ILog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Locale

class NiuAccessibilityService : AccessibilityService() {

    private val TAG = "NiuAccess"
    private var backClickInfos =
        arrayOf("[Breeno]", "[卸载" + Constants.APP_NAME + "]", "卸载" + Constants.APP_NAME, "手机管家", "概览")
        private var banWindows = arrayOf("[快捷中心,", "[卸载" + Constants.APP_NAME + "]", "卸载 " + Constants.APP_NAME, "概览")
    /**
     * Callback for [android.view.accessibility.AccessibilityEvent]s.
     *
     * @param event The new event. This event is owned by the caller and cannot be used after
     * this method returns. Services wishing to use the event after this method returns should
     * make a copy.
     */
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        ILog.i(TAG, "--eventType---:$event.eventType")
        ILog.d(TAG, "--eventType text--:$event.text")
        ILog.d(TAG, "--eventType getPackageName--:" + event.packageName)
        ILog.d(TAG, "--eventType classname--:" + event.className)


        luncherEvent(event)
    }

    /**
     * Callback for interrupting the accessibility feedback.
     */
    override fun onInterrupt() {
        ILog.d(TAG,"onInterrupt")
    }

    override fun onCreate() {
        super.onCreate()
        ILog.d(TAG,"onCreate")
        EventBus.getDefault().register(this)
        BroadcastManager.register(this)


    }

    override fun onDestroy() {
        super.onDestroy()
        ILog.d(TAG,"onDestroy")
        EventBus.getDefault().unregister(this)
        BroadcastManager.unRegister(this)
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        ILog.d(TAG,"onServiceConnected")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ILog.d(TAG,"onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onMessageEvent(message: MessageEvent){

    }

    private fun luncherEvent(event: AccessibilityEvent) {
        val eventType = event.eventType
        var className = ""

        if(TextUtils.isEmpty(event.packageName)){
            ILog.e(TAG,"event type is Empty type:$eventType")
        }
        if (event.className != null) {
            className = event.className.toString()
        }
        val packagetName = event.packageName.toString()

        if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED
            || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            || eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {

//            if (eventType == 1 || eventType == 32 || eventType == 4096) {
//            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && !TextUtils.isEmpty(packagetName)) {
//                if (roomIsVivo && packagetName == "com.vivo.upslide" && event.text.size == 0) {
//                    return
//                }
//                ILog.d(TAG, "packageName:$packagetName")
//                val webSocketManager = WebSocketManager.instance
//                webSocketManager.sendEventMessage(eventType.toString()  , packagetName)
//            }
//            if (roomIsVivo) {
//                if (packagetName == "com.android.launcher" && eventType == 4096 && event.text.size == 0 && className == "androidx.recyclerview.widget.RecyclerView") {
//                    goBack()
//                }
//                if (packagetName == "com.heytap.quicksearchbox") {
//                    goBack()
//                }
//            }
            if (packagetName == Constants.ANDROID || packagetName.contains("input") || packagetName.contains("UniHomeLauncher") || packagetName.contains("password") || packagetName.contains(
                    "keyboard"
                ) || packagetName.contains("sogouoem") || packagetName == Constants.APPLICATION_ID || packagetName == "com.android.launcher" || packagetName == "com.android.mms" || packagetName == "com.android.systemUi"
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
                Constants.APP_NAME
            )
        ) {
            ILog.d(this.TAG, "长按防止卸载返回 goBack()---")
            goBack()
        } else if (packagetName == "com.ss.android.ugc.aweme" && eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            //刷抖音?
            checkBalckAppAndUseTime(packagetName)
        } else {
//            if (roomIsVivo) {
//                if (packagetName.contains("launcher") && eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
//                    if (findById(
//                            "com.oppo.launcher:id/btn_remove",
//                            0
//                        ) || findById(
//                            "com.bbk.launcher2:id/uninstall_drop_target",
//                            0
//                        ) || findById(
//                            "com.android.launcher:id/title",
//                            0
//                        ) || findClickById("com.android.launcher:id/btn_remove", 0)
//                    ) {
//                        if (event.text.toString() != "快捷方式") {
//                            ILog.d("", "---防止卸载-")
//                            goBack()
//                            return
//                        }
//                        return
//                    } else if (!TextUtils.isEmpty(event.text.toString())) {
//                        val text = event.text.toString()
//                        if (text.contains("最近用过的应用")) {
//                            ILog.d(this.TAG, "最近用过的应用--")
//                            goBack()
//                            return
//                        } else if (text.contains("卸载") && className == "android.app.AlertDialog") {
//                            ILog.d(this.TAG, "卸载--")
//                            goBack()
//                            return
//                        }
//                    }
//                }
//                if (!roomIsOppo && (findById(
//                        "com.bbk.launcher2:id/tv_drag_rearrange",
//                        0
//                    ) || findById("com.android.launcher:id/btn_remove", 0))
//                ) {
//                    ILog.d(this.TAG, "防卸载--")
//                    goBack()
//                    return
//                }
//            }
            if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
//                if (roomIsVivo && event.text.toString().contains("交互池")) {
//                    ILog.d(this.TAG, "交互池--")
//                    return
//                }
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
//                if (packagetName.contains("com.oppo.launcher") && event.text.contains("清除")) {
//                    val intent = Intent(this, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                    return
//                }
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
//                if (roomIsHuawei) {
//                    if (className == "com.android.settings.HWSettings") {
//                        gotoDefindSetting()
//                        return
//                    }
//                    return
//                }
//                gotoDefindSetting()
                return
            }
            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                val txt = event.text.toString()
                for (ban:String in banWindows){
                    if(txt.contains(ban)){
                        goBack();
                        return
                    }
                }
                if (packagetName == "com.oplus.battery") {
                    goBack()
                    return
                }
            }
//            if (eventType == 1 || eventType == 32 || eventType == 4096 || eventType == 2048) {
//                if (eventType == 2048) {
//                    val nowTime = System.currentTimeMillis()
//                    if (nowTime - remenberTIme < 4000) {
//                        remenberTIme = System.currentTimeMillis()
//                        return
//                    }
//                    remenberTIme = System.currentTimeMillis()
//                }
//                if (packageNameLastTime != packagetName) {
//                    UploadAppManager.getInstance(this)?.upNewInstallAPP(packagetName)
//                }
//                checkBalckAppAndUseTime(packagetName)
//                changeTime = System.currentTimeMillis()
//                packageNameLastTime = packagetName
//                if (packagetName != "com.tencent.mm" || event.className == null) {
//                    return
//                }
//                val isLimit = XcxControlManager.getInstance()
//                    .checkXcxInControl(this, event.className.toString())
//                if (isLimit) {
//                    goBack()
//                }
//            }
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


    @Synchronized
    fun checkBalckAppAndUseTime(packageName: String?) {
        if (packageName != null) {
            if (packageName != Constants.APPLICATION_ID && packageName != "com.oplus.wirelesssettings" && packageName != "com.android.launcher.Launcher") {
//                if (UserWhiteAppListManager.instance?.isWhiteApp(this, packageName) == true) {
//                    return
//                }
//                if (SystemWhiteAppListManager.getInstance().systemWhiteApp(this, packageName)) {
//                    return
//                }
//                if (SystemBlackAppListManager.getInstance().systemWhiteApp(this, packageName)) {
//                    goBack()
//                    return
//                } else
                    if ((packageName.contains("com.oppo") || packageName.contains("com.android") || packageName.contains(
                        "coloros"
                    ) || packageName.contains("com.bbk") || packageName == Constants.APPLICATION_ID || packageName == "com.baidu.input_vivo" || packageName.contains(
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
        val isCantNotUse = true//StudentMainController.getInstance().checkNotUseTime(this)
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
        val isCannotUseApp = false//StudentMainController.getInstance().appCanUse(packageName)
        if (isCannotUseApp) {
            ILog.d(TAG, "can't use---$packageName")
            goBack()
            return
        }
        ILog.d(TAG, "can use---$packageName")
    }
}