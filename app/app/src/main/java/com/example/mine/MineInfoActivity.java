package com.example.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mine.mineinfo.MineInfo;
import com.example.mine.mineinfo.MineInfoManager;
import com.example.mine.network.MineTypeResponse;
import com.example.mine.network.RetrofitClientInstance;
import com.example.mine.network.UploadFileService;
import com.example.mine.network.UploadStringService;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MineInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mineinfo);

        BufferedReader reader=null;

        try{
            reader=new BufferedReader(
                    new InputStreamReader(getAssets().open("mine_data.json"))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson=new Gson();

        // 加载矿物信息
        MineInfoManager manager=new MineInfoManager(this);
        MineInfo info=manager.getMineInfo("crystal");

        Log.e("1",info.images.toString() );

        //显示图片
        String name=info.images.get(0);
        Bitmap bitmap=null;
        Log.e("name",name );
        try{
            InputStream in=getAssets().open(name);
            bitmap=BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView imageView=findViewById(R.id.mineInfoImageView);
        imageView.setImageBitmap(bitmap);

        // 上传json,显示返回结果
        String json="{\"content\":\"bulabula\"}";

        final TextView textView=(TextView) findViewById(R.id.mineInfoTextView);

        UploadStringService service = RetrofitClientInstance.getRetrofitInstance().create(UploadStringService.class);
        Call<MineTypeResponse> call=service.uploadString(json);
        call.enqueue(new Callback<MineTypeResponse>() {
            @Override
            public void onResponse(Call<MineTypeResponse> call, Response<MineTypeResponse> response) {
                String result=response.body().getType();
                textView.setText(result);
            }

            @Override
            public void onFailure(Call<MineTypeResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });



    }

}
