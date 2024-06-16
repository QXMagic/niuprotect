package com.niuniu.babyprotect.third.floatwindow;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import im.niu.protect.R;
public class AppRunFloatWindowsManager {
    private static volatile AppRunFloatWindowsManager mInstance;
    private ValueAnimator animator;
    private Button btnClose;
    private View floatView;
    private int layoutX;
    private int layoutY;
    private Context mContext;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    public static synchronized AppRunFloatWindowsManager getInstance() {
        AppRunFloatWindowsManager appRunFloatWindowsManager;
        synchronized (AppRunFloatWindowsManager.class) {
            if (mInstance == null) {
                synchronized (AppRunFloatWindowsManager.class) {
                    if (mInstance == null) {
                        mInstance = new AppRunFloatWindowsManager();
                    }
                }
            }
            appRunFloatWindowsManager = mInstance;
        }
        return appRunFloatWindowsManager;
    }

    public AppRunFloatWindowsManager initManager(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        showWindow();
        return this;
    }

    public boolean requestPermission(Context context) {
        return SettingsCompat.canDrawOverlays(context, false, false);
    }

    private synchronized void showWindow() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_float_window, (ViewGroup) null, false);
        this.floatView = inflate;
        this.btnClose = (Button) inflate.findViewById(R.id.btn_close);
        this.mLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= 26) {
            this.mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            this.mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        this.mLayoutParams.format = 1;
        this.mLayoutParams.gravity = Gravity.TOP|Gravity.LEFT;//51 = 32,16,2,1;
        this.mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ;//40;
        this.mContext.getResources().getDisplayMetrics();
        this.layoutY = 0;
        this.layoutX = 0;
        this.mLayoutParams.width = -2;
        this.mLayoutParams.height = -2;
        this.mLayoutParams.x = this.layoutX;
        this.mLayoutParams.y = this.layoutY;
        setListener();
    }

    public void showFloatWindow() {
        this.mWindowManager.addView(this.floatView, this.mLayoutParams);
    }

    public void removeFloatWindow() {
        WindowManager windowManager;
        View view = this.floatView;
        if (view != null && (windowManager = this.mWindowManager) != null) {
            windowManager.removeView(view);
        }
    }

    public class fun1 implements View.OnTouchListener {
        boolean downMove = false;
        int finalMoveX;
        boolean isMove;
        private int moveX;
        long startTime;
        int startX;
        int startY;

        fun1() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            if (action == 0) {
                this.startX = (int) event.getX();
                this.startY = (int) event.getY();
                this.startTime = System.currentTimeMillis();
                this.isMove = false;
                this.downMove = false;
                return false;
            } else if (action != 1) {
                if (action != 2) {
                    return false;
                }
                if (Math.abs(this.startX - event.getX()) > 2.0f || Math.abs(this.startY - event.getY()) > 2.0f) {
                    this.downMove = true;
                    mLayoutParams.x = (int) (event.getRawX() - this.startX);
                    mLayoutParams.y = (int) (event.getRawY() - this.startY);
                    updateViewLayout();
                }
                return true;
            } else {
                long curTime = System.currentTimeMillis();
                boolean z = curTime - this.startTime > 100;
                this.isMove = z;
                if (z) {
                    if (mLayoutParams.x + (floatView.getMeasuredWidth() / 2) >= mWindowManager.getDefaultDisplay().getWidth() / 2) {
                        this.finalMoveX = mWindowManager.getDefaultDisplay().getWidth() - floatView.getMeasuredWidth();
                    } else {
                        this.finalMoveX = 0;
                    }
                    AppRunFloatWindowsManager appRunFloatWindowsManager = AppRunFloatWindowsManager.this;
                    appRunFloatWindowsManager.animator = ValueAnimator.ofInt(appRunFloatWindowsManager.mLayoutParams.x, this.finalMoveX).setDuration(Math.abs(mLayoutParams.x - this.finalMoveX));
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            onTouch(valueAnimator);
//                            AppRunFloatWindowsManager.1.this.lambda$onTouch$0$AppRunFloatWindowsManager$1(valueAnimator);
                        }
                    });
                    animator.start();
                }
                return this.isMove;
            }
        }

        public void onTouch(ValueAnimator animation) {
            if (animation != null) {
                this.moveX = ((Integer) animation.getAnimatedValue()).intValue();
                mLayoutParams.x = ((Integer) animation.getAnimatedValue()).intValue();
                updateViewLayout();
            }
        }
    }

    private void setListener() {
        View view = this.floatView;
        if (view != null) {
            view.setOnTouchListener(new fun1());
        }
        Button button = this.btnClose;
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWindowManager.removeView(floatView);
                }
            });
        }
    }

    public void updateViewLayout() {
        WindowManager.LayoutParams layoutParams;
        WindowManager windowManager;
        View view = this.floatView;
        if (view != null && (layoutParams = this.mLayoutParams) != null && (windowManager = this.mWindowManager) != null) {
            try {
                windowManager.updateViewLayout(view, layoutParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int dp2px(Context context, float dpValue) {
        if (context != null) {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) ((dpValue * scale) + 0.5f);
        }
        return (int) dpValue;
    }
}
