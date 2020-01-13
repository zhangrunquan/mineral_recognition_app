package com.example.mine.p6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mine.MyViewHolder.MyViewHolder;
import com.example.mine.R;
import com.example.mine.ViewData.ViewData;
import com.example.mine.es.ViewData.EsViewData;

import java.io.InputStream;

public class P6CardViewHolder extends MyViewHolder {

    private TextView mTextView;
    private ImageView imageView;

    public P6CardViewHolder(@NonNull View view) {
        super(view);
        mTextView = (TextView) view.findViewById(R.id.p6CardText);
        imageView=view.findViewById(R.id.p6CardImage);
    }

    @Override
    public void bindDataToViews(ViewData viewData) {
        P6CardViewData data=(P6CardViewData) viewData;

        String text=data.getTextMap().get("englishName");
        mTextView.setText(text);

        String imagePath=data.getImagePath();

        Bitmap bitmap = null;
        try {
            InputStream in = data.getContext().getAssets().open(imagePath);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(bitmap);
    }
}
