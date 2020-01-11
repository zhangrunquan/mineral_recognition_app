package com.example.mine;

import android.widget.ArrayAdapter;

public class SpinnerData {
    public ArrayAdapter<CharSequence> mAdapter;
    public String mFieldName;
    public SpinnerData(ArrayAdapter<CharSequence> adapter, String fieldName){
        mAdapter=adapter;
        mFieldName=fieldName;
    }
}
