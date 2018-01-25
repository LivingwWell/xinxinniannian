package com.example.lee.xinxinniannian.utils;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lee on 2018/1/9.
 */

public class SHOP extends BmobObject {
    private String name;
    private String momey;
    private String fenlei;
    private BmobFile pic;
    private String url;
    private int id;
    private String dizhi;



    public SHOP(){

    }

    public SHOP(int id,String name, String momey, String fenlei, BmobFile pic, String url,String dizhi) {
        this.id=id;
        this.name = name;
        this.momey = momey;
        this.fenlei = fenlei;
        this.pic = pic;
        this.url = url;
        this.dizhi=dizhi;
    }
    public String getDizhi() {
        return dizhi;
    }

    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMomey() {
        return momey;
    }

    public void setMomey(String momey) {
        this.momey = momey;
    }

    public String getFenlei() {
        return fenlei;
    }

    public void setFenlei(String fenlei) {
        this.fenlei = fenlei;
    }

    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "\""+url+"\"";
    }
}
