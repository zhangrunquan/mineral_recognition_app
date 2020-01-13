package com.example.mine.MyAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.MyViewHolder.MyViewHolder;
import com.example.mine.ViewData.ViewData;

import java.util.List;

public abstract class ViewDataAdapter extends RecyclerView.Adapter {
    protected List<ViewData> mList;

    public ViewDataAdapter(List<ViewData> list) {
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        ViewData data = (ViewData) mList.get(position);
        return data.getmDataType();
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewData data = mList.get(position);
        ((MyViewHolder) holder).bindDataToViews(data);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
