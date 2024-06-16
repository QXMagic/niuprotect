package com.niuniu.babyprotect.tools;

import android.widget.Toast;
import com.niuniu.babyprotect.BabyApplication;
public class ToastUtil {
    public static void show(String msg) {
        Toast.makeText(BabyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
