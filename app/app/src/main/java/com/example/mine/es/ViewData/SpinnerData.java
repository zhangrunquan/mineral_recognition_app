package com.example.mine.es.ViewData;

import android.widget.ArrayAdapter;

public class SpinnerData extends ViewData {
    private ArrayAdapter<CharSequence> mAdapter;


    public SpinnerData(String mFieldName, int mDataType, ArrayAdapter<CharSequence> mAdapter) {
        super(mFieldName, mDataType);
        this.mAdapter = mAdapter;
    }

    public ArrayAdapter<CharSequence> getmAdapter() {
        return mAdapter;
    }
}
