package im.niu.corelib.manager

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import im.niu.corelib.data.json.BaseResponse
import im.niu.corelib.data.json.VersionInfo
import im.niu.corelib.events.EventType
import im.niu.corelib.events.MessageEvent
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class UpgradeManager(private val context: Context) {

    private val TAG = "UpgradeManager"
    private val SERVER_VERSION_URL = "https://dev.xinyu126.com/api/mm/children/version?version=" // 替换为实际的服务器版本检查URL
    private val client = OkHttpClient()
    fun upgrade() {
        if (!downloadApk()) {
            Log.d(TAG, "No new version available")
        }
    }


    private fun getCurrentAppVersion(): String {
        // 获取当前应用的版本号
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionName
    }

    private fun downloadApk() :Boolean {

        val currentVersion = getCurrentAppVersion()
        Log.d(TAG, "current version is $currentVersion")
        var request = Request.Builder()
            .url(SERVER_VERSION_URL+currentVersion)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e(TAG, "Error downloading APK: ${e.message}")
            }
            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                // 解析响应中的版本号
                val resp = response.body?.string()?.trim() ?: ""
                // 判断版本号是否需要更新
                // 解析json
                val gson = Gson()
                val type = object : TypeToken<BaseResponse<VersionInfo>>() {}
                val baseResponse = gson.fromJson(resp, type)
                if(baseResponse.code!=1){
                    return
                }

                val versionInfo = baseResponse.data
                if(versionInfo.version == currentVersion){
                    return
                }
                val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "niu_"+versionInfo.version+".apk")
                if(file.exists()){
                    installApk(file)
                    return
                }
                request = Request.Builder()
                    .url(versionInfo.url)
                    .build()
                client.newCall(request).enqueue(object:Callback{
                    override fun onFailure(call: okhttp3.Call, e: IOException) {
                        Log.e(TAG, "Error downloading APK: ${e.message}")
                    }
                    override fun onResponse(call: okhttp3.Call, response: Response) {
                        val tmpFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "download_temp.apk")
                        Log.e(TAG, "Download complete ,body length= ${response.body?.contentLength()}")
                        response.body?.let { body ->{
                            body.byteStream().use { input ->
                                val output = FileOutputStream(tmpFile)
                                output.write(input.readBytes())
                                output.close()
                                tmpFile.renameTo(file)
                                installApk(file)
                            }
                        }}
                    }
                })
            }
        })

        return true
    }

    private fun installApk(file: File) {
        EventBus.getDefault().post(MessageEvent(EventType.MSG_INSTALL_APK))
        val intent = Intent(Intent.ACTION_VIEW)
        val apkUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        context.startActivity(intent)
    }
}
