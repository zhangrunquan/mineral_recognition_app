package com.example.mine.es.ViewData;

public class CheckBoxData extends EsViewData {
    private String mText;


    public CheckBoxData(String mFieldName, int mDataType, String mText) {
        super(mFieldName, mDataType);

        this.mText = mText;
    }

    public String getmText() {
        return mText;
    }


}
