package com.niuniu.babyprotect.keepalive;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.manager.StudentMainController;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.manager.WebSocketManager;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.model.WebsocketMessage;
import com.niuniu.babyprotect.model.eventbus.EventParentChangeBindMode;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.ui.map.LocationTraceService;
import java.net.URI;
import java.net.URISyntaxException;
import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
public class WebsocktService extends Service {
    private static final String TAG = "WebSClient";
    private static WebSocketManager instance;
    public static boolean webSocketIsDestroy = false;
    WebSClient cc;
    Context mContext;
    Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            EventBus.getDefault().post(new EventParentChangeBindMode(""));
            StudentMainController.getInstance().requestMainControl();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        reconnectWebsocket();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void start() {
        ILog.d(TAG, "-uri---start-");
        this.mContext = BabyApplication.getInstance();
        if (this.cc == null) {
            UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this.mContext);
            if (userInfo == null) {
                ILog.d(TAG, "-uri---userInfo==null -");
                return;
            }
            String uri = "ws://139.9.121.96:8281/stu-mob-mon-customer/api/websocket/" + userInfo.getId();
            ILog.d(TAG, "-uri-" + uri);
            try {
                WebSClient webSClient = new WebSClient(new URI(uri));
                this.cc = webSClient;
                webSClient.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void reconnectWebsocket() {
        WebSClient webSClient = this.cc;
        if (webSClient == null) {
            start();
            return;
        }
        if (webSClient.isClosed()) {
            this.cc.reconnect();
            ILog.d(TAG, "-reconnect-1111111111");
        }
        ILog.d(TAG, "-reconnect-" + this.cc.isClosed());
        ILog.d(TAG, "-reconnect-isOpen :" + this.cc.isOpen());
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
            ILog.d(WebsocktService.TAG, "StatusMessage---" + handshakedata.getHttpStatusMessage());
            ILog.d(WebsocktService.TAG, "getHttpStatus---" + ((int) handshakedata.getHttpStatus()));
        }

        @Override
        public void onMessage(String msg) {
            ILog.d(WebsocktService.TAG, "rec msg --" + msg);
            dealMsg(msg);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            ILog.d(WebsocktService.TAG, "onClose--" + code + "---reason--" + reason);
            if (code == -1 || code == 1006) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!WebsocktService.webSocketIsDestroy) {
                            reconnectWebsocket();
                        }
                    }
                }, 10000l);
            }
        }

        @Override
        public void onError(Exception ex) {
            ILog.d(WebsocktService.TAG, "onError--" + ex.getMessage());
        }
    }

    public void dealMsg(String content) {
        if (!TextUtils.isEmpty(content) && !content.equals("conn_success")) {
            WebsocketMessage mUmengCustomMsg = (WebsocketMessage) new Gson().fromJson(content, WebsocketMessage.class);
            int type = mUmengCustomMsg.getOperateType();
            ILog.d(TAG, "onMessage--orderorder ==" + type);
            if (type == 12) {
                startService(new Intent(this.mContext, LocationTraceService.class));
            } else if (type == 16 || type == 17) {
                this.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventParentChangeBindMode(""));
                        StudentMainController.getInstance().getUserWhiteApp();
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        close();
        ILog.d(TAG, "onDestroy----");
        webSocketIsDestroy = true;
    }

    private void close() {
        WebSClient webSClient = this.cc;
        if (webSClient != null) {
            webSClient.close();
            this.cc = null;
            ILog.d(TAG, "-close-");
        }
    }
}
