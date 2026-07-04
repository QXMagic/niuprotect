package com.niu.protect.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.niu.protect.BabyApplication.Companion.instance
import com.niu.protect.R
import com.niu.protect.manager.DeviceIdManager
import com.niu.protect.tools.ILog
import com.niu.protect.tools.Tools
import com.niu.protect.ui.main.MainActivity
import com.niu.protect.ui.setting.YsActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var appChannel = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getAppChannel()
        Handler(Looper.getMainLooper()).postDelayed({
            enterHomeActivity()
//            UMengManager.initUmeng(instance, appChannel)
        }, 2000L)
    }

    private fun enterHomeActivity() {
        val isFirst = Tools.firstStart(this)
        val intent = if (isFirst) {
            // 已绑定(持有设备凭证)进主页，否则进绑定页
            if (DeviceIdManager.getInstance().isBound) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, BindActivity::class.java)
            }
        } else {
            Intent(this, YsActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

    private fun getAppChannel() {
        val packageManager = packageManager
        try {
            val applicationInfo = packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            )
            if (applicationInfo.metaData != null) {
                val valueOf = applicationInfo.metaData.getString("channel").toString()
                appChannel = valueOf
                ILog.d(TAG, "app channel is $valueOf")
            } else {
                ILog.d(TAG, "app meta is null")
            }
        } catch (e: PackageManager.NameNotFoundException) {
            ILog.d(TAG, "NameNotFoundException")
            e.printStackTrace()
        }
    }

    companion object {
        private val TAG = SplashActivity::class.java.name
    }
}