package com.niuniu.babyprotect.tools;

import android.content.Context;
import android.content.Intent;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.ui.main.MainActivity;
public class JumpActivityTools {
    public static void jumpToMainActivity(Context context, UserInfo userInfo) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.TAG_USERINFO, userInfo);
        context.startActivity(intent);
    }
}
