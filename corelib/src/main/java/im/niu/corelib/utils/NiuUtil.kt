package im.niu.corelib.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Build
import android.text.TextUtils
import android.util.Log


class NiuUtil {

    companion object {
        private val TAG = "Util"
        fun _isServiceRunning(context: Context, serviceName: String): Boolean {
            if (TextUtils.isEmpty(serviceName)) {
                return false
            }
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val infos = activityManager.getRunningServices(200)
            for (info in infos) {
                if (TextUtils.equals(info.service.className, serviceName)) {
                    return true
                }
            }
            return false
        }

        fun isAppRunning(context: Context, packageName: String): Boolean {
            if (TextUtils.isEmpty(packageName)) {
                return false
            }
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val infos = activityManager.getRunningAppProcesses()
            for (info in infos) {
                if (TextUtils.equals(info.processName, packageName)) {
                    return true
                }
            }
            return false
        }

        fun isServiceRunning(context: Context,serviceName:String):Boolean{
            var run = _isServiceRunning(context,serviceName)
            ILog.d(TAG,"isServiceRunning:$run")
            val serviceIntent = Intent()
            serviceIntent.setPackage(context.packageName)
            val resolveInfos: List<ResolveInfo> =
                context.getPackageManager().queryIntentServices(serviceIntent, 0)
            for (resolveInfo in resolveInfos) {
                Log.d(TAG, "Intent service name: " + resolveInfo.serviceInfo)
                if(serviceName.equals(resolveInfo.serviceInfo.name)){
                    ILog.d(TAG,"isServiceRunning:true")
                    return true
                }
            }
            ILog.d(TAG,"isServiceRunning:false")
            return false
        }
    }
}