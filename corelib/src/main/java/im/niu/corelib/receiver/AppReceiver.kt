package im.niu.corelib.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import im.niu.corelib.utils.ILog

class AppReceiver : BroadcastReceiver() {

    private val TAG = "AppReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        ILog.d(TAG,"app info receive")
    }
}