package com.example.lee.xinxinniannian.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lee on 2018/1/11.
 */

public class MirrorTransformation implements Transformation {
    private float opacity = 1f;

    private float centreY = 0.5f;

    private float distance;

    private float gap;

    public MirrorTransformation() {
    }

    public MirrorTransformation setDistance(float distance) {
        this.distance = distance;
        return this;
    }

    public float getDistance() {
        return distance;
    }

    public MirrorTransformation setGap(float gap) {
        this.gap = gap;
        return this;
    }

    public float getGap() {
        return gap;
    }

    /**
     * 设置倒影的不透明性
     *
     */
    public MirrorTransformation setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }

    /**
     * 获取倒影的不透明性。
     *
     */
    public float getOpacity() {
        return opacity;
    }

    public MirrorTransformation setCentreY(float centreY) {
        this.centreY = centreY;
        return this;
    }

    public float getCentreY() {
        return centreY;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        int h = (int) (centreY * height);
        int d = (int) (gap*height);

        Canvas canvas = new Canvas();
        Paint paint = new Paint();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);

        // 设置背景颜色
      //  canvas.drawColor(Color.BLACK);

        // 画布上的区域
        RectF clipSrcF = new RectF(-44, 189, width-44, height+189);
        RectF clipDestF = new RectF(-44, 189, width-44, h+189);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(clipSrcF, clipDestF, Matrix.ScaleToFit.CENTER);
        canvas.scale(1.85f,1.85f,200,200);
        canvas.drawBitmap(source, matrix, null);

        // 画布上的倒影
        clipSrcF = new RectF(-44, 189, width-44, height+189);
        clipDestF = new RectF(-44,  h+d+189, width-44, height+d+189);
        matrix= new Matrix();
        matrix.setRectToRect(clipSrcF, clipDestF, Matrix.ScaleToFit.CENTER);
        matrix.preTranslate(0, height);
        matrix.preScale(1, -1);
        canvas.clipRect(0,0,width,height);
        canvas.drawBitmap(source ,matrix, paint);

        // 回收
       source.recycle();

        // 倒影的透明度
        RectF clipGradientF = new RectF(0, 0, width, height);
        matrix.mapRect(clipGradientF);
        paint.setShader(new LinearGradient(0, h, 0, height, Color.argb(60, 0, 0, 0),
               Color.argb(0, 225, 225, 225),
               Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(clipGradientF, paint);
        return bitmap;
    }

    @Override
    public String key() {
        return null;
    }
}
