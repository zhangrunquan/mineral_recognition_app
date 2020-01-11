package com.example.mine.es;

import android.widget.ArrayAdapter;

import com.example.mine.SpinnerData;

public class FromToSpinnerData extends SpinnerData {
    private Boolean mIsFrom;

    public FromToSpinnerData(String fieldName,ArrayAdapter<CharSequence> adapter ,Boolean isFrom){
        super(adapter,fieldName);
        mIsFrom=isFrom;
    }

    public Boolean isFrom(){
        return mIsFrom;
    }
}
