package com.niu.protect.service

import androidx.work.WorkManager
import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.niu.protect.manager.UploadAppManager
import com.niu.protect.tools.image.ImageSave

class UploadAppWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        ImageSave.delFile()
        UploadAppManager.getInstance(this.applicationContext).GetInstallAppList()
        return Result.success()
    }

    companion object {
        @JvmStatic
        fun startActionBaz(context: Context) {
            val workManager = WorkManager.getInstance(context)
            val workRequest = OneTimeWorkRequest.Builder(UploadAppWorker::class.java).build()
            workManager.enqueue(workRequest)
        }
    }



}