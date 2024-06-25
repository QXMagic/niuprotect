package com.niu.protect.tools;

import android.widget.Toast;

import com.niu.protect.core.Constants;

public class ToastUtil {
    public static void show(String msg) {
        Toast.makeText(Constants.MainInstance.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
