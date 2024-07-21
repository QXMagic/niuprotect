package im.niu.corelib.manager

import im.niu.corelib.data.AppSetting
import org.litepal.LitePal

/**
 *
 *  同步服务器端数据
 *
 * @author : xin ge
 * @date : 2024/7/19
 * @description : App 管理器
 */
class AppController {
    private var whiteList: MutableList<AppSetting> = ArrayList()
    private var blackList: MutableList<AppSetting> = ArrayList()
    private var timeRangeList: MutableList<AppSetting> = ArrayList()

    fun init() {
        val list =LitePal.findAll(AppSetting::class.java)
        for (item in list){
            when(item.type){
                AppSetting.TYPE_WHITE -> whiteList.add(item)
                AppSetting.TYPE_BLACK -> blackList.add(item)
                AppSetting.TYPE_TIME_RANGE -> timeRangeList.add(item)
            }
        }
    }

    fun pushSetting(setting: AppSetting){
        setting.save()
        when(setting.type){
            AppSetting.TYPE_WHITE -> whiteList.add(setting)
            AppSetting.TYPE_BLACK -> blackList.add(setting)
            AppSetting.TYPE_TIME_RANGE -> timeRangeList.add(setting)
            AppSetting.TYPE_DELETE -> {
                setting.delete()
            }
        }
    }

    fun removeSetting(setting: AppSetting){
        setting.delete()
        when(setting.type){
            AppSetting.TYPE_WHITE -> whiteList.remove(setting)
            AppSetting.TYPE_BLACK -> blackList.remove(setting)
            AppSetting.TYPE_TIME_RANGE -> timeRangeList.remove(setting)
        }
    }
}