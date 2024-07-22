package im.niu.corelib.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import im.niu.corelib.R
import im.niu.corelib.service.KeepLiveJobService
import im.niu.corelib.service.MainIntentService

class BootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boot)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForegroundService(Intent(this, MainIntentService::class.java))
        } else {
            startService(Intent(this, MainIntentService::class.java))
        }
        KeepLiveJobService.startJob(this)

        finish()
    }
}