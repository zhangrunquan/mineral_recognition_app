package com.example.mine.p6;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.MyViewHolder.MyViewHolder;
import com.example.mine.R;
import com.example.mine.ViewData.ViewData;
import com.example.mine.es.ViewData.EsViewData;

import java.util.List;

import static com.example.mine.ViewData.DataType.P6_CARD_VIEW_DATA;

public class P6RecyclerAdapter  extends RecyclerView.Adapter {
    private List<ViewData> mList;

    public P6RecyclerAdapter(List<ViewData> list) {
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
            case P6_CARD_VIEW_DATA:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.p6_card, parent, false);
                return new P6CardViewHolder(view);

            default:
                throw new RuntimeException("Invalid view type!");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("P6RecyclerAdapter", String.format("whatistheposition%d", position));
        ViewData data = mList.get(position);
        switch (holder.getItemViewType()) {
            case P6_CARD_VIEW_DATA:
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
