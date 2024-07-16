package im.niu.corelib.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import im.niu.corelib.manager.BroadcastManager
import im.niu.corelib.receiver.MainReceiver
import im.niu.corelib.utils.ILog
import org.greenrobot.eventbus.EventBus

class NiuAccessibilityService : AccessibilityService() {

    private val TAG = "NiuAccessibilityService"
    /**
     * Callback for [android.view.accessibility.AccessibilityEvent]s.
     *
     * @param event The new event. This event is owned by the caller and cannot be used after
     * this method returns. Services wishing to use the event after this method returns should
     * make a copy.
     */
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        ILog.d(TAG,"event")
    }

    /**
     * Callback for interrupting the accessibility feedback.
     */
    override fun onInterrupt() {
        ILog.d(TAG,"onInterrupt")
    }

    override fun onCreate() {
        super.onCreate()
        ILog.d(TAG,"onCreate")
//        EventBus.getDefault().register(this)
        BroadcastManager.register(this)


    }

    override fun onDestroy() {
        super.onDestroy()
        ILog.d(TAG,"onDestroy")
//        EventBus.getDefault().unregister(this)
        BroadcastManager.unRegister(this)
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        ILog.d(TAG,"onServiceConnected")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ILog.d(TAG,"onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }
}