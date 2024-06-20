package com.niu.protect.broadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import com.niu.protect.tools.ILog;
import java.util.List;
public class BroadcastManager {
    static AppInstallReceiver mAppInstallReceiver;
    static ShutDownReciver mShutDownReciver;
    static ScreenReceiver screenReceiver;

    public static void startScreenBroadcastReceiver(Context context) {
        screenReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        filter.addAction(ScreenReceiver.ACTION_ACCESSIBILITY_START);
        filter.addAction(ScreenReceiver.ACTION_ACCESSIBILITY_STOP);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(screenReceiver, filter);
    }

    public static void registerShutDownReciver(Context context) {
        ILog.d("BroadcastManager", "注册广播--");
        if (mShutDownReciver != null) {
            return;
        }
        mShutDownReciver = new ShutDownReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.ACTION_SHUTDOWN");
        filter.addAction("android.intent.action.MEDIA_MOUNTED");
        filter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        context.registerReceiver(mShutDownReciver, filter);
        registerAppInstallReceiver(context);
        startScreenBroadcastReceiver(context);
    }

    public static void registerAppInstallReceiver(Context context) {
        if (mAppInstallReceiver != null) {
            return;
        }
        mAppInstallReceiver = new AppInstallReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.INSTALL_PACKAGE");
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");
        context.registerReceiver(mAppInstallReceiver, filter);
    }

    public static void unRegisterShutDownReciver(Context context) {
        ILog.d("BroadcastManager", "注册广播-unRegisterShutDownReciver-");
        ShutDownReciver shutDownReciver = mShutDownReciver;
        if (shutDownReciver != null) {
            context.unregisterReceiver(shutDownReciver);
            mShutDownReciver = null;
        }
        unAppInstallReceiver(context);
        unScreenBroadcastReceiver(context);
    }

    public static void unAppInstallReceiver(Context context) {
        AppInstallReceiver appInstallReceiver = mAppInstallReceiver;
        if (appInstallReceiver != null) {
            context.unregisterReceiver(appInstallReceiver);
            mAppInstallReceiver = null;
        }
    }

    public static void sendAccessibilityStart(Context context) {
        Intent intent = new Intent(ScreenReceiver.ACTION_ACCESSIBILITY_START);
        context.sendBroadcast(intent);
    }

    public static void sendAccessibilityStop(Context context) {
        Intent intent = new Intent(ScreenReceiver.ACTION_ACCESSIBILITY_STOP);
        context.sendBroadcast(intent);
    }

    public static void unScreenBroadcastReceiver(Context context) {
        ScreenReceiver screenReceiver2 = screenReceiver;
        if (screenReceiver2 != null) {
            context.unregisterReceiver(screenReceiver2);
        }
    }

    private void searchRigisterBroadcast(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.ACTION_SHUTDOWN");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(intent, 0);
        if (resolveInfos != null) {
            resolveInfos.isEmpty();
        }
    }
}
