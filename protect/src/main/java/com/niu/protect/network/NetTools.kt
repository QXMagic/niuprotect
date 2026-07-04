package com.niu.protect.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.niu.protect.core.Constants
import com.niu.protect.manager.UserInfoManager
import com.niu.protect.tools.ILog
import com.niu.protect.tools.ToastUtil
import com.niu.protect.tools.Tools
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class NetTools {
    var netListener: ResultCallBackListener? = null
    var request: Request? = null
    var softContext: WeakReference<Context?>? = null
    var call: Call? = null
    var urlKey = ""
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            var isok = false
            var str: String? = msg.obj.toString()
            if (msg.what == -1) {
                str = null
            } else if (msg.what == -2) {
                str = null
                softContext!!.get()
            }
            if (str != null) {
                try {
                    ILog.log("$urlKey====$msg")
                    if (str.contains("html")) {
                        ILog.log("request error")
                        return
                    }
                    val json = JSONObject(str)
                    if (json.has("code")) {
                        // likeadmin 格式 {code, show, msg, data}：1=成功，-1=登录态失效
                        val code = json.getInt("code")
                        if (code == 1) {
                            isok = true
                            if (netListener != null) {
                                netListener!!.onResponse(json)
                            }
                        } else {
                            if (code == -1) {
                                Tools.saveToken(softContext!!.get(), null)
                            }
                            if (softContext!!.get() != null) {
                                Tools.showAlert3(softContext!!.get(), json.optString("msg"))
                            }
                        }
                    } else {
                        val code = json.getString("status")
                        if (code != null) {
                            if (code == "200") {
                                isok = true
                                if (netListener != null) {
                                    netListener!!.onResponse(json)
                                }
                            } else {
                                if (code == "100008") {
                                    if (softContext!!.get() != null) {
                                        UserInfoManager.getInstance()
                                            .saveUser(softContext!!.get(), null)
                                    }
                                } else if (code == "UNAUTHORIZED") {
                                    Tools.saveToken(softContext!!.get(), null)
                                } else {
                                    Tools.showAlert3(softContext!!.get(), json.getString("message"))
                                }
                                if (softContext!!.get() != null) {
                                    Tools.showAlert3(softContext!!.get(), json.getString("message"))
                                }
                            }
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            if (!isok && netListener != null) {
                netListener!!.onResponse(null)
            }
            netListener = null
            dissLoad()
        }
    }

    fun postAsynHttp(
        context: Context?,
        key: String,
        parameters: Map<String?, String?>,
        nListener: ResultCallBackListener?
    ) {
        postAsynHttp(context, false, key, parameters, nListener, null)
    }

    fun postAsynHttpWithAuthorization(
        context: Context?,
        key: String,
        parameters: Map<String?, String?>,
        nListener: ResultCallBackListener?,
        token: String?
    ) {
        postAsynHttp(context, false, key, parameters, nListener, token)
    }

    fun postAsynHttp(
        context: Context?,
        showLoad: Boolean,
        key: String,
        parameters: Map<String?, String?>,
        nListener: ResultCallBackListener?,
        authorization: String?
    ) {
        var authorization = authorization
        urlKey = key
        softContext = WeakReference(context)
        //        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        val mOkHttpClient = OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).build()
        netListener = nListener
        if (!NetCheckUtil.isNetworkAvailable(softContext!!.get())) {
            netListener!!.onResponse(null)
            ToastUtil.show("网络连接出问题了，请检查网络连接")
            return
        }
        if (showLoad) {
            showLoad()
        }
        val fc = FormBody.Builder()
        for ((key1, value) in parameters) {
            fc.add(key1, toUtf8(value))
        }
        val token = Tools.getToken(context)
        if (token != null) {
            authorization = token
        }
        ILog.d(TAG, "token ==$token")
        val `object` = JSONObject(parameters)
        ILog.d(TAG, "content ==$`object`")
        val requestBody =
            FormBody.create(MediaType.parse(Constants.DEFAULT_CONTENT_TYPE), `object`.toString())
        if (authorization != null && authorization != "") {
            val builder = Request.Builder()
            val url = builder.url(StudentBaseUrl.baseurl + key)
            request =
                url.addHeader("Authorization", "Bearer $authorization")
                    .addHeader("token", authorization) // likeadmin 从 token 头取登录态
                    .post(requestBody).build()
        } else {
            val builder2 = Request.Builder()
            request = builder2.url(StudentBaseUrl.baseurl + key).post(requestBody).build()
        }
        val newCall = mOkHttpClient.newCall(request)
        call = newCall
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val msg = Message()
                msg.obj = ""
                if (e.message == null || e.message.toString() == "Socket closed") {
                    msg.what = -1
                } else {
                    msg.what = -2
                    e.printStackTrace()
                }
                handler.sendMessage(msg)
                Log.i(TAG, "error:" + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val str = response.body()!!.string()
                Log.i(TAG, "network---" + urlKey + Constants.COLON_SEPARATOR + str)
                val msg = Message()
                msg.obj = str
                msg.what = 1
                handler.sendMessage(msg)
            }
        })
    }

    fun postAsynJSONHttp(
        context: Context?,
        key: String,
        `object`: JSONObject,
        nListener: ResultCallBackListener?
    ) {
        softContext = WeakReference(context)
        val utoken = Tools.getToken(context)
        ILog.d(TAG, utoken)
        netListener = nListener
        if (!NetCheckUtil.isNetworkAvailable(softContext!!.get())) {
            netListener!!.onResponse(null)
            ToastUtil.show("网络连接出问题了，请检查网络连接")
            return
        }
        val params = `object`.toString()
        val JSON = MediaType.parse(Constants.DEFAULT_CONTENT_TYPE)
        val body = RequestBody.create(JSON, params)
        val builder = Request.Builder()
        val url = builder.url(StudentBaseUrl.baseurl + key)
        val request = url.addHeader("Authorization", "Bearer $utoken").post(body).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val msg = Message()
                msg.obj = ""
                if (e.message == "Socket closed") {
                    msg.what = -1
                } else {
                    msg.what = -2
                }
                handler.sendMessage(msg)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val str = response.body()!!.string()
                val msg = Message()
                msg.obj = str
                msg.what = 1
                handler.sendMessage(msg)
            }
        })
    }

    fun getAsynHttp(
        context: Context?,
        key: String,
        parameters: Map<String, String>,
        nListener: ResultCallBackListener?
    ) {
        getAsynHttp(context, true, key, parameters, null, nListener)
    }

    fun getAsynHttp(
        context: Context?,
        key: String,
        parameters: Map<String, String>,
        token: String?,
        nListener: ResultCallBackListener?
    ) {
        getAsynHttp(context, true, key, parameters, token, nListener)
    }

    fun getAsynHttp(
        context: Context?,
        showLoad: Boolean,
        key: String,
        parameters: Map<String, String>,
        token: String?,
        nListener: ResultCallBackListener?
    ) {
        val token2: String?
        urlKey = key
        softContext = WeakReference(context)
        //        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        val mOkHttpClient = OkHttpClient.Builder().build()
        netListener = nListener
        if (!NetCheckUtil.isNetworkAvailable(softContext!!.get())) {
            netListener!!.onResponse(null)
            ToastUtil.show("网络连接出问题了，请检查网络连接")
            return
        }
        if (showLoad) {
            showLoad()
        }
        val url = StudentBaseUrl.baseurl + key
        var parameterStr = ""
        val utoken = Tools.getToken(context)
        token2 = utoken ?: token
        Log.i(TAG, "token $token2")
        for ((key1, value) in parameters) {
            parameterStr = if (parameterStr == "") "?$key1=$value" else "$parameterStr&$key1=$value"
        }
        val requestBuilder =
            Request.Builder().url(url + parameterStr).addHeader("Authorization", "Bearer $token2")
        if (token2 != null) {
            requestBuilder.addHeader("token", token2) // likeadmin 从 token 头取登录态
        }
        requestBuilder.method("GET", null)
        val build = requestBuilder.build()
        request = build
        val newCall = mOkHttpClient.newCall(build)
        call = newCall
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val msg = Message()
                msg.obj = ""
                if (e.message == "Socket closed") {
                    msg.what = -1
                } else {
                    msg.what = -2
                }
                handler.sendMessage(msg)
                Log.i(TAG, "error" + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val str = response.body()!!.string()
                val msg = Message()
                msg.obj = str
                msg.what = 1
                handler.sendMessage(msg)
            }
        })
    }

    fun postImageAsynHttp(
        context: Context?,
        showLoad: Boolean,
        key: String,
        parameters: Map<String, String?>,
        nListener: ResultCallBackListener?
    ) {
        val mOkHttpClient = OkHttpClient()
        softContext = WeakReference(context)
        netListener = nListener
        if (showLoad) {
            showLoad()
        }
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        for ((key1, value) in parameters) {
            if (key1.contains("file")) {
                val f = File(value)
                builder.addFormDataPart(key1, f.name, RequestBody.create(MediaType.parse("*/*"), f))
            } else {
                builder.addFormDataPart(key1, value)
            }
        }
        val token = Tools.getToken(context)
        if (token != null) {
            builder.addFormDataPart("token", token)
        }
        val requestBody = builder.build()
        val builder2 = Request.Builder()
        val build = builder2.url(StudentBaseUrl.baseurl + key).post(requestBody).build()
        request = build
        val newCall = mOkHttpClient.newCall(build)
        call = newCall
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val msg = Message()
                msg.obj = ""
                if (e.message == "Socket closed") {
                    msg.what = -1
                } else {
                    msg.what = -2
                }
                handler.sendMessage(msg)
                Log.i(TAG, "error:" + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val str = response.body()!!.string()
                val msg = Message()
                msg.obj = str
                msg.what = 1
                handler.sendMessage(msg)
            }
        })
    }

    private fun showLoad() {}
    fun dissLoad() {}
    private fun toUtf8(str: String?): String? {
        return if (str.isNullOrEmpty()) {
            ""
        } else try {
            return str.toByteArray().toString();
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        //    public static final MediaType FORM_CONTENT_TYPE = MediaType.parse(AbstractBceClient.DEFAULT_CONTENT_TYPE);
        private val TAG = NetTools::class.java.name
        var netTools: NetTools? = null
        @JvmStatic
        val instance: NetTools
            get() = NetTools()
    }
}