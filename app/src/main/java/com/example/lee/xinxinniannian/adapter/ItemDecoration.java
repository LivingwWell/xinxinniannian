package com.example.lee.xinxinniannian.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lee on 2018/6/9.
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private final int height;

    public ItemDecoration(int height) {
        this.height = height;
    }
    public void geteItemOffsete(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        outRect.bottom = height;
        outRect.left=height;
        outRect.top=height;
    }
}
