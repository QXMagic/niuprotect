package com.niuniu.babyprotect.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.baidubce.AbstractBceClient;
import com.huawei.hms.framework.common.ContainerUtils;
import com.niuniu.babyprotect.BuildConfig;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.ToastUtil;
import com.niuniu.babyprotect.tools.Tools;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONException;
import org.json.JSONObject;
public class NetTools {
    public static final MediaType FORM_CONTENT_TYPE = MediaType.parse(AbstractBceClient.DEFAULT_CONTENT_TYPE);
    private static final String TAG = "netTools";
    static NetTools netTools;
    ResultCallBackListener netListener;
    Request request;
    WeakReference<Context> softContext;
    Call call = null;
    public String urlKey = "";
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            boolean isok = false;
            String str = msg.obj.toString();
            if (msg.what == -1) {
                str = null;
            } else if (msg.what == -2) {
                str = null;
                softContext.get();
            }
            if (str != null) {
                try {
                    ILog.log(urlKey + "====" + msg);
                    if (str.contains("html")) {
                        ILog.log("request error");
                        return;
                    }
                    JSONObject json = new JSONObject(str);
                    String code = json.getString("status");
                    if (code != null) {
                        if (code.equals("200")) {
                            isok = true;
                            if (netListener != null) {
                                netListener.onResponse(json);
                            }
                        } else {
                            if (code.equals("100008")) {
                                if (softContext.get() != null) {
                                    UserInfoManager.getInstance().saveUser(softContext.get(), null);
                                }
                            } else if (code.equals("UNAUTHORIZED")) {
                                Tools.saveToken(softContext.get(), null);
                            } else {
                                Tools.showAlert3(softContext.get(), json.getString("message"));
                            }
                            if (softContext.get() != null) {
                                Tools.showAlert3(softContext.get(), json.getString("message"));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (!isok && netListener != null) {
                netListener.onResponse(null);
            }
            netListener = null;
            dissLoad();
        }
    };

    public static NetTools getInstance() {
        return new NetTools();
    }

    public void postAsynHttp(Context context, String key, Map<String, String> parameters, ResultCallBackListener nListener) {
        postAsynHttp(context, false, key, parameters, nListener, null);
    }

    public void postAsynHttpWithAuthorization(Context context, String key, Map<String, String> parameters, ResultCallBackListener nListener, String token) {
        postAsynHttp(context, false, key, parameters, nListener, token);
    }

    public void postAsynHttp(Context context, boolean showLoad, String key, Map<String, String> parameters, ResultCallBackListener nListener, String authorization) {
        this.urlKey = key;
        this.softContext = new WeakReference<>(context);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).build();
        this.netListener = nListener;
        if (!NetCheckUtil.isNetworkAvailable(this.softContext.get())) {
            this.netListener.onResponse(null);
            ToastUtil.show("网络连接出问题了，请检查网络连接");
            return;
        }
        if (showLoad) {
            showLoad();
        }
        FormBody.Builder fc = new FormBody.Builder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            fc.add(entry.getKey(), toUtf8(entry.getValue()));
        }
        String token = Tools.getToken(context);
        if (token != null) {
            authorization = token;
        }
        ILog.d(TAG, "token ==" + token);
        JSONObject object = new JSONObject(parameters);
        ILog.d(TAG, "content ==" + object.toString());
        RequestBody requestBody = FormBody.create(MediaType.parse(AbstractBceClient.DEFAULT_CONTENT_TYPE), object.toString());
        if (authorization != null && !authorization.equals("")) {
            Request.Builder builder = new Request.Builder();
            Request.Builder url = builder.url(StudentBaseUrl.baseurl + key);
            this.request = url.addHeader("Authorization", "Bearer " + authorization).post(requestBody).build();
        } else {
            Request.Builder builder2 = new Request.Builder();
            this.request = builder2.url(StudentBaseUrl.baseurl + key).post(requestBody).build();
        }
        Call newCall = mOkHttpClient.newCall(this.request);
        this.call = newCall;
        newCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.obj = "";
                if (e.getMessage() == null || e.getMessage().toString().equals("Socket closed")) {
                    msg.what = -1;
                } else {
                    msg.what = -2;
                    e.printStackTrace();
                }
                handler.sendMessage(msg);
                Log.i(NetTools.TAG, "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i(NetTools.TAG, "network---" + urlKey + Constants.COLON_SEPARATOR + str);
                Message msg = new Message();
                msg.obj = str;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }

    public void postAsynJSONHttp(Context context, String key, JSONObject object, ResultCallBackListener nListener) {
        this.softContext = new WeakReference<>(context);
        String utoken = Tools.getToken(context);
        ILog.d(TAG, utoken);
        this.netListener = nListener;
        if (!NetCheckUtil.isNetworkAvailable(this.softContext.get())) {
            this.netListener.onResponse(null);
            ToastUtil.show("网络连接出问题了，请检查网络连接");
            return;
        }
        String params = object.toString();
        MediaType JSON = MediaType.parse(AbstractBceClient.DEFAULT_CONTENT_TYPE);
        RequestBody body = RequestBody.create(JSON, params);
        Request.Builder builder = new Request.Builder();
        Request.Builder url = builder.url(StudentBaseUrl.baseurl + key);
        Request request = url.addHeader("Authorization", "Bearer " + utoken).post(body).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.obj = "";
                if (e.getMessage().equals("Socket closed")) {
                    msg.what = -1;
                } else {
                    msg.what = -2;
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Message msg = new Message();
                msg.obj = str;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }

    public void getAsynHttp(Context context, String key, Map<String, String> parameters, ResultCallBackListener nListener) {
        getAsynHttp(context, true, key, parameters, null, nListener);
    }

    public void getAsynHttp(Context context, String key, Map<String, String> parameters, String token, ResultCallBackListener nListener) {
        getAsynHttp(context, true, key, parameters, token, nListener);
    }

    public void getAsynHttp(Context context, boolean showLoad, String key, Map<String, String> parameters, String token, ResultCallBackListener nListener) {
        String token2;
        this.urlKey = key;
        this.softContext = new WeakReference<>(context);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().build();
        this.netListener = nListener;
        if (!NetCheckUtil.isNetworkAvailable(this.softContext.get())) {
            this.netListener.onResponse(null);
            ToastUtil.show("网络连接出问题了，请检查网络连接");
            return;
        }
        if (showLoad) {
            showLoad();
        }
        String url = StudentBaseUrl.baseurl + key;
        String parameterStr = "";
        String utoken = Tools.getToken(context);
        if (utoken == null) {
            token2 = token;
        } else {
            token2 = utoken;
        }
        Log.i(TAG, "token " + token2);
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            parameterStr = parameterStr.equals("") ? "?" + entry.getKey() + "=" + entry.getValue() : parameterStr + "&" + entry.getKey() + "=" + entry.getValue();
        }
        Request.Builder requestBuilder = new Request.Builder().url(url + parameterStr).addHeader("Authorization", "Bearer " + token2);
        requestBuilder.method("GET", null);
        Request build = requestBuilder.build();
        this.request = build;
        Call newCall = mOkHttpClient.newCall(build);
        this.call = newCall;
        newCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.obj = "";
                if (e.getMessage().equals("Socket closed")) {
                    msg.what = -1;
                } else {
                    msg.what = -2;
                }
                handler.sendMessage(msg);
                Log.i(NetTools.TAG, "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Message msg = new Message();
                msg.obj = str;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }

    public void postImageAsynHttp(Context context, boolean showLoad, String key, Map<String, String> parameters, ResultCallBackListener nListener) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        this.softContext = new WeakReference<>(context);
        this.netListener = nListener;
        if (showLoad) {
            showLoad();
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (entry.getKey().contains("file")) {
                File f = new File(entry.getValue());
                builder.addFormDataPart(entry.getKey(), f.getName(), RequestBody.create(MediaType.parse("*/*"), f));
            } else {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        String token = Tools.getToken(context);
        if (token != null) {
            builder.addFormDataPart("token", token);
        }
        MultipartBody requestBody = builder.build();
        Request.Builder builder2 = new Request.Builder();
        Request build = builder2.url(StudentBaseUrl.baseurl + key).post(requestBody).build();
        this.request = build;
        Call newCall = mOkHttpClient.newCall(build);
        this.call = newCall;
        newCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.obj = "";
                if (e.getMessage().equals("Socket closed")) {
                    msg.what = -1;
                } else {
                    msg.what = -2;
                }
                handler.sendMessage(msg);
                Log.i(NetTools.TAG, "error:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Message msg = new Message();
                msg.obj = str;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }

    private void showLoad() {
    }

    public void dissLoad() {
    }

    public String toUtf8(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        try {
            String result = new String(str.getBytes(), "UTF-8");
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
