package im.niu.corelib

import android.content.Context
import com.tencent.mmkv.MMKV
import java.util.UUID

class App {
    fun init(){
        
    }

    companion object {
        fun init(context: Context) {
            MMKV.initialize(context)
        }
    }
}