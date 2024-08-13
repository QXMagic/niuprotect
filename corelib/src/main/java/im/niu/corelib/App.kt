package im.niu.corelib

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import im.niu.corelib.manager.AppDataManager
import im.niu.corelib.manager.AppLimitManager
import im.niu.corelib.net.WebSocketManager
import org.litepal.LitePal

class App : Application(){
    private val Tag = "App"
    override fun onCreate() {
        super.onCreate()
        init(this)
    }
    private fun init(context: Context) {
        MMKV.initialize(context)
        LitePal.initialize(this);
        LitePal.getDatabase();
        webSocketManager = WebSocketManager("192.168.31.31", 9090)
        appManager = AppDataManager(context)
        appLimit = AppLimitManager()
        webSocketManager.syncData()
    }
    companion object {
        lateinit var webSocketManager: WebSocketManager
        lateinit var appManager: AppDataManager
        lateinit var appLimit:AppLimitManager
    }
}