package im.niu.corelib.manager

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import im.niu.corelib.receiver.AppReceiver
import im.niu.corelib.receiver.MainReceiver
import im.niu.corelib.utils.ILog

class BroadcastManager {



    companion object {
        var mAppInstallReceiver: AppReceiver = AppReceiver()
        var mainReceiver: MainReceiver = MainReceiver()
        private val TAG: String = "BroadCast"

        private fun searchRigisterBroadcast(context: Context,action:String): Boolean {
            val intent = Intent()
            intent.action = action
            val pm: PackageManager = context.packageManager
            val resolveInfos: List<ResolveInfo> = pm.queryBroadcastReceivers(intent, 0)
            for (resolveInfo in resolveInfos){
                ILog.d(TAG, resolveInfo.toString())
                if (resolveInfo.activityInfo.packageName == context.packageName){
                    return true
                }
            }

            return false
        }
        /**
         * 注册一个广播接收器来监听系统相关的广播事件。
         * 本函数用于设置广播接收器所关注的事件类型，并将其注册到系统中。
         *
         * @param context 应用程序的上下文对象，用于注册广播接收器。
         */
        fun register(context: Context) {
            var filter = IntentFilter()
            if(!searchRigisterBroadcast(context,Intent.ACTION_BOOT_COMPLETED)){
                // 注册屏幕开关相关的广播动作
                filter.addAction("android.intent.action.SCREEN_ON")
                filter.addAction("android.intent.action.SCREEN_OFF")
                filter.addAction("android.intent.action.USER_PRESENT") // 注册用户解锁广播动作

                // 注册网络连接状态改变的广播动作
                filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
                // 注册系统关机和媒体设备挂载、卸载的广播动作
                filter.addAction("android.intent.action.ACTION_SHUTDOWN")
                filter.addAction("android.intent.action.MEDIA_MOUNTED")
                filter.addAction("android.intent.action.MEDIA_UNMOUNTED")
                context.registerReceiver(mainReceiver, filter)
                filter = IntentFilter()
            }
            if(!searchRigisterBroadcast(context,Intent.ACTION_PACKAGE_ADDED)) {
                // 创建一个IntentFilter用于过滤广播
                // 注册与软件包安装、删除相关的广播动作
                filter.addAction("android.intent.action.INSTALL_PACKAGE")
                filter.addAction("android.intent.action.PACKAGE_ADDED")
                filter.addAction("android.intent.action.PACKAGE_REMOVED")
                filter.addDataScheme("package") // 添加对软件包名称的过滤
                // 使用上下文对象注册广播接收器，并应用之前设置的IntentFilter
                context.registerReceiver(mAppInstallReceiver, filter)
            }
        }

        fun unRegister(context: Context){
            context.unregisterReceiver(mainReceiver)
            context.unregisterReceiver(mAppInstallReceiver)
        }
    }
}