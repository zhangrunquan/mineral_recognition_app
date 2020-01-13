package com.example.mine.p6;

import android.content.Context;

import com.example.mine.ViewData.ViewData;

import java.util.Map;

public class P6CardViewData extends ViewData {
    private String imagePath;
    private Map<String,String> textMap;
    private Context context;

    public P6CardViewData(int mDataType, String imagePath, Map<String, String> textMap, Context context) {
        super(mDataType);
        this.imagePath = imagePath;
        this.textMap = textMap;
        this.context = context;
    }


    public String getImagePath() {
        return imagePath;
    }

    public Map<String, String> getTextMap() {
        return textMap;
    }

    public Context getContext() {
        return context;
    }
}
