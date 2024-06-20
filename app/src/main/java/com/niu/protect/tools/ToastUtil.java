package com.niu.protect.tools;

import android.widget.Toast;
import com.niu.protect.BabyApplication;
public class ToastUtil {
    public static void show(String msg) {
        Toast.makeText(BabyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
