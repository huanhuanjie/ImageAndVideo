package com.example.imageandvideo.entity;

import android.graphics.Bitmap;
import android.os.Binder;

public class BitmapBinder extends Binder {
    private Bitmap bitmap;

    public BitmapBinder(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    Bitmap getBitmap() {
        return bitmap;
    }
}
