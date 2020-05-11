package com.example.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mine.network.MineTypeResponse;
import com.example.mine.network.RetrofitClientInstance;
import com.example.mine.network.UploadFileService;
import com.example.mine.ui.camera.CameraFragment;
import com.example.mine.ui.experiment.ExperimentFragment;

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

public class P3Activity extends AppCompatActivity {
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_SELECT_FILE = 2;

    static final String TMP_PHOTO = "tmp_photo.jpg";

    private ImageView userImage;
    private ImageView infoImage;

    private TextView mineTypeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p3);
//
//        Button butBack = findViewById(R.id.p3Back);
//        butBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        Button butTakePhoto = findViewById(R.id.p3ButtonTakeMorePhoto);
        butTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        Button butExperiment = findViewById(R.id.p3ButtonExperiment);
        butExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityCommon.goToExperiment(P3Activity.this);
                changeToExperimentFragment();
            }
        });

        // 根据收到的信息决定行为
        int actionCode = getIntent().getIntExtra("action", -1);

        switch (actionCode) {
            case ActivityCommon.ACTION_TAKE_PHOTO:
                butTakePhoto.callOnClick();
                break;
            case ActivityCommon.ACTION_CHOOSE_PHOTO:
                choosePhoto();
                break;
            default:
                throw new RuntimeException("Illegal action code");
        }

        userImage = findViewById(R.id.p3UserImage);
        infoImage = findViewById(R.id.p3InfoImage);
        Resources resources = getBaseContext().getResources();
        Drawable imageDrawable = resources.getDrawable(R.drawable.circle2); //图片在drawable文件夹下
        infoImage.setBackgroundDrawable(imageDrawable);
        mineTypeText = findViewById(R.id.p3MineType);
    }
    private void changeToExperimentFragment(){
        //如果是用的v4的包，则用getActivity().getSuppoutFragmentManager();
        FragmentManager fm = getSupportFragmentManager();
        //注意v4包的配套使用
        ExperimentFragment fragment = new ExperimentFragment();
        fm.beginTransaction().replace(this.getTaskId(),fragment).addToBackStack(null).commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA: //相机
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    // 显示图片
                    userImage.setImageBitmap(imageBitmap);


                    // bitmap保存为jpg格式的文件
                    saveBitmapToFile(imageBitmap, TMP_PHOTO, Bitmap.CompressFormat.JPEG, 100);

                    // 将文件读入为base64编码的字符串
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
//                            TextView textView = (TextView) findViewById(R.id.mainTextView);
                            mineTypeText.setText(result);
                        }

                        @Override
                        public void onFailure(Call<MineTypeResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                break;
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
                        userImage.setImageBitmap(bm);
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
                                mineTypeText.setText(result);
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
            default:
                break;
        }
    }

    // 调用相机拍照
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
        }
    }

    // 将bitmap保存到文件
    void saveBitmapToFile(Bitmap bitmap, String fileName, Bitmap.CompressFormat format, int quality) {
        try {
            FileOutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE);
            Boolean flag = bitmap.compress(format, quality, out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 用户选择一张已有图片
    public void choosePhoto() {
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select a picture")
                , REQUEST_CODE_SELECT_FILE);
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
