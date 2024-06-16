package com.niuniu.babyprotect.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.accessibility.auto.app.OpenSettingApp;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.model.AppInfo;
import com.niuniu.babyprotect.model.EventMessageModel;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.model.WebsocketMessage;
import com.niuniu.babyprotect.model.eventbus.EventParentChangeBindMode;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.apk.ApkTools;
import com.niuniu.babyprotect.ui.map.LocationTraceService;
import com.niuniu.babyprotect.websocket.BaseWebSocketEvent;
import java.net.URI;
import java.net.URISyntaxException;
import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
public class WebSocketManager {
    private static final String TAG = "WebSClient";
    public static final int TAG_MSG_SEND_MSG = 2;
    private static WebSocketManager instance;
    WebSClient cc;
    Context mContext;
    Gson mGson;
    String userId;
    String packageName = "";
    Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2 && cc != null && !cc.isClosed()) {
                cc.send((String) msg.obj);
                ILog.d(WebSocketManager.TAG, "接收到消息后发送消息：" + msg.obj);
            }
        }
    };

    private WebSocketManager() {
    }

    public static WebSocketManager getInstance() {
        if (instance == null) {
            synchronized (WebSocketManager.class) {
                if (instance == null) {
                    instance = new WebSocketManager();
                }
            }
        }
        return instance;
    }

    public void start() {
        UserInfo userInfo;
        this.mContext = BabyApplication.getInstance();
        if (this.cc != null || (userInfo = UserInfoManager.getInstance().getUserInfo(this.mContext)) == null) {
            return;
        }
        this.userId = userInfo.getId();
        String uri = "ws://139.9.121.96:8281/stu-mob-mon-customer/api/websocket/" + userInfo.getId();
        ILog.d(TAG, "-uri-" + uri);
        try {
            WebSClient webSClient = new WebSClient(new URI(uri));
            this.cc = webSClient;
            webSClient.close();
            this.cc.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void getUserId() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this.mContext);
        if (userInfo == null) {
            return;
        }
        this.userId = userInfo.getId();
    }

    public void reconnect() {
        WebSClient webSClient = this.cc;
        if (webSClient == null) {
            start();
        } else if (webSClient.isClosed()) {
            try {
                this.cc.reconnect();
                ILog.d(TAG, "-reconnect---true");
            } catch (Exception e) {
                ILog.d(TAG, "-reconnect---Exception");
            }
        }
    }

    private void close() {
        WebSClient webSClient = this.cc;
        if (webSClient != null) {
            webSClient.close();
            this.cc = null;
            ILog.d(TAG, "-close-");
        }
    }

    public class WebSClient extends WebSocketClient {
        public WebSClient(URI serverUri) {
            super(serverUri);
        }

        public WebSClient(URI serverUri, Draft protocolDraft) {
            super(serverUri, protocolDraft);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            ILog.d(WebSocketManager.TAG, "StatusMessage---" + handshakedata.getHttpStatusMessage());
            ILog.d(WebSocketManager.TAG, "getHttpStatus---" + ((int) handshakedata.getHttpStatus()));
        }

        @Override
        public void onMessage(String msg) {
            ILog.d(WebSocketManager.TAG, "rec msg --" + msg);
            dealMsg(msg);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            ILog.d(WebSocketManager.TAG, "onClose--" + code + "---reason--" + reason);
        }

        @Override
        public void onError(Exception ex) {
            ILog.d(WebSocketManager.TAG, "onError--" + ex.getMessage());
        }
    }

    public void dealMsg(String msg) {
        if (!TextUtils.isEmpty(msg) && !msg.equals("conn_success")) {
            WebsocketMessage mUmengCustomMsg = (WebsocketMessage) new Gson().fromJson(msg, (Class<Object>) WebsocketMessage.class);
            int type = mUmengCustomMsg.getOperateType();
            ILog.d(TAG, "onMessage--orderorder ==" + type);
            if (type == 12) {
                this.mContext.startService(new Intent(this.mContext, LocationTraceService.class));
                return;
            }
            if (type == 16 || type == 17) {
                this.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventParentChangeBindMode(""));
                        StudentMainController.getInstance().getUserWhiteApp();
                    }
                });
            } else if (type == 18) {
                this.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        UserInfoManager.getInstance().refreshUserInfo(mContext, new UserInfoManager.OnSuccessCallBack() {
                            @Override
                            public void onSuccess() {
                            }
                        });
                    }
                });
            } else if (type == 21) {
                this.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        OpenSettingApp.checkShowIcon(mContext, 1);
                    }
                });
            } else if (type == 20) {
                this.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        OpenSettingApp.checkShowIcon(mContext, 2);
                    }
                });
            } else if (type == 22) {
                this.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        TempOutControlManager.getInstance().requestOutControl(mContext);
                    }
                });
            } else if (type == 23) {
                this.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        XcxControlManager.getInstance().requestSmallProgramlist(mContext);
                    }
                });
            } else {
                this.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventParentChangeBindMode(""));
                        StudentMainController.getInstance().requestMainControl();
                    }
                });
            }
            this.mHandler.obtainMessage(2, mUmengCustomMsg.getMemberId()).sendToTarget();
        }
    }

    public void sendMessage(String msg) {
        WebSClient webSClient = this.cc;
        if (webSClient != null && !webSClient.isClosed()) {
            this.cc.send(msg);
        }
    }

    public void sendMessageEventEntry(int eventType, Object o) {
        BaseWebSocketEvent webSocketEvent = new BaseWebSocketEvent();
        webSocketEvent.setType(eventType);
        webSocketEvent.setData(o);
        String msg = new Gson().toJson(webSocketEvent);
        ILog.d(TAG, "msg: " + msg);
        sendMessage(msg);
    }

    public void sendEventMessage(String eventType, String evenPackageName) {
        String appName;
        if (TextUtils.isEmpty(evenPackageName)) {
            ILog.log("evenPackageName  is empty");
        } else if (this.packageName.equals(evenPackageName)) {
        } else {
            this.packageName = evenPackageName;
            WebSClient webSClient = this.cc;
            if (webSClient != null && webSClient.isOpen()) {
                if (TextUtils.isEmpty(this.userId)) {
                    getUserId();
                }
                if (!TextUtils.isEmpty(this.userId)) {
                    if (this.mGson == null) {
                        this.mGson = new Gson();
                    }
                    if (evenPackageName.contains("launcher")) {
                        appName = "系统桌面";
                    } else {
                        AppInfo appInfo = ApkTools.getAPPInfoByPackageName(this.mContext, evenPackageName);
                        if (appInfo == null) {
                            appName = evenPackageName;
                        } else {
                            appName = appInfo.getAppName();
                        }
                    }
                    String eventMessage = "您的孩子正在使用" + appName;
                    EventMessageModel mEventMessageModel = new EventMessageModel(this.userId, eventType, eventMessage, System.currentTimeMillis(), evenPackageName);
                    String sendMessage = this.mGson.toJson(mEventMessageModel);
                    ILog.d("websockets", "sendMessage:" + sendMessage);
                    this.cc.send(sendMessage);
                    return;
                }
                ILog.log("websockets userId is empty");
            }
        }
    }

    public void sendScreenEventMessage(String eventType, String eventName) {
        WebSClient webSClient = this.cc;
        if (webSClient != null && webSClient.isOpen()) {
            if (TextUtils.isEmpty(this.userId)) {
                getUserId();
            }
            if (!TextUtils.isEmpty(this.userId)) {
                if (this.mGson == null) {
                    this.mGson = new Gson();
                }
                String str = this.userId;
                EventMessageModel mEventMessageModel = new EventMessageModel(str, eventType, "您的孩子" + eventName, System.currentTimeMillis(), "");
                String sendMessage = this.mGson.toJson(mEventMessageModel);
                ILog.d("websockets", "sendMessage:" + sendMessage);
                this.cc.send(sendMessage);
                return;
            }
            ILog.log("websockets userId is empty");
        }
    }

    public void onDestroy() {
        close();
    }
}
