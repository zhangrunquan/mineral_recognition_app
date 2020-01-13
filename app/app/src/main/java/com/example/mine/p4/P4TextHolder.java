package com.example.mine.p4;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mine.MyViewHolder.MyViewHolder;
import com.example.mine.R;
import com.example.mine.ViewData.ViewData;

public class P4TextHolder extends MyViewHolder {

    private TextView textView;

    public P4TextHolder(@NonNull View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.p4Text);
    }

    @Override
    public void bindDataToViews(ViewData viewData) {
        P4TextData data=(P4TextData) viewData;

        textView.setText(data.getTextMap().get("mineName"));
    }
}
