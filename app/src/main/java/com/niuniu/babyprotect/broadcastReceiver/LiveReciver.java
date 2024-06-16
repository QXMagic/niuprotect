package com.niuniu.babyprotect.broadcastReceiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
//import androidx.lifecycle.CoroutineLiveDataKt;
import com.niuniu.babyprotect.accessibility.auto.app.OpenSettingApp;
import com.niuniu.babyprotect.manager.WebSocketManager;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.ToastUtil;
import java.util.ArrayList;
public class LiveReciver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        ILog.d("LiveReciver--", "LiveReciver" + intent.getAction());
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    OpenSettingApp.showIcon(context);
                }
            }, 5000L);
        } else if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
            ILog.d("xxxxxxa==1：", "手机被唤醒");
        } else if ("com.example.service_destory".equals(intent.getAction())) {
            ILog.d("xxxxxxa==1：", "上次服务被挂了");
            ToastUtil.show("上次服务被挂了");
        } else if ("com.example.clock".equals(intent.getAction())) {
            ILog.d("xxxxxxa==1：", "定时闹钟的广播");
        } else if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
            ILog.d("xxxxxxa==1：", "关机");
            WebSocketManager.getInstance().sendScreenEventMessage("1015", "关机");
        }
    }

    public boolean isServiceRunning(Context context, String ServiceName) {
        if (TextUtils.isEmpty(ServiceName)) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList) myManager.getRunningServices(1000);
        for (int i = 0; i < runningService.size(); i++) {
            Log.i("xxxxxxa==1：", "" + runningService.get(i).service.getClassName().toString());
            if (runningService.get(i).service.getClassName().toString().equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }
}
