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

    public void oneTimesWorkRequest(Context context) {
        WorkRequest myWorkRequest = OneTimeWorkRequest.from(Worker1.class);
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
