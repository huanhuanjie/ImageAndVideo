package com.example.imageandvideo.entity;

import android.graphics.Bitmap;

public class GridViewItem {

    private String imgName;
    private Bitmap imgId;

    public GridViewItem(String imgName, Bitmap imgId) {
        this.imgName = imgName;
        this.imgId = imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Bitmap getImgId() {
        return imgId;
    }

    public void setImgId(Bitmap imgId) {
        this.imgId = imgId;
    }
}
