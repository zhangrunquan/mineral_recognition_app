package com.example.mine;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_CODE_SELECT_FILE=2;
    static final String TMP_PHOTO = "tmp_photo.jpg";
    private ImageView picture;
    private Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picture = findViewById(R.id.pic);
        but=findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_event();
            }
        });
    }

    public void button_event(){
        Intent intent=new  Intent(MainActivity.this,EsActivity.class);
        startActivity(intent);
    }

    // 用户选择一张已有图片
    public void chooseExistedPicture(){
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select a picture")
                , REQUEST_CODE_SELECT_FILE);
    }

    // 调用相机拍照
    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 相机
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            saveBitmapToFile(imageBitmap, TMP_PHOTO, Bitmap.CompressFormat.JPEG, 100);
            picture.setImageBitmap(imageBitmap);
        }
        switch (requestCode){
            case REQUEST_CODE_SELECT_FILE:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();

                    // 判断图片MIME类型
                    ContentResolver cR = getContentResolver();
//                    MimeTypeMap mime = MimeTypeMap.getSingleton();
//                    String type = mime.getExtensionFromMimeType(cR.getType(uri)); # 得到如jpeg的具体格式
                    Log.e("MIME TYPE",cR.getType(uri) );
                    try {
                        AssetFileDescriptor afd = getContentResolver().openAssetFileDescriptor(uri, "r");
                        FileDescriptor fd = afd.getFileDescriptor();
                        try (BufferedReader br = new BufferedReader(new FileReader(fd))) {
                            Log.e( "onActivityResult: ", br.toString());
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            default:
                break;
        }

    }

    // 将bitmap保存到文件
    void saveBitmapToFile(Bitmap bitmap, String fileName, Bitmap.CompressFormat format, int quality) {
        try {
            FileOutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE);
            Boolean flag = bitmap.compress(format, quality, out);
            Log.e("saveBitmapToFile: ", flag.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
