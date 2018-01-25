package com.example.lee.xinxinniannian.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.lee.xinxinniannian.MainActivity;
import com.example.lee.xinxinniannian.R;

/**
 * Created by lee on 2017/7/18.
 */

public class SplashAvtivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        handleWelcome();
    }
    private void handleWelcome(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //打开主页并关闭欢迎页面
                Intent intent = new Intent(SplashAvtivity.this, MainActivity.class);
                startActivity(intent);
                SplashAvtivity.this.finish();
            }
        }, 2000);
    }
    }

