package com.example.mine.es.ViewData;

public class EsViewData {
    private String mFieldName;
    private int mDataType;


    public EsViewData(String mFieldName, int mDataType) {
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
