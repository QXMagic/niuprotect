package com.niu.protect.map.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.baidu.trace.model.StatusCodes;
import com.niu.protect.core.Constants;


public class TrackReceiver extends BroadcastReceiver {
    private PowerManager.WakeLock wakeLock;

    public TrackReceiver(PowerManager.WakeLock wakeLock) {
        this.wakeLock = null;
        this.wakeLock = wakeLock;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.SCREEN_OFF".equals(action)) {
            PowerManager.WakeLock wakeLock = this.wakeLock;
            if (wakeLock != null && !wakeLock.isHeld()) {
                this.wakeLock.acquire();
            }
        } else if ("android.intent.action.SCREEN_ON".equals(action) || "android.intent.action.USER_PRESENT".equals(action)) {
            PowerManager.WakeLock wakeLock2 = this.wakeLock;
            if (wakeLock2 != null && wakeLock2.isHeld()) {
                this.wakeLock.release();
            }
        } else if (StatusCodes.GPS_STATUS_ACTION.equals(action)) {
            int statusCode = intent.getIntExtra(Constants.RESULT, 0);
            String statusMessage = intent.getStringExtra("statusMessage");
            System.out.println(String.format("GPS status, statusCode:%d, statusMessage:%s", Integer.valueOf(statusCode), statusMessage));
        }
    }
}
