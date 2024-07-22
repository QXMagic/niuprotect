package im.niu.corelib.service

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import im.niu.corelib.utils.ILog
import im.niu.corelib.utils.NiuUtil

class KeepLiveJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        ILog.d(Tag,"on job start")
        //        if (Build.VERSION.SDK_INT >= 24) {
//            startJob(this);
//        }
        val isLocalServiceRunning: Boolean =
            NiuUtil.isServiceRunning(this,MainIntentService::class.java.name
            )
        if (!isLocalServiceRunning) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForegroundService(Intent(this, MainIntentService::class.java))
            } else {
                startService(Intent(this, MainIntentService::class.java))
            }
        }
        return false
    }


    override fun onStopJob(params: JobParameters?): Boolean {
        ILog.e(Tag,"on job stop")
        return false
    }


    companion object {
        private var JobId = 10
        private var Tag = "KeepLiveJob"
        fun startJob(context: Context) {
            ILog.d(Tag,"to start job")
            var jobScheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            var jobInfoBuilder = JobInfo.Builder(
                JobId, ComponentName(
                    context.packageName,
                    KeepLiveJobService::class.java.name
                )
            ).setPersisted(true)
            jobInfoBuilder.setMinimumLatency(5000L)
            jobScheduler.schedule(jobInfoBuilder.build())
        }
    }
}