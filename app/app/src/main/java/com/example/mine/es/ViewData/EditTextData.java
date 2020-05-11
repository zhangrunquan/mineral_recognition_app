package com.example.mine.es.ViewData;

public class EditTextData extends EsViewData {
    private String hint;
    public EditTextData(String mFieldName, int mDataType,String hint) {
        super(mFieldName, mDataType);
        this.hint=hint;
    }
    public String gethint() {
        return hint;
    }
}
