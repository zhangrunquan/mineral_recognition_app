package com.example.mine.es.ViewData;

import android.widget.ArrayAdapter;

public class FromToSpinnerData extends SpinnerData {
    private Boolean mIsFrom;

    public FromToSpinnerData(String mFieldName, int mDataType, ArrayAdapter<CharSequence> mAdapter, Boolean mIsFrom) {
        super(mFieldName, mDataType, mAdapter);
        this.mIsFrom = mIsFrom;
    }

    public Boolean isFrom() {
        return mIsFrom;
    }
}
