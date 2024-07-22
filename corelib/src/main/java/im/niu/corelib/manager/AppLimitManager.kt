package im.niu.corelib.manager

import android.content.Context
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

class AppLimitManager(context: Context) {
    private lateinit var allowList:MutableList<TimeSetting>
    private lateinit var denyList:MutableList<TimeSetting>
    private lateinit var rangeList:MutableList<TimeSetting>

    private var whiteList: MutableList<AppSetting>
    private var blackList: MutableList<AppSetting>
    private var timeList: MutableList<AppSetting>
    private var systemBlackMap: HashMap<String,String>
    private var systemWhiteMap: HashMap<String,String>
    private var osSetting: OperateSystem;

    init {
        EventBus.getDefault().register(this)
        whiteList = ArrayList()
        blackList = ArrayList()
        timeList = ArrayList()
        systemBlackMap = HashMap()
        systemWhiteMap = HashMap()

        systemBlackMap["com.coloros.oppoguardelf"] = "未知"
        systemBlackMap["com.huawei.systemmanager.power"] = "未知"
        osSetting = OperateSystem.getOsSetting()
    }

    fun isAllow(packageName: String): Boolean {
        if (whiteList.size > 0) {
            for (app in whiteList) {
                if (app.packageName == packageName) {
                    return true
                }
            }
        }
        if (blackList.size > 0) {
            for (app in blackList) {
                if (app.packageName == packageName) {
                    return false
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
        return false
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: MessageEvent) {
        if(event.type== EventType.TIME_LIMIT){
            refreshData()
        }
    }

    private fun refreshData(){
        ILog.d(TAG,"refresh Time Limit Data")
        try {
            val timeLimit = LitePal.findAll(TimeSetting::class.java)
            for (setting in timeLimit) {

            }
        }catch (e: Exception){
            ILog.e(TAG,"refresh Time Limit Data error",e)
        }

        whiteList.clear()
        blackList.clear()
        timeList.clear()
        val applist = LitePal.findAll(AppSetting::class.java)
        for (app in applist){
            when (app.type) {
                AppSetting.TYPE_WHITE -> {
                    whiteList.add(app)
                }
                AppSetting.TYPE_BLACK -> {
                    blackList.add(app)
                }
                AppSetting.TYPE_TIME -> {

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