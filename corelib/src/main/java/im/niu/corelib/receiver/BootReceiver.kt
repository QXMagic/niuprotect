package im.niu.corelib.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import im.niu.corelib.service.MainIntentService
import im.niu.corelib.utils.ILog
import im.niu.corelib.worker.BootWorker
import java.util.concurrent.TimeUnit


class BootReceiver : BroadcastReceiver() {
    private var preTag = "boot_receiver"
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {

            val constraints: Constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(false).setRequiresDeviceIdle(false).build()

            val request: PeriodicWorkRequest =
                PeriodicWorkRequest.Builder(BootWorker::class.java, 15L, TimeUnit.MINUTES).setConstraints(
                    constraints
                ).setInitialDelay(15L, TimeUnit.SECONDS).addTag(preTag).build()
            cancelWork(context)

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork("uploadAppUse", ExistingPeriodicWorkPolicy.KEEP, request)

            ILog.d("BOOT", "boot receiver,to start Main Service")
//            val serviceIntent = Intent(context, MainIntentService::class.java)
//            context.startForegroundService(serviceIntent)
        }
//        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
//            // 启动应用
//            val intent = Intent(context, AppReceiver::class.java)
//            context.sendBroadcast(intent)
//        }
    }

    fun cancelWork(context: Context?) {
        WorkManager.getInstance(context!!).cancelAllWorkByTag(preTag)
    }
}