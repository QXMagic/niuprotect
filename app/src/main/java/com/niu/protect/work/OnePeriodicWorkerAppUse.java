package com.niu.protect.work;

import android.content.Context;
import android.content.Intent;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.niu.protect.manager.UseTimeDataManager;
import com.niu.protect.ui.map.LocationTraceService;
public class OnePeriodicWorkerAppUse extends Worker {
    Context mContext;

    public OnePeriodicWorkerAppUse(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
    }

    @Override
    public ListenableWorker.Result doWork() {
        UseTimeDataManager.getInstance().upAppUser();
        this.mContext.startService(new Intent(this.mContext, LocationTraceService.class));
        return ListenableWorker.Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }
}
