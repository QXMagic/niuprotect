package com.niuniu.babyprotect.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.niuniu.babyprotect.accessibility.auto.app.OpenSettingApp;
public class ShutDownReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        OpenSettingApp.hideIcon(context);
    }
}
