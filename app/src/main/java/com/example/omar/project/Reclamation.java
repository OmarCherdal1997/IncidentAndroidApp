package com.example.omar.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Reclamation {
    private Bitmap bitmap;
    private String detail;
    private String type;

    public Reclamation(Bitmap bitmap, String detail, String type) {
        this.bitmap = bitmap;
        this.detail = detail;
        this.type = type;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getDetail() {
        return detail;
    }

    public String getType() {
        return type;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setType(String type) {
        this.type = type;
    }
}
