package com.niuniu.babyprotect.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.niuniu.babyprotect.ui.webview.OneWebActivity;
public class BaseWebActivity extends BaseActivity {
    int pageSize = 0;
    public String title;
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._context = this;
    }

    public void initWebView(WebView wView) {
        this.webView = wView;
        wView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
//        settings.setAppCacheMaxSize(8388608L);
        String appCachePath = this._context.getApplicationContext().getCacheDir().getAbsolutePath();
//        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
//        settings.setAppCacheEnabled(true);
        this.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                Log.i("xxxx1", url.toString());
                if (url.contains("leave-list")) {
                    view.loadUrl(url);
                    return true;
                }
                String title = "";
                if (url.contains("student/leave")) {
                    title = "添加请假单";
                } else if (url.contains("leave-detail")) {
                    title = "请假详情";
                }
                Intent intent = new Intent(_context, OneWebActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("jumpUrl", url);
                startActivity(intent);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        this.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            }
        });
    }

    public void jumpWeb(String url, String title) {
        jumpWeb(url, title, 0);
    }

    public void jumpShopWeb(String url, String title) {
        jumpWeb(url, title, 1);
    }

    public void jumpWeb(String url, String title, int indexType) {
        Intent intent = new Intent(this._context, OneWebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("jumpUrl", url);
        intent.putExtra("indexType", indexType);
        startActivity(intent);
    }

    public void loadUrl(String url) {
        showlog(url);
        loadUrl(url, true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void loadUrl(String url, Boolean isToken) {
        this.webView.loadUrl(url);
    }

    @Override
    public void backAction() {
        finish();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }
}
