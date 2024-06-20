package com.niuniu.babyprotect.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class TestService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TestService", "********************START****************");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
