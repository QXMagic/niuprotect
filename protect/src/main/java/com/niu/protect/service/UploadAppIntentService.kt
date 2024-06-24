package com.niu.protect.service

import android.app.IntentService
import androidx.work.WorkManager
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.niu.protect.BabyApplication.Companion.instance
import com.niu.protect.manager.UploadAppManager
import com.niu.protect.tools.ILog
import com.niu.protect.tools.image.ImageSave

class UploadAppIntentService(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {

    override fun doWork(): Result {
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        ImageSave.delFile()
        UploadAppManager.getInstance(instance).GetInstallAppList()
        return Result.success()
    }

//    private fun handleActionFoo(param1: String, param2: String) {}
//    private fun handleActionBaz(param1: String, param2: String) {}
//    override fun onDestroy() {
//        super.onDestroy()
//        ILog.d("InitStartIntentService", "ondestroy")
//    }

    companion object {
        private const val ACTION_BAZ = "com.niu.protect.service.action.BAZ"
        private const val ACTION_FOO = "com.niu.protect.service.action.FOO"
        private const val EXTRA_PARAM1 = "com.niu.protect.service.extra.PARAM1"
        private const val EXTRA_PARAM2 = "com.niu.protect.service.extra.PARAM2"
        @JvmStatic
        fun startActionBaz(context: Context, param1: String?, param2: String?) {
//            val intent = Intent(context, UploadAppIntentService::class.java)
//            intent.action = ACTION_BAZ
//            intent.putExtra(EXTRA_PARAM1, param1)
//            intent.putExtra(EXTRA_PARAM2, param2)
//            context.startService(intent)
            val workManager = WorkManager.getInstance(context)
            val workRequest = OneTimeWorkRequest.Builder(UploadAppIntentService::class.java).build()
            workManager.enqueue(workRequest)
        }
    }



}