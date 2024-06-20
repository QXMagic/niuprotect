package com.niu.protect.work;

import android.content.Context;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
public class Worker1 extends Worker {
    public Worker1(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public ListenableWorker.Result doWork() {
        return null;
    }
}
