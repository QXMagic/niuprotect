package im.niu.corelib.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import im.niu.corelib.R

class AlertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)
    }
}