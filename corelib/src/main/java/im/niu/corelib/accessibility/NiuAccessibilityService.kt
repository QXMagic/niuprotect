package im.niu.corelib.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.text.TextUtils
import android.view.accessibility.AccessibilityEvent
import im.niu.corelib.App
import im.niu.corelib.Constants
import im.niu.corelib.events.MessageEvent
import im.niu.corelib.manager.BroadcastManager
import im.niu.corelib.ui.BootActivity
import im.niu.corelib.utils.ILog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class NiuAccessibilityService : AccessibilityService() {

    private val TAG = "NiuAccess"
    private var backClickInfos =
        arrayOf("[Breeno]", "[卸载" + Constants.APP_NAME + "]", "卸载" + Constants.APP_NAME, "手机管家", "概览")
    /**
     * Callback for [android.view.accessibility.AccessibilityEvent]s.
     *
     * @param event The new event. This event is owned by the caller and cannot be used after
     * this method returns. Services wishing to use the event after this method returns should
     * make a copy.
     */
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        ILog.i(TAG, "--eventType---:$event.eventType")
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
            return
        }
        if (event.className != null) {
            className = event.className.toString()
        }
        val packagetName = event.packageName.toString().lowercase()
        val text = event.text.toString()
        if (eventType == AccessibilityEvent.TYPE_VIEW_LONG_CLICKED
                && packagetName.contains("launcher")
                && text.contains(Constants.APP_NAME)
        ) {
            goBack("防止监管卸载返回")
        }

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

//            if (packagetName == "com.hihonor.systemmanager" && event.text.toString()
//                    .contains("应用锁")
//            ) {
//                return
//            }
            if (packagetName == "com.coloros.assistantscreen" || packagetName == "com.vivo.hiboard" || packagetName == "com.huawei.intelligent") {
//                ILog.d(this.TAG, "负一屏幕返回 goBack()---")
                goBack("负一屏幕返回")
                return
            }
        }
//        if (packagetName == "com.ss.android.ugc.aweme" && eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
//            //刷抖音?
//            checkCanUse(packagetName)
//        } else {
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
                if (packagetName.contains("com.android")
                    || packagetName.contains("com.bbk")
                    || packagetName.contains("com.huawei")
                ) {

                    for(bk in backClickInfos){
                        if(text.contains(bk)){
                            goBack("点击 $packagetName 返回,危险关键字--> $bk")
                            break
                        }
                    }
                }
                if (packagetName.contains("com.oppo.launcher") && event.text.contains("清除")) {
                    val intent = Intent(this, BootActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    return
                }
            }
            //设置页面
            if (packagetName == "com.android.settings" && eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                ILog.d(
                    this.TAG,
                    "Setting info className-$className, text: $text"
                )
                if (text.contains("移动")
                    || text.contains("蓝牙")
                    || text.contains("WLAN")
                    || text.contains("通话")
                    || text.contains("网络详情")
                    || text.contains("输入密码")
                    || text.contains("加密")
                    || text.contains("•")
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

            if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED
                || eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED
                || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                || eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                //不点击,光屏幕变化检测,4秒之后不检测
                if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                    val nowTime = System.currentTimeMillis()
                    if (nowTime - remenberTIme < 4000) {
//                        remenberTIme = System.currentTimeMillis()
                        return
                    }
                    remenberTIme = System.currentTimeMillis()
                }
                if (packageNameLastTime != packagetName) {
                    ILog.d(TAG, "change app ,packageNameLastTime: $packageNameLastTime -> $packagetName")
                }
                checkBalckAppAndUseTime(packagetName,event)
//                changeTime = System.currentTimeMillis()
                packageNameLastTime = packagetName
//                if (packagetName != "com.tencent.mm" || event.className == null) {
//                    return
//                }
//                val isLimit = XcxControlManager.getInstance()
//                    .checkXcxInControl(this, event.className.toString())
//                if (isLimit) {
//                    goBack()
//                }
        }
    }
    var packageNameLastTime = ""
    var remenberTIme = 0L

    private fun goBack(msg:String) {
        ILog.d(TAG, "goBack, $msg")
        performGlobalAction(GLOBAL_ACTION_BACK)
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
    fun checkBalckAppAndUseTime(packageName: String,event: AccessibilityEvent) {
        if(App.appLimit.isForbid(packageName,event)){
            goBack("禁用 app $packageName")
            return
        }
        if (!App.appLimit.isAllow(packageName,event)) {
            return
        }
        //系统APP
        if ((packageName.startsWith("com.oppo")
                    || packageName.startsWith("com.android")
                    || packageName.contains("coloros.")
                    || packageName.contains("com.bbk")
                    || packageName.startsWith("com.baidu.input")
                    || packageName.contains("contacts")
                    || packageName.contains("deskclock")
                    || packageName.contains("camera")
                    || packageName.startsWith("com.vivo")
                    || packageName.startsWith("com.huawei")
                    || packageName.contains("launcher")
                    || packageName.startsWith("com.hihonor")
                )
            && !packageName.contains("browser")
            && !packageName.contains("video")
            && !packageName.contains("music")
            && !packageName.contains("store")
            && !packageName.contains("chrome")
            && !packageName.contains("market")
            && !packageName.contains("community")
            && !packageName.contains("phonemanager")
            && !packageName.contains("minigamecenter")
            && !packageName.contains("hwvplayer")
        ) {
            return
        } else {
            val isCantNotUse = true//StudentMainController.getInstance().checkNotUseTime(this)
            if (isCantNotUse) {
                //不在使用时间内, 系统APP 排除娱乐APP可以使用,其他APP不能用
                if (packageName.contains("browser") ||
                    packageName.contains("video") ||
                    packageName.contains("store") ||
                    packageName.contains("music") ||
                    packageName.contains("market") ||
                    packageName.contains("community") ||
                    packageName.contains("chrome") ||
                    packageName.contains("hwvplayer")
                ) {
                    goBack("$packageName:浏览器, 视频, 音乐, 浏览器等")
                }
                if (!packageName.contains("com.oppo") &&
                    !packageName.contains("com.android") &&
                    !packageName.contains("coloros") &&
                    !packageName.contains("com.bbk") &&
                    !packageName.contains("launcher")
                ) {
                    goBack("$packageName 不是系统APP")
                    return
                }
            }
        }
    }
}