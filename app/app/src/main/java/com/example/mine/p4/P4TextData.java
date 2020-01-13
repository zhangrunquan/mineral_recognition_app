package com.example.mine.p4;

import com.example.mine.ViewData.ViewData;

import java.util.Map;

public class P4TextData extends ViewData {

    private Map<String,String> textMap;

    public P4TextData(int mDataType, Map<String, String> textMap) {
        super(mDataType);
        this.textMap = textMap;
    }

    public Map<String, String> getTextMap() {
        return textMap;
    }
}
