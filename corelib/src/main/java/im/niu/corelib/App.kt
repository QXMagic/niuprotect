package im.niu.corelib

import android.app.Application
import android.content.Context
import android.content.Intent
import com.tencent.mmkv.MMKV
import im.niu.corelib.service.KeepLiveJobService
import im.niu.corelib.service.MainIntentService
import java.util.UUID

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        init(this)
    }

    companion object {
        val APP_NAME = "妞管家"

        fun init(context: Context) {
            MMKV.initialize(context)
        }
    }
}