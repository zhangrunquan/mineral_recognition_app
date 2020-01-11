package com.example.mine.es.ViewData;

public class ViewData {
    private String mFieldName;
    private int mDataType;


    ViewData(String mFieldName, int mDataType) {
        this.mFieldName = mFieldName;
        this.mDataType = mDataType;
    }

    public String getmFieldName() {
        return mFieldName;
    }

    public int getmDataType() {
        return mDataType;
    }
}
