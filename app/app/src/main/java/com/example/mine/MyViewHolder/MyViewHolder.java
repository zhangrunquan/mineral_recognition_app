package com.example.mine.MyViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.ViewData.ViewData;
import com.example.mine.es.ViewData.EsViewData;

abstract public class MyViewHolder extends RecyclerView.ViewHolder{

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    abstract public void bindDataToViews(ViewData viewData);
}
