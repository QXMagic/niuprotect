package com.niu.protect.keepalive;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
public class KeepAliveJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("KeepAliveJobService", "JobService");
//        if (Build.VERSION.SDK_INT >= 24) {
//            startJob(this);
//        }
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
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(10, new ComponentName(context.getPackageName(), KeepAliveJobService.class.getName())).setPersisted(true);
//        if (Build.VERSION.SDK_INT < 24) {
//            jobInfoBuilder.setPeriodic(5000L);
//        } else {
            jobInfoBuilder.setMinimumLatency(5000L);
//        }
        jobScheduler.schedule(jobInfoBuilder.build());
    }
}
