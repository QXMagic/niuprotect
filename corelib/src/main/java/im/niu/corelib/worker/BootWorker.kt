package im.niu.corelib.worker

import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import im.niu.corelib.service.MainIntentService
import im.niu.corelib.ui.BootActivity
import im.niu.corelib.utils.ILog

class BootWorker(context: Context, workerParams: WorkerParameters) :
CoroutineWorker(context, workerParams)  {
    override suspend fun doWork(): Result {
        ILog.d("BootWorker","to start Boot Activity")
        var context = applicationContext
        val serviceIntent = Intent(context, BootActivity::class.java)
        serviceIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(serviceIntent)
        return Result.success()
    }
}