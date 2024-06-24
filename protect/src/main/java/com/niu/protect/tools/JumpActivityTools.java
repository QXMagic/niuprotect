package com.niu.protect.tools;

import android.content.Context;
import android.content.Intent;

import com.niu.protect.model.UserInfo;
public class JumpActivityTools {
    public static void jumpToMainActivity(Context context,Class cls, UserInfo userInfo) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("userinfo", userInfo);
        context.startActivity(intent);
    }
}
