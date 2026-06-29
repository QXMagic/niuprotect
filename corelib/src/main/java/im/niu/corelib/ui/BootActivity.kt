package im.niu.corelib.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import im.niu.corelib.manager.UpgradeManager
import im.niu.corelib.service.KeepLiveJobService
import im.niu.corelib.service.MainIntentService

class BootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForegroundService(Intent(this, MainIntentService::class.java))
        } else {
            startService(Intent(this, MainIntentService::class.java))
        }
        KeepLiveJobService.startJob(this)
        val up = UpgradeManager(this)
        up.upgrade()
        finish()
    }
}