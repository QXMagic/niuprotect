package com.niuniu.babyprotect.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.baidu.platform.comapi.map.NodeType;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.main.DesktopActivity;
import com.niuniu.babyprotect.ui.main.MainActivity;
public class FloatingService extends Service {
    public static boolean isFloatService = false;
    private TextView mFloatingButton;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    MsgReceiver msgReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        isFloatService = true;
        showFloatingWindow();
        if (!MainActivity.mainRunning) {
            ILog.d("MainActivity---------", " is dead");
        }
        ILog.d("FloatingService---------", " run");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 1;
    }

    private void showFloatingWindow() {
        this.msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.yiou.floatingService.RECEIVER");
        registerReceiver(this.msgReceiver, intentFilter);
        if (Settings.canDrawOverlays(this)) {
            this.mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 0, 0, -2);
            this.mParams = layoutParams;
            layoutParams.x = 0;
            this.mParams.y = 0;
            this.mParams.flags = 8;
            this.mParams.gravity = 51;
            Log.d("MainActivity", "sdk:" + Build.VERSION.SDK_INT);
            if (Build.VERSION.SDK_INT >= 26) {
                this.mParams.type = 2038;
            } else {
                this.mParams.type = NodeType.E_STREET_CLICK_JUMP_MOVE;
            }
            TextView textView = new TextView(this);
            this.mFloatingButton = textView;
            textView.setTextColor(Color.argb(0, 0, 0, 0));
            this.mFloatingButton.setText("*.");
            this.mFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa", "点击了");
                }
            });
            this.mWindowManager.addView(this.mFloatingButton, this.mParams);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void gotoMain() {
        Tools.showAlert3(this, "点击了");
        Intent secondIntent = new Intent(this, DesktopActivity.class);
        secondIntent.addFlags(872480768);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, secondIntent, 0);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
        Tools.saveDeskTop(this, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.msgReceiver);
        closeWindow();
    }

    public void closeWindow() {
        TextView textView;
        WindowManager windowManager = this.mWindowManager;
        if (windowManager != null && (textView = this.mFloatingButton) != null) {
            windowManager.removeView(textView);
        }
    }

    public class MsgReceiver extends BroadcastReceiver {
        public MsgReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            closeWindow();
        }
    }
}
