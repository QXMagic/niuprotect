package im.niu.corelib.utils

import android.text.TextUtils
import android.util.Log

object ILog {
    private const val isDebug = true

    fun log(obj: Any?) {
        if (obj != null) {
            Log.i("ILOG", obj.toString())
        }
    }

    fun d(tag: String?, msg: String?) {
        if (!TextUtils.isEmpty(msg)) {
            Log.d(tag, msg!!)
        }
    }
    fun e(tag: String?, msg: String?) {
        if (!TextUtils.isEmpty(msg)) {
            Log.e(tag, msg!!)
        }
    }
    fun i(tag: String?, msg: String?) {
        if (!TextUtils.isEmpty(msg)) {
            Log.i(tag, msg!!)
        }
    }
}