package com.example.mine.p4;

import android.content.Context;

import com.example.mine.ViewData.ViewData;

public class P4ImageData extends ViewData {

    private String imagePath;
    private Context context;

    public P4ImageData(int mDataType, String imagePath, Context context) {
        super(mDataType);
        this.imagePath = imagePath;
        this.context = context;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Context getContext() {
        return context;
    }
}
