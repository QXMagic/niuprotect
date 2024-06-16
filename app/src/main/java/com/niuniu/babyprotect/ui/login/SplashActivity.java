package com.niuniu.babyprotect.ui.login;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.niuniu.babyprotect.BabyApplication;
import im.niu.protect.R;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.third.umeng.UMengManager;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.main.MainActivity;
import com.niuniu.babyprotect.ui.setting.YsActivity;
public class SplashActivity extends AppCompatActivity {
    private String appChannel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getAppChannel();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                enterHomeActivity();
                UMengManager.initUmeng(BabyApplication.getInstance(), appChannel);
            }
        }, 2000L);
    }

    public void enterHomeActivity() {
        boolean isUse = Tools.getOneUse(this);
        if (isUse) {
            UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
            if (userInfo != null) {
                String token = Tools.getToken(this);
                if (token != null) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                Intent intent2 = new Intent(this, PhoneCodeLoginActivity.class);
                startActivity(intent2);
                finish();
                return;
            }
            Intent intent3 = new Intent(this, PhoneCodeLoginActivity.class);
            startActivity(intent3);
            finish();
            return;
        }
        Intent intent4 = new Intent(this, YsActivity.class);
        startActivity(intent4);
        finish();
    }

    private void getAppChannel() {
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                String valueOf = String.valueOf(applicationInfo.metaData.get("channel"));
                this.appChannel = valueOf;
                ILog.d("channel get", valueOf);
            }
        } catch (PackageManager.NameNotFoundException e) {
            ILog.d("channel get", "NameNotFoundException");
            e.printStackTrace();
        }
    }
}
