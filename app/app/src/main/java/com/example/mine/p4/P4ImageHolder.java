package com.example.mine.p4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.mine.MyViewHolder.MyViewHolder;
import com.example.mine.R;
import com.example.mine.ViewData.ViewData;

import java.io.InputStream;

public class P4ImageHolder extends MyViewHolder {

    private ImageView imageView;

    public P4ImageHolder(@NonNull View itemView) {
        super(itemView);
        imageView= itemView.findViewById(R.id.p4Image);
    }

    @Override
    public void bindDataToViews(ViewData viewData) {
        P4ImageData data=(P4ImageData) viewData;

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
