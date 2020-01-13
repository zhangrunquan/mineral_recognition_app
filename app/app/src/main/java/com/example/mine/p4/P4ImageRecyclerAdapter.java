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
import static com.example.mine.ViewData.DataType.P4_IMAGE_TYPE;

public class P4ImageRecyclerAdapter extends ViewDataAdapter {

    public P4ImageRecyclerAdapter(List<ViewData> list) {
        super(list);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case P4_IMAGE_TYPE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.p4_image, parent, false);
                return new P4ImageHolder(view);

            default:
                throw new RuntimeException("Invalid view type!");
        }
    }

}
