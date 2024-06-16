package com.niuniu.babyprotect.map.maputil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
public class ViewUtil {
    private Toast mToast = null;
    private TextView mTextView = null;

    public void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void startActivityForResult(Activity fromActivity, Class<?> toClass, int requestCode) {
        Intent intent = new Intent(fromActivity, toClass);
        fromActivity.startActivityForResult(intent, requestCode);
    }

    public static void setBackgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(2);
        context.getWindow().setAttributes(lp);
    }

    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= 67108864;
        } else {
            winParams.flags &= -67108865;
        }
        win.setAttributes(winParams);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((pxValue / scale) + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dpValue * scale) + 0.5f);
    }

    public static void resizePicker(FrameLayout frameLayout) {
        List<NumberPicker> numberPickers = findNumberPicker(frameLayout);
        for (NumberPicker numberPicker : numberPickers) {
            resizeNumberPicker(numberPicker);
        }
    }

    private static List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> numberPickers = new ArrayList<>();
        if (viewGroup != null) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    numberPickers.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                } else {
                    continue;
                }
            }
        }
        return numberPickers;
    }

    private static void resizeNumberPicker(NumberPicker numberPicker) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, -2);
        params.setMargins(15, 0, 15, 0);
        numberPicker.setLayoutParams(params);
    }
}
