package com.example.mine.p4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.MyAdapter.ViewDataAdapter;
import com.example.mine.R;
import com.example.mine.ViewData.ViewData;

import java.util.List;

import static com.example.mine.ViewData.DataType.P4_IMAGE_RECYCLER_TYPE;
import static com.example.mine.ViewData.DataType.P4_TEXT_TYPE;

public class P4MainRecyclerAdapter extends ViewDataAdapter {

    public P4MainRecyclerAdapter(List<ViewData> list) {
        super(list);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case P4_IMAGE_RECYCLER_TYPE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.p4_image_recycler, parent, false);
                return new P4ImageRecylerHolder(view);
            case P4_TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.p4_text, parent, false);
                return new P4TextHolder(view);
            default:
                throw new RuntimeException("Invalid view type!");
        }
    }

}
