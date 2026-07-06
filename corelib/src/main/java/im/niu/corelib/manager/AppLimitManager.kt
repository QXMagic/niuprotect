package im.niu.corelib.manager

import android.content.Context
import android.view.accessibility.AccessibilityEvent
import im.niu.corelib.Constants
import im.niu.corelib.data.AppSetting
import im.niu.corelib.data.TimeSetting
import im.niu.corelib.events.EventType
import im.niu.corelib.events.MessageEvent
import im.niu.corelib.os.OperateSystem
import im.niu.corelib.utils.ILog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.litepal.LitePal

class AppLimitManager(private val selfPackage: String? = null) {
    /**
     * 允许时间段列表
     * */
    private lateinit var allowList:MutableList<TimeSetting>
    /**
     * 禁止时间段列表
     * */
//    private lateinit var denyList:MutableList<TimeSetting>
    private lateinit var rangeList:MutableList<TimeSetting>

    private var whiteList: MutableList<AppSetting>
    private var blackList: MutableList<AppSetting>
    private var timeList: MutableList<AppSetting>
    private var systemBlackMap: HashMap<String,String>
    private var systemWhiteMap: HashMap<String,String>
    private var systemWhiteText:MutableList<String>
    private var systemBlackText:MutableList<String>

    private var osSetting: OperateSystem;

    init {
        EventBus.getDefault().register(this)
        allowList = ArrayList()
        rangeList = ArrayList()
        whiteList = ArrayList()
        blackList = ArrayList()
        timeList = ArrayList()
        systemBlackMap = HashMap()
        systemWhiteMap = HashMap()
        systemWhiteText = ArrayList()
        systemBlackText = ArrayList()

        //系统白名单APP列表
        systemWhiteMap["android"] = "安卓"
        systemWhiteMap["com.android.systemui"] = "主界面"
        systemWhiteMap["com.android.mms"] = "短信"
        systemWhiteMap["com.android.launcher"] = "启动器1"
        systemWhiteMap["com.android.launcher.launcher"] = "发射器2"
        systemWhiteMap["com.oplus.wirelesssettings "] = "无限网络设置"



        systemWhiteText.add("unihomelauncher")
        systemWhiteText.add("password")
        systemWhiteText.add("keyboard")
        systemWhiteText.add("sogouoem")

        systemBlackMap["com.coloros.oppoguardelf"] = "未知"
        systemBlackMap["com.huawei.systemmanager.power"] = "未知"
        osSetting = OperateSystem.getOsSetting()
        // 启动即从 LitePal 加载已持久化的管控设置（否则版本已最新、服务端不再下发时名单为空）
        refreshData()
    }

    /**
     * 判断是否允许
     * @param packageName
     * @param event
     *
     * @return true:允许,直接跳过后续检测，false:不允许,继续判断后续检测
     *
     */
    fun isAllow(packageName: String,event:AccessibilityEvent): Boolean {
        if(systemWhiteMap.containsKey(packageName)){
            return true
        }
        if (whiteList.size > 0) {
            for (app in whiteList) {
                if (app.packageName == packageName) {
                    return true
                }
            }
        }

        if (timeList.size > 0) {
            val now = System.currentTimeMillis()%86400000
            for (app in timeList) {
                if (app.packageName == packageName) {
                    val setting = app.timeSetting
                    if (setting != null) {
                        if(setting.startTime<=now&&setting.endTime>now){
                            return true
                        }
                    }
                    return false
                }
            }
        }
//        for (app in rangeList){
//            if(app.packageName==packageName){
//                return true
//            }
//        }
        return osSetting.isAllow(packageName,event)
    }
    private var banWindows = arrayOf("[快捷中心,", "[卸载" + Constants.APP_NAME + "]", "卸载 " + Constants.APP_NAME, "概览")

    /**
     * 判断是否禁止
     * @param packageName
     *
     * @return true:禁止,直接退出,false:允许,继续判断后续检测
     *
     */
    fun isForbid(packageName: String,event:AccessibilityEvent): Boolean {
        if (blackList.size > 0) {
            for (app in blackList) {
                if (app.packageName == packageName) {
                    ILog.i(TAG, "forbidden in black list: $packageName")
                    return true
                }
            }
        }
        if(event.eventType==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            val txt = event.text.toString()
            for (ban:String in banWindows){
                if(txt.contains(ban)){
                    ILog.i(TAG, "contains ban word: $ban")
                    return true
                }
            }
            if (packageName == "com.oplus.battery") {
                ILog.i(TAG, "forbidden: packageName:com.oplus.battery")
                return true
            }
        }
        val now = System.currentTimeMillis()
        for (app in timeList){
            if(app.packageName==packageName){
                if(app.timeSetting!!.isInRange(now)){
                    return false
                }
                ILog.i(TAG, "forbidden: $packageName not in allow time")
                return true
            }
        }
        return osSetting.isForbid(packageName,event)
    }


    /** 是否配置了白名单（白名单模式：非白名单一律禁止） */
    fun hasWhiteList(): Boolean = whiteList.isNotEmpty()

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: MessageEvent) {
        ILog.i(TAG,"on EventBus -> Event:"+event.type)
        if(event.type== EventType.TIME_LIMIT){
            refreshData()
        }
    }

    private fun refreshData(){
        ILog.d(TAG,"refresh Time Limit Data")
        allowList.clear()
        rangeList.clear()
        try {
            val timeLimit = LitePal.findAll(TimeSetting::class.java)
            for (setting in timeLimit) {
                when (setting.type) {
                    AppSetting.TYPE_WHITE -> {
                        allowList.add(setting)
                        rangeList.add(setting)
                    }
//                    AppSetting.TYPE_BLACK -> {
//                        denyList.add(setting)
//                    }
                    AppSetting.TYPE_TIME -> {
                        rangeList.add(setting)
                    }
                    AppSetting.TYPE_DELETE -> {
                        LitePal.delete(TimeSetting::class.java,setting.id)
                    }
                }
            }
        }catch (e: Exception){
            ILog.e(TAG,"refresh Time Limit Data error",e)
        }

        whiteList.clear()
        blackList.clear()
        timeList.clear()
        val appList = LitePal.findAll(AppSetting::class.java)
        for (app in appList){
            // 管控App自身永远由无障碍守卫(pkg != APPLICATION_ID)放行，绝不能进任何名单。
            // 尤其：若自身被误设为白名单(type=1)，会让 hasWhiteList() 为真、整机翻转成白名单
            // 模式，把其它所有App(如微信)全部拦截。这里遇到自身设置行直接删除并跳过，从根杜绝。
            if (selfPackage != null && app.packageName == selfPackage) {
                ILog.i(TAG, "drop self app-setting to avoid whitelist lockdown: ${app.packageName} type=${app.type}")
                try { LitePal.delete(AppSetting::class.java, app.id) } catch (e: Exception) {}
                continue
            }
            when (app.type) {
                AppSetting.TYPE_WHITE -> {
                    whiteList.add(app)
                }
                AppSetting.TYPE_BLACK -> {
                    blackList.add(app)
                }
                AppSetting.TYPE_TIME -> {
                    for(ts in rangeList){
                        if(ts.id == app.timeLimit){
                            app.timeSetting = ts
                            break
                        }
                    }
                    timeList.add(app)
                }
                AppSetting.TYPE_DELETE -> {
                    LitePal.delete(AppSetting::class.java,app.id)
                }
            }
        }
    }

    companion object {
        const val TAG = "LimitManager"
    }
}