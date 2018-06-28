package com.example.lee.xinxinniannian.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2018/6/20.
 */

public class StarsDao {
    private static Stars stars;

    public StarsDao(Context context) {
        stars = new Stars(context);
    }
//添加
    public long add(String imagerurl, String weburl, String fenlei) {
        SQLiteDatabase db = stars.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("imagerUrl", imagerurl);
        values.put("webUrl", weburl);
        values.put("fenlei", fenlei);

        long rowid = db.insert("StarDB", null, values);
        db.close();
        return rowid;
    }

     public static List<Data> alt(){
        List<Data>satrlist=new ArrayList<>();
    SQLiteDatabase sd=stars.getReadableDatabase();
    Cursor cursor=sd.query("StarDB",null,null,null,null,null,null);
    while (cursor.moveToNext()){
        String imagerurl=cursor.getString(cursor.getColumnIndex("imagerUrl"));
        String weburl=cursor.getString(cursor.getColumnIndex("webUrl"));
        String fenlei=cursor.getString(cursor.getColumnIndex("fenlei"));
        Data data=new Data(imagerurl,weburl,fenlei);
        satrlist.add(data);
    }
    sd.close();
    cursor.close();
       return satrlist;
}

}
