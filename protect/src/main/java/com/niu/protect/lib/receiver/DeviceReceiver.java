package com.niu.protect.lib.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.niu.protect.manager.MineDevicePolicyManager;
public class DeviceReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, "设备管理：可用", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(final Context context, Intent intent) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MineDevicePolicyManager.getInstance(context.getApplicationContext()).onActivate();
            }
        }, 3000L);
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "这是一个可选的消息，警告有关禁止用户的请求";
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        Toast.makeText(context, "设备管理：密码己经改变", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        Toast.makeText(context, "设备管理：改变密码失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        Toast.makeText(context, "设备管理：改变密码成功", Toast.LENGTH_SHORT).show();
    }
}
