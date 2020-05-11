package com.example.mine.es.ViewData;

public class ButtonData extends EsViewData {
    private String mText;
    private EsViewData esViewData;
    public ButtonData(EsViewData esViewData,String mFieldName, int mDataType, String mText) {
        super(mFieldName,mDataType);
        this.mText = mText;
        this.esViewData=esViewData;
    }

    public String getmText() {
        return mText;
    }

    public EsViewData getesViewData() {
        return this.esViewData;
    }
}
