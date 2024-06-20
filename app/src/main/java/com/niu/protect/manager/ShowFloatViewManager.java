package com.niu.protect.manager;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.baidu.platform.comapi.map.NodeType;
import com.niu.protect.tools.ILog;
public class ShowFloatViewManager {
    public static final String ALARM_ACTION = "com.babyprotect.alarmAction";
    public static ShowFloatViewManager mShowFloatViewManager;
    private TextView mFloatingButton;
    private WindowManager mWindowManager;

    private ShowFloatViewManager() {
    }

    public static ShowFloatViewManager getInstance() {
        if (mShowFloatViewManager == null) {
            synchronized (ShowFloatViewManager.class) {
                mShowFloatViewManager = new ShowFloatViewManager();
            }
        }
        return mShowFloatViewManager;
    }

    public void showFloatView(Context context) {
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(-2, -2, 0, 0, -2);
        mParams.x = 0;
        mParams.y = 0;
        mParams.flags = 8;
        mParams.gravity = 51;
        Log.d("MainActivity", "sdk:" + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= 26) {
            mParams.type = 2038;
        } else {
            mParams.type = NodeType.E_STREET_CLICK_JUMP_MOVE;
        }
        TextView textView = new TextView(context);
        this.mFloatingButton = textView;
        textView.setTextColor(Color.argb(0, 0, 0, 0));
        this.mFloatingButton.setText("*.");
        this.mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ILog.d("aaaa", "点击了");
            }
        });
        this.mWindowManager.addView(this.mFloatingButton, mParams);
    }

    public void removeFloatView() {
        TextView textView;
        WindowManager windowManager = this.mWindowManager;
        if (windowManager != null && (textView = this.mFloatingButton) != null) {
            windowManager.removeView(textView);
        }
    }
}
