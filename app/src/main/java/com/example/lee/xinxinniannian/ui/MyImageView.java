package com.example.lee.xinxinniannian.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lee on 2018/6/10.
 */


@SuppressLint("AppCompatCustomView")
public class MyImageView extends ImageView {
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

 public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
     super.onMeasure(widthMeasureSpec, widthMeasureSpec);
 }

}
