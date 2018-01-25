package com.example.lee.xinxinniannian.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.lee.xinxinniannian.R;

/**
 * Created by lee on 2018/1/16.
 */

public class WebActivty extends Activity {
    private ProgressBar mProgressBar;
    private WebView webView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        //去状态栏和标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();

    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        webView = (WebView) findViewById(R.id.webView);
        //进行加载网页的逻辑
        Intent intent = getIntent();
        final String url = intent.getStringExtra("urls");
        //支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        webView.setWebChromeClient(new WebViewClient());
        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient());
        webView.loadUrl(url);

    }

    public class WebViewClient extends WebChromeClient {
        //进度变化的监听
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(view.GONE);
            }
            mProgressBar.setProgress(newProgress*4);
        }
    }
}
