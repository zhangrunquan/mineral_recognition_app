package com.example.mine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mine.mineinfo.MineInfo;
import com.example.mine.mineinfo.MineInfoManager;
import com.example.mine.network.MineTypeResponse;
import com.example.mine.network.RetrofitClientInstance;
import com.example.mine.network.UploadStringService;
import com.google.gson.Gson;

import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MineInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mineinfo);

        Gson gson = new Gson();

        // 获取矿物信息示例
        MineInfoManager manager = new MineInfoManager(this);
        MineInfo info = manager.getMineInfo("crystal");


        //显示矿物信息图片示例
        String name = info.images.get(0);
        Bitmap bitmap = null;
        try {
            InputStream in = getAssets().open(name);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView imageView = findViewById(R.id.mineInfoImageView);
        imageView.setImageBitmap(bitmap);

        // 上传json,显示返回结果
        String json = "{\"content\":\"bulabula\"}";

        final TextView textView = (TextView) findViewById(R.id.mineInfoTextView);

        UploadStringService service = RetrofitClientInstance.getRetrofitInstance().create(UploadStringService.class);
        Call<MineTypeResponse> call = service.uploadString(json);

        call.enqueue(new Callback<MineTypeResponse>() {
            @Override
            public void onResponse(Call<MineTypeResponse> call, Response<MineTypeResponse> response) {
                String result = response.body().getType();
                textView.setText(result);
            }

            @Override
            public void onFailure(Call<MineTypeResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
