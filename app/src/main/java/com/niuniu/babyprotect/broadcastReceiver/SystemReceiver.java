package com.niuniu.babyprotect.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.niuniu.babyprotect.tools.ILog;
public class SystemReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ILog.d("SystemReceiver--", "SystemReceiver--------" + intent.getAction());
    }
}
