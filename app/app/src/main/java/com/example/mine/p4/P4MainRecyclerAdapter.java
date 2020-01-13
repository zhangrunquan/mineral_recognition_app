package com.example.mine.p4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.MyViewHolder.MyViewHolder;
import com.example.mine.R;
import com.example.mine.ViewData.ViewData;
import com.example.mine.p6.P6CardViewHolder;

import java.util.List;

import static com.example.mine.ViewData.DataType.P4_IMAGE_RECYCLER_TYPE;
import static com.example.mine.ViewData.DataType.P6_CARD_VIEW_DATA;

public class P4MainRecyclerAdapter extends RecyclerView.Adapter{
    private List<ViewData> mList;

    public P4MainRecyclerAdapter(List<ViewData> list) {
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        ViewData data = (ViewData) mList.get(position);
        return data.getmDataType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case P4_IMAGE_RECYCLER_TYPE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.p4_image_recycler, parent, false);
                return new P4ImageRecylerHolder(view);

            default:
                throw new RuntimeException("Invalid view type!");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewData data = mList.get(position);
        switch (holder.getItemViewType()) {
            case P4_IMAGE_RECYCLER_TYPE:
                ((MyViewHolder) holder).bindDataToViews(data);
                break;
            default:
                throw new RuntimeException("Invalid view type!");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
