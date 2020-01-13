package com.example.mine.p4;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.ViewData.ViewData;

public class P4ImageRecyclerData extends ViewData {

    private P4ImageRecyclerAdapter adapter;
    private Context context;

    public P4ImageRecyclerData(int mDataType, P4ImageRecyclerAdapter adapter, Context context) {
        super(mDataType);

        this.adapter = adapter;
        this.context = context;
    }

    public P4ImageRecyclerAdapter getAdapter() {
        return adapter;
    }

    public Context getContext() {
        return context;
    }


}
