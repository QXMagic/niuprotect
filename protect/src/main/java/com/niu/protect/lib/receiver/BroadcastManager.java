package com.niu.protect.lib.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.niu.protect.core.Constants;
import com.niu.protect.tools.ILog;
import java.util.List;
public class BroadcastManager {
    static AppInstallReceiver mAppInstallReceiver;
    static ShutDownReciver mShutDownReciver;
    static ScreenReceiver screenReceiver;

    /**
     * Android 14(targetSdk 34)起，动态注册非「纯系统广播」的 receiver 必须显式声明
     * RECEIVER_EXPORTED / NOT_EXPORTED，否则抛 SecurityException。这些 receiver 只接收
     * 系统广播和本应用自发广播，用 NOT_EXPORTED 即可。
     * 漏了这个标志会导致无障碍服务 onCreate 崩溃、服务永远绑不上。
     */
    private static void registerReceiverCompat(Context context, android.content.BroadcastReceiver receiver, IntentFilter filter) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            context.registerReceiver(receiver, filter);
        }
    }

    public static void startScreenBroadcastReceiver(Context context) {
        screenReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        filter.addAction(Constants.ACTION_ACCESSIBILITY_START);
        filter.addAction(Constants.ACTION_ACCESSIBILITY_STOP);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiverCompat(context, screenReceiver, filter);
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
        registerReceiverCompat(context, mShutDownReciver, filter);
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
        registerReceiverCompat(context, mAppInstallReceiver, filter);
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
        Intent intent = new Intent(Constants.ACTION_ACCESSIBILITY_START);
        context.sendBroadcast(intent);
    }

    public static void sendAccessibilityStop(Context context) {
        Intent intent = new Intent(Constants.ACTION_ACCESSIBILITY_STOP);
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
