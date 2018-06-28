package com.example.lee.xinxinniannian.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.lee.xinxinniannian.MainActivity;
import com.example.lee.xinxinniannian.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by lee on 2018/1/16.
 */

public class WebActivty extends Activity {
    private ProgressBar mProgressBar;
    private WebView webView;
    SmartRefreshLayout smartRefreshLayout;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        //去状态栏和标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        context = this;
        ClassicsHeader.REFRESH_HEADER_PULLING = "下拉回到首页";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "释放回到首页";
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smartRefreshLayout);
        //下拉回到首页
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Intent intent = new Intent();
                intent.setClass(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //back键返回上个html
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
            mProgressBar.setProgress(newProgress * 4);
        }
    }
}
