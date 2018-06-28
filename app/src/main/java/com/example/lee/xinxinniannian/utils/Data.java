package com.example.lee.xinxinniannian.utils;

import cn.bmob.v3.BmobObject;

/**
 * Created by lee on 2018/6/14.
 */

public class Data extends BmobObject {
    private String imagerUrl;//图片地址
    private String webUrl;//链接地址
    private String fenleil;//分类

    public Data() {

    }

    public Data( String imagerUrl, String webUrl, String fenleil) {
        this.imagerUrl = imagerUrl;
        this.webUrl = webUrl;
        this.fenleil = fenleil;
    }

    public String getImagerUrl() {
        return imagerUrl;
    }

    public void setImagerUrl(String imagerUrl) {
        this.imagerUrl = imagerUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getFenleil() {
        return fenleil;
    }

    public void setFenleil(String fenleil) {
        this.fenleil = fenleil;
    }
}
