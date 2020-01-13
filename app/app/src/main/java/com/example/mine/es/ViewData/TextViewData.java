package com.example.mine.es.ViewData;

public class TextViewData extends EsViewData {
    private String mText;
    public TextViewData(String mFieldName, int mDataType, String mText) {
        super(mFieldName, mDataType);
        this.mText = mText;
    }

    public String getmText() {
        return mText;
    }
}
