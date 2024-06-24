package com.niu.protect.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.niu.protect.lib.Constants;
import com.niu.protect.manager.UserInfoManager;
import com.niu.protect.manager.WebSocketManager;
import com.niu.protect.model.UserInfo;
import com.niu.protect.tools.ILog;
import com.niu.protect.websocket.ScreenAndBattaryStatusEvent;
import java.util.Timer;
import java.util.TimerTask;
public class ScreenReceiver extends BroadcastReceiver {

    private static final int PreTime = 10000;
    public static final int SCREENN_OFF = 2;
    public static final int SCREENN_ON = 1;
    private static final String TAG = "ScreenReceiver";
    public static boolean isOn = true;
    private String action;
    Timer timer;
    TimerTask timerTask;
    private int totalTime = 0;
    boolean isStartRun = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        this.action = action;
        if (action.equals("android.intent.action.SCREEN_ON")) {
            ILog.d(TAG, "ACTION_SCREEN_ON");
            if (!isOn) {
                isOn = true;
            }
            WebSocketManager.getInstance().reconnect();
        } else if (this.action.equals("android.intent.action.SCREEN_OFF")) {
            ILog.d(TAG, "ACTION_SCREEN_OFF");
            isOn = false;
            WebSocketManager.getInstance().sendScreenEventMessage("1011", "关闭屏幕");
            sendScreenMsg(context, 0);
        } else if (this.action.equals("android.intent.action.USER_PRESENT")) {
            ILog.d(TAG, "开屏");
            WebSocketManager.getInstance().reconnect();
            WebSocketManager.getInstance().sendScreenEventMessage("1012", "打开屏幕");
            sendScreenMsg(context, 1);
        } else if (this.action.equals(Constants.ACTION_ACCESSIBILITY_START)) {
            ILog.d(TAG, "ACTION_ACCESSIBILITY_START");
            WebSocketManager.getInstance().reconnect();
            WebSocketManager.getInstance().sendScreenEventMessage("1013", "自动辅助权限已经开启");
        } else if (this.action.equals(Constants.ACTION_ACCESSIBILITY_STOP)) {
            ILog.d(TAG, "ACTION_ACCESSIBILITY_STOP");
            WebSocketManager.getInstance().sendScreenEventMessage("1014", "自动辅助权限已经关闭");
        } else if (this.action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            ILog.d(TAG, "CONNECTIVITY_ACTION");
            WebSocketManager.getInstance().reconnect();
        }
    }

    private int getBatteryLevel(Context context) {
        BatteryManager manager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        return manager.getIntProperty(4);
    }

    private void sendScreenMsg(Context context, int screenStauts) {
        int battery = getBatteryLevel(context);
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(context);
        if (userInfo != null) {
            String userId = UserInfoManager.getInstance().getUserInfo(context).getId();
            WebSocketManager webSocketManager = WebSocketManager.getInstance();
            webSocketManager.sendMessageEventEntry(3, new ScreenAndBattaryStatusEvent(battery + "", screenStauts, userId));
        }
    }
}
