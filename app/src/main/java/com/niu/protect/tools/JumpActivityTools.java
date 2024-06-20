package com.niu.protect.tools;

import android.content.Context;
import android.content.Intent;
import com.niu.protect.model.UserInfo;
import com.niu.protect.ui.main.MainActivity;
public class JumpActivityTools {
    public static void jumpToMainActivity(Context context, UserInfo userInfo) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.TAG_USERINFO, userInfo);
        context.startActivity(intent);
    }
}
