package com.niu.protect.work;

import android.content.Context;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import java.util.concurrent.TimeUnit;
public class MineWorkerManager {
    public static final int PERIODTIME = 15;
    public static final String WORK_NAME = "name_get_use_appinfos";
    public static MineWorkerManager instance = null;
    public static final String preTag = "pre_request";

    private MineWorkerManager() {
    }

    public static MineWorkerManager getInstance() {
        if (instance == null) {
            synchronized (MineWorkerManager.class) {
                if (instance == null) {
                    instance = new MineWorkerManager();
                }
            }
        }
        return instance;
    }

    /**
     * 提交一次性的工作请求。
     * 使用WorkManager来安排一个一次性的工作任务，这个任务由Worker1类实现。
     * 该方法通过创建一个WorkRequest对象并将其加入到WorkManager的队列中来实现任务的安排。
     * 一旦任务被安排，它将在满足WorkRequest中定义的约束条件时执行。
     *
     * @param context 应用程序的上下文，用于获取WorkManager的实例。
     */
    public void oneTimesWorkRequest(Context context) {
        // 创建一个一次性的工作请求，该请求执行由Worker1类定义的工作。
        WorkRequest myWorkRequest = OneTimeWorkRequest.from(Worker1.class);
        // 获取WorkManager的实例，并将刚才创建的工作请求加入到执行队列中。
        WorkManager.getInstance(context).enqueue(myWorkRequest);
    }


    public void periodicWorkRequest(Context context) {
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresBatteryNotLow(false).setRequiresDeviceIdle(false).build();
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(OnePeriodicWorkerAppUse.class, 15L, TimeUnit.MINUTES).setConstraints(constraints).setInitialDelay(15L, TimeUnit.SECONDS).addTag(preTag).build();
        cancelWork(context);
        WorkManager.getInstance(context).enqueueUniquePeriodicWork("uploadAppUse", ExistingPeriodicWorkPolicy.KEEP, request);
    }

    public static void cancelWork(Context context) {
        WorkManager.getInstance(context).cancelAllWorkByTag(preTag);
    }
}
