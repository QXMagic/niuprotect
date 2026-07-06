package com.niu.protect.lib.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
public class DeviceReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, "防卸载已开启：需先取消设备管理器才能卸载", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(final Context context, Intent intent) {
        // 取消激活后，本应用即可被卸载。防卸载的核心是「激活期间系统禁止卸载」，
        // 不再做后台强制重新激活(受后台启动限制不可靠，且与用户对抗)。
        Toast.makeText(context, "防卸载已关闭", Toast.LENGTH_SHORT).show();
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "关闭后孩子将可以卸载本应用，家长管控会失效。确定要关闭吗？";
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
