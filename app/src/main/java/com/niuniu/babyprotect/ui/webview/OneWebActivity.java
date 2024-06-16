package com.niuniu.babyprotect.ui.webview;

import android.os.Bundle;
import android.webkit.WebView;
import im.niu.protect.R;
import com.niuniu.babyprotect.ui.base.BaseWebActivity;
public class OneWebActivity extends BaseWebActivity {
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_web);
        initView();
        initData();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    private void initView() {
        this.webView = (WebView) findViewById(R.id.webView);
    }

    private void initData() {
        String jumpUrl = getIntent().getStringExtra("jumpUrl");
        String title = getIntent().getStringExtra("title");
        changeTitle(title);
        showBack();
        initWebView(this.webView);
        loadUrl(jumpUrl);
    }
}
