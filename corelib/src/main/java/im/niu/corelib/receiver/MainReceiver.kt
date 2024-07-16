package im.niu.corelib.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import im.niu.corelib.utils.ILog

class MainReceiver : BroadcastReceiver() {
    private val TAG = "MainReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        ILog.d(TAG,"MainReceiver onReceive ${intent.action}")
    }
}