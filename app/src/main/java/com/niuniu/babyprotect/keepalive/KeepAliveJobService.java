package com.niuniu.babyprotect.keepalive;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.lifecycle.CoroutineLiveDataKt;
public class KeepAliveJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("KeepAliveJobService", "JobService");
        if (Build.VERSION.SDK_INT >= 24) {
            startJob(this);
        }
        boolean isLocalServiceRunning = ServiceUtils.isServiceRunning(this, LocalForegroundService.class.getName());
        if (!isLocalServiceRunning) {
            startService(new Intent(this, LocalForegroundService.class));
        }
        boolean isRemoteServiceRunning = ServiceUtils.isServiceRunning(this, RemoteForegroundService.class.getName());
        if (!isRemoteServiceRunning) {
            startService(new Intent(this, RemoteForegroundService.class));
            return false;
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("KeepAliveJobService", "JobService onStopJob 关闭");
        return false;
    }

    public static void startJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(10, new ComponentName(context.getPackageName(), KeepAliveJobService.class.getName())).setPersisted(true);
        if (Build.VERSION.SDK_INT < 24) {
            jobInfoBuilder.setPeriodic(CoroutineLiveDataKt.DEFAULT_TIMEOUT);
        } else {
            jobInfoBuilder.setMinimumLatency(CoroutineLiveDataKt.DEFAULT_TIMEOUT);
        }
        jobScheduler.schedule(jobInfoBuilder.build());
    }
}
