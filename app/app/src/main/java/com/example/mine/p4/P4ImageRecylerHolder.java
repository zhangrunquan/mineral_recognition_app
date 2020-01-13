package com.example.mine.p4;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.MyViewHolder.MyViewHolder;
import com.example.mine.R;
import com.example.mine.ViewData.ViewData;

public class P4ImageRecylerHolder extends MyViewHolder {

    private RecyclerView imageRecycler;

    public P4ImageRecylerHolder(@NonNull View itemView) {
        super(itemView);
        imageRecycler= itemView.findViewById(R.id.p4ImageRecycler);
    }

    @Override
    public void bindDataToViews(ViewData viewData) {
        P4ImageRecyclerData data=(P4ImageRecyclerData) viewData;

        P4ImageRecyclerAdapter adapter=data.getAdapter();
        Context context=data.getContext();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        imageRecycler.setLayoutManager(layoutManager);
        imageRecycler.setAdapter(adapter);

    }
}
