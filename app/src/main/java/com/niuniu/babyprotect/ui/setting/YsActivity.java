package com.niuniu.babyprotect.ui.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import com.niuniu.babyprotect.ui.login.PhoneCodeLoginActivity;
import com.niuniu.babyprotect.ui.main.MainActivity;
import com.niuniu.babyprotect.ui.webview.OneWebActivity;

import im.niu.protect.R;
public class YsActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ys);
        changeTitle("隐私保护说明");
        TextView nottxt = (TextView) findViewById(R.id.nottxt);
        SpannableStringBuilder style = new SpannableStringBuilder();
        style.append((CharSequence) "为了便于您阅读，请您点击下列链接，完整阅读全文：《用户协议》《隐私政策》");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(YsActivity.this, OneWebActivity.class);
                intent.putExtra("jumpUrl", StudentBaseUrl.URL_USER);
                intent.putExtra("title", "用户协议");
                startActivity(intent);
            }
        };
        style.setSpan(clickableSpan, 24, 30, 33);
        nottxt.setText(style);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
        style.setSpan(foregroundColorSpan, 24, 30, 33);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(YsActivity.this, OneWebActivity.class);
                intent.putExtra("jumpUrl", StudentBaseUrl.URL_YINSI);
                intent.putExtra("title", "隐私政策");
                startActivity(intent);
            }
        };
        style.setSpan(clickableSpan2, 30, 36, 33);
        nottxt.setText(style);
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(Color.parseColor("#0000FF"));
        style.setSpan(foregroundColorSpan2, 30, 36, 33);
        nottxt.setMovementMethod(LinkMovementMethod.getInstance());
        nottxt.setText(style);
        TextView cancelbtn = (TextView) findViewById(R.id.cancelbtn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView okbtn = (TextView) findViewById(R.id.okbtn);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.saveOneUse(YsActivity.this);
                String token = Tools.getToken(YsActivity.this);
                if (token != null) {
                    UserInfoManager.getInstance().getUserInfo(YsActivity.this);
                    Intent intent = new Intent(YsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                Intent intent2 = new Intent(YsActivity.this, PhoneCodeLoginActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }
}
