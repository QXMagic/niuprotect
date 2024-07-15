package com.niu.protect.keepalive;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.niu.protect.backService.IMyAidlInterface;
import com.niu.protect.core.AppGlobal;
import com.niu.protect.manager.StudentMainController;
import com.niu.protect.manager.UserInfoManager;
import com.niu.protect.manager.WebSocketManager;
import com.niu.protect.model.UserInfo;
import com.niu.protect.model.WebsocketMessage;
import com.niu.protect.model.eventbus.EventParentChangeBindMode;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;

import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
public class LocalForegroundService extends Service {
    private static final String TAG = "WebSClient";
    private static WebSocketManager instance;
    WebSClient cc;
    private Connection connection;
    Context mContext;
    private MyBinder myBinder;
    ScreenOpenReceiver screenReceiver;
    private boolean isDestroy = false;
    Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            EventBus.getDefault().post(new EventParentChangeBindMode(""));
            StudentMainController.getInstance().requestMainControl();
        }
    };

    class MyBinder extends IMyAidlInterface.Stub {
        MyBinder() {
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.myBinder = new MyBinder();
        startService();
    }

    public void startService() {
        NotificationChannel channel = new NotificationChannel("service", "service", NotificationManager.IMPORTANCE_NONE);
        channel.setLightColor(-16776961);
        channel.setLockscreenVisibility(0);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(channel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "service");
        Notification notification = builder.setOngoing(true).setPriority(-2).setCategory("service").build();
        startForeground(10, notification);
    }

    public void bindService() {
        this.connection = new Connection();
        Intent bindIntent = new Intent(this, RemoteForegroundService.class);
        bindService(bindIntent, this.connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService();
        return super.onStartCommand(intent, flags, startId);
    }

    public class Connection implements ServiceConnection {
        Connection() {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService();
            bindService();
        }
    }

    public static class CancelNotificationService extends Service {
        @Override
        public void onCreate() {
            super.onCreate();
            startForeground(10, new Notification());
            stopSelf();
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

    private void start() {
        ILog.d(TAG, "-uri---start-");
        this.mContext = AppGlobal.MainInstance.getContext();
        if (this.cc == null) {
            UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this.mContext);
            if (userInfo == null) {
                ILog.d(TAG, "-uri---userInfo==null -");
                return;
            }
            String uri = StudentBaseUrl.WEBSOCKET_URI + userInfo.getId();
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
            ILog.d(LocalForegroundService.TAG, "StatusMessage---" + handshakedata.getHttpStatusMessage());
            ILog.d(LocalForegroundService.TAG, "getHttpStatus---" + ((int) handshakedata.getHttpStatus()));
        }

        @Override
        public void onMessage(String msg) {
            ILog.d(LocalForegroundService.TAG, "rec msg --" + msg);
            dealMsg(msg);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            ILog.d(LocalForegroundService.TAG, "onClose--" + code + "---reason--" + reason);
            if (code == -1 || code == 1006) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ILog.d(LocalForegroundService.TAG, "reconnectWebsocket---" + isDestroy);
                        if (!isDestroy) {
                            reconnectWebsocket();
                        }
                    }
                }, 10000l);
            }
        }

        @Override
        public void onError(Exception ex) {
            ILog.d(LocalForegroundService.TAG, "onError--" + ex.getMessage());
        }
    }

    public void dealMsg(String content) {
        if (!TextUtils.isEmpty(content) && !content.equals("conn_success")) {
            WebsocketMessage mUmengCustomMsg = (WebsocketMessage) new Gson().fromJson(content,WebsocketMessage.class);
            int type = mUmengCustomMsg.getOperateType();
            ILog.d(TAG, "onMessage--orderorder ==" + type);
            if (type == 12) {
//                startService(new Intent(this.mContext, LocationTraceService.class));
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
        unScreenBroadcastReceiver();
        close();
        ILog.d(TAG, "onDestroy----");
        this.isDestroy = true;
    }

    private void close() {
        WebSClient webSClient = this.cc;
        if (webSClient != null) {
            webSClient.close();
            this.cc = null;
            ILog.d(TAG, "-close-");
        }
    }

    private void onRegisterReceiver() {
        this.screenReceiver = new ScreenOpenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        filter.addAction(AppGlobal.ACTION_ACCESSIBILITY_START);
        filter.addAction(AppGlobal.ACTION_ACCESSIBILITY_STOP);
        registerReceiver(this.screenReceiver, filter);
    }

    public void unScreenBroadcastReceiver() {
        ScreenOpenReceiver screenOpenReceiver = this.screenReceiver;
        if (screenOpenReceiver != null) {
            unregisterReceiver(screenOpenReceiver);
        }
    }

    public class ScreenOpenReceiver extends BroadcastReceiver {
        public ScreenOpenReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ILog.d("ScreenOpenReceiver---", "--intent---" + intent.getAction());
            reconnectWebsocket();
        }
    }
}
