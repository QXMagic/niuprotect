package com.niu.protect.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.niu.protect.manager.WebSocketManager;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.apk.ApkTools;
public class AppInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        ILog.d("AppInstallReceiver", "add-----" + intent.getAction());
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            ILog.d("AppInstallReceiver", "有新安装的app" + packageName);
            WebSocketManager webSocketManager = WebSocketManager.getInstance();
            webSocketManager.sendScreenEventMessage("1014", "安装了新应用" + packageName + "，请查看新安装的应用");
        } else if (intent.getAction().equals("android.intent.action.INSTALL_PACKAGE")) {
            String packageName2 = intent.getDataString();
            ILog.d("AppInstallReceiver", "有新安装的app");
            WebSocketManager webSocketManager2 = WebSocketManager.getInstance();
            webSocketManager2.sendScreenEventMessage("1014", "安装了新应用" + packageName2);
        } else if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName3 = intent.getDataString();
            ILog.d("AppInstallReceiver", "卸载了app");
            ApkTools.getAPPInfoByPackageName(context, packageName3);
            WebSocketManager webSocketManager3 = WebSocketManager.getInstance();
            webSocketManager3.sendScreenEventMessage("1015", "卸载了应用" + packageName3);
        }
    }
}
