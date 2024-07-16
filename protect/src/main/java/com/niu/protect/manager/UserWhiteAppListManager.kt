package com.niu.protect.manager

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.niu.protect.model.AppData
import com.niu.protect.model.UserBlackAppInfoModel
import com.niu.protect.network.NetTools
import com.niu.protect.network.StudentBaseUrl
import com.niu.protect.tools.ILog
import org.json.JSONObject

class UserWhiteAppListManager private constructor() {
    private var mUserWhiteApps: List<AppData>? = null
    fun isWhiteApp(context: Context, packName: String): Boolean {
        val userBlackApps = getmUserBalckApps(context)
        if (userBlackApps != null && userBlackApps.size > 0) {
            val size = userBlackApps.size
            for (i in 0 until size) {
                val dataDTO = userBlackApps[i]
                if (dataDTO.getPackageName() == packName) {
                    return true
                }
            }
            return false
        }
        return false
    }

    fun reqeustUserWhitelist(context: Context) {
        val parameters: Map<String, String> = HashMap()
        NetTools.instance.getAsynHttp(
            context,
            StudentBaseUrl.USER_WHITE_APP_LIST,
            parameters
        ) { msg: JSONObject? ->
            if (msg != null) {
                Log.i(TAG, msg.toString())
                saveWhiteUserApp(context, msg.toString())
                mUserWhiteApps = null
            }
        }
    }

    private fun getSaveWhiteUserApp(context: Context): List<AppData> {
        val sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0)
        val userMsg = sp.getString(SharedPreManager.KEY_USER_WHITE_APP, "")
        if (!TextUtils.isEmpty(userMsg)) {
            ILog.d(TAG, userMsg)
            val userBlackAppInfoModel =
                Gson().fromJson(userMsg, UserBlackAppInfoModel::class.java) as UserBlackAppInfoModel
            return userBlackAppInfoModel.data
        }
        return ArrayList()
    }

    private fun getmUserBalckApps(context: Context): List<AppData>? {
        if (mUserWhiteApps == null) {
            mUserWhiteApps = getSaveWhiteUserApp(context)
        }
        val sb = StringBuilder()
        sb.append("用户APP白名单------------")
        val list = mUserWhiteApps
        sb.append(list!!.size)
        ILog.d(TAG, sb.toString())
        return mUserWhiteApps
    }

    companion object {
        private const val TAG = "UserWhiteAppListManager"
        @JvmStatic
        var instance: UserWhiteAppListManager? = null
            get() {
                if (field == null) {
                    synchronized(UserWhiteAppListManager::class.java) {
                        if (field == null) {
                            field = UserWhiteAppListManager()
                        }
                    }
                }
                return field
            }
            private set

        fun saveWhiteUserApp(context: Context, msg: String?) {
            val sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0)
            val editor = sp.edit()
            if (msg == null) {
                editor.remove(SharedPreManager.KEY_USER_WHITE_APP)
            } else {
                editor.putString(SharedPreManager.KEY_USER_WHITE_APP, msg)
            }
            editor.commit()
        }
    }
}