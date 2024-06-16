package com.niuniu.babyprotect.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.work.WorkRequest;

import com.niuniu.babyprotect.manager.StudentMainController;
public class ControllerWayService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        StudentMainController.getInstance().requestMainControl();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopSelf();
            }
        }, WorkRequest.MIN_BACKOFF_MILLIS);
    }

    @Override
    public IBinder onBind(Intent intent) {
        
//TODO decode
        return  null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TestService", "********************START****************");
        return super.onStartCommand(intent, flags, startId);
    }
}
