package com.example.mine;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mine.network.MineTypeResponse;
import com.example.mine.network.RetrofitClientInstance;
import com.example.mine.network.UploadFileService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_CODE_SELECT_FILE = 2;
    static final String TMP_PHOTO = "tmp_photo.jpg";
    private ImageView picture;
    private Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picture = findViewById(R.id.pic);
        but = findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_event();
            }
        });
    }

    public void button_event() {
//        Intent intent=new  Intent(MainActivity.this, MineInfoActivity.class);
//        startActivity(intent);

        // 测试相机拍摄,显示,上传图片
        dispatchTakePictureIntent();

        // 测试图库选择图片,显示,上传
//        chooseExistedPicture();
    }

    // 用户选择一张已有图片
    public void chooseExistedPicture() {
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select a picture")
                , REQUEST_CODE_SELECT_FILE);
    }

    // 调用相机拍照
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SELECT_FILE: //选择图片
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    // 判断图片MIME类型
                    ContentResolver cR = getContentResolver();
//                    MimeTypeMap mime = MimeTypeMap.getSingleton();
//                    String type = mime.getExtensionFromMimeType(cR.getType(uri)); # 得到如jpeg的具体格式
                    Log.e("MIME TYPE", cR.getType(uri)); // TODO: 2020/1/12 可以添加对格式的过滤

                    // 显示图片
                    try {
                        Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        picture.setImageBitmap(bm);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String pictureString = null;
                    try {
                        InputStream is = getContentResolver().openInputStream(uri);
                        pictureString = inputStreamToString(is);  // 图片文件读入为base64编码的字符串


                        // 上传图片
                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse("image/jpg"),
                                        pictureString
                                );

                        MultipartBody.Part body =
                                MultipartBody.Part.createFormData("image", TMP_PHOTO, requestFile);

                        UploadFileService service = RetrofitClientInstance.getRetrofitInstance().create(UploadFileService.class);
                        Call<MineTypeResponse> call = service.uploadFile(body);

                        call.enqueue(new Callback<MineTypeResponse>() {
                            @Override
                            public void onResponse(Call<MineTypeResponse> call, Response<MineTypeResponse> response) {
                                String result = response.body().getType();
                                TextView textView = (TextView) findViewById(R.id.mainTextView);
                                textView.setText(result);
                            }

                            @Override
                            public void onFailure(Call<MineTypeResponse> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case REQUEST_IMAGE_CAPTURE: //相机
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    // bitmap保存为jpg格式的文件
                    saveBitmapToFile(imageBitmap, TMP_PHOTO, Bitmap.CompressFormat.JPEG, 100);
                    picture.setImageBitmap(imageBitmap);

                    String picture = null;

                    try {
                        picture = inputStreamToString(openFileInput(TMP_PHOTO));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    // 上传图片
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("image/jpg"), picture);

                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("image", TMP_PHOTO, requestFile);

                    UploadFileService service = RetrofitClientInstance.getRetrofitInstance().create(UploadFileService.class);
                    Call<MineTypeResponse> call = service.uploadFile(body);

                    call.enqueue(new Callback<MineTypeResponse>() {
                        @Override
                        public void onResponse(Call<MineTypeResponse> call, Response<MineTypeResponse> response) {
                            String result = response.body().getType();
                            TextView textView = (TextView) findViewById(R.id.mainTextView);
                            textView.setText(result);
                        }

                        @Override
                        public void onFailure(Call<MineTypeResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                break;
            default:
                break;
        }

    }

    // 将bitmap保存到文件
    void saveBitmapToFile(Bitmap bitmap, String fileName, Bitmap.CompressFormat format, int quality) {
        try {
            FileOutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE);
            Boolean flag = bitmap.compress(format, quality, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    String inputStreamToString(InputStream in) {
        String content = null;
        byte[] data = null;
        try {
            data = new byte[in.available()];
            in.read(data);
            in.close();
            content = new String(Base64.encode(data, Base64.DEFAULT));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}
