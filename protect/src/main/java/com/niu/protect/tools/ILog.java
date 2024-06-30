package com.niu.protect.tools;

import android.text.TextUtils;
import android.util.Log;
public class ILog {
    private static final boolean isDebug = true;

    public static void log(Object obj) {
        if (obj != null) {
            Log.i("ILOG", obj.toString());
        }
    }

    public static void d(String tag, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }
}
