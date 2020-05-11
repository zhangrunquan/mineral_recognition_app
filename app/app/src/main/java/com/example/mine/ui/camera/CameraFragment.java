package com.example.mine.ui.camera;
import androidx.appcompat.app.AppCompatActivity;

//import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mine.ActivityCommon;
import com.example.mine.P3Activity;
import com.example.mine.P5Activity;
import com.example.mine.R;
import com.example.mine.ViewData.DataType;
import com.example.mine.mineinfo.MineInfo;
import com.example.mine.mineinfo.MineInfoManager;
import com.example.mine.network.MineTypeResponse;
import com.example.mine.network.RetrofitClientInstance;
import com.example.mine.network.UploadFileService;
import com.example.mine.p4.P4ImageData;
import com.example.mine.ui.experiment.ExperimentFragment;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class CameraFragment extends Fragment {
//
//    private CameraModel cameraViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        cameraViewModel =
//                ViewModelProviders.of(this).get(CameraModel.class);
//        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        cameraViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
//}
public class CameraFragment extends Fragment {

    private CameraModel cameraViewModel;
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_SELECT_FILE = 2;
    private static final int RESULT_OK = -1;
//    private static final int REQUEST_CODE_SELECT_FILE = 2;
    static final String TMP_PHOTO = "tmp_photo.jpg";

    private ImageView userImage;
    private ImageView infoImage;
    private ImageView infoImage2;
    private TextView mineTypeText;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        cameraViewModel =
//                ViewModelProviders.of(this).get(CameraModel.class);
//        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        cameraViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.activity_p3, container, false);

//        Button butBack = root.findViewById(R.id.p3Back);
//        butBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });

        Button butTakePhoto = root.findViewById(R.id.p3ButtonTakeMorePhoto);
        butTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        Button butExperiment = root.findViewById(R.id.p3ButtonExperiment);
        butExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityCommon.goToExperiment(CameraFragment.this.getActivity());
                changeToExperimentFragment();
            }
        });



        // 根据收到的信息决定行为
        int actionCode = getActivity().getIntent().getIntExtra("action", ActivityCommon.ACTION_TAKE_PHOTO);
        Log.d("CameraFragment", String.format("whatistheactioncode%d", actionCode
                ));
        switch (actionCode) {
            case ActivityCommon.ACTION_TAKE_PHOTO:
                butTakePhoto.callOnClick();
                break;
            case ActivityCommon.ACTION_CHOOSE_PHOTO:
                choosePhoto();
                break;
            default:
//                butTakePhoto.callOnClick();
//                break;
                throw new RuntimeException("Illegal action code");
        }

        userImage = root.findViewById(R.id.p3UserImage);
        infoImage = root.findViewById(R.id.p3InfoImage);
        infoImage2 = root.findViewById(R.id.p3InfoImage2);
        mineTypeText = root.findViewById(R.id.p3MineType);

        Button butInfo = root.findViewById(R.id.p3ButtonMoreInfo);
        butInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCommon.goToInfoPage(CameraFragment.this.getActivity(), (String)mineTypeText.getText());
            }
        });
        return root;
    }
    private void changeToExperimentFragment(){
        //如果是用的v4的包，则用getActivity().getSuppoutFragmentManager();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        //注意v4包的配套使用
        ExperimentFragment fragment = new ExperimentFragment();
        fm.beginTransaction().replace(CameraFragment.this.getId(),fragment).addToBackStack(null).commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        picture = inputStreamToString(getActivity().openFileInput(TMP_PHOTO));
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
                            MineInfoManager manager=new MineInfoManager(getActivity());
                            MineInfo mineInfo=manager.getMineInfo((String)mineTypeText.getText());
                            if (mineInfo.images.size()!=0){
                                String path=mineInfo.images.get(0);
                                String herepath="...."+path;
                                Log.d("Camera",path);
                                InputStream in = null;
                                try {
                                    in = getActivity().getAssets().open(path);
                                    //InputStream in = new P4ImageData(DataType.P4_IMAGE_TYPE,path,getActivity()).getContext().getAssets().open(path);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Bitmap bitmap = BitmapFactory.decodeStream(in);
//                                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
//                                        .openInputStream(new Uri(new Path(path)));
                                infoImage.setImageBitmap(bitmap);
//                                infoImage.setImageURI(Uri.fromFile(new File(herepath)));
                            }
                            infoImage2.setImageResource(R.drawable.circle2);
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
                    ContentResolver cR = getActivity().getContentResolver();
//                    MimeTypeMap mime = MimeTypeMap.getSingleton();
//                    String type = mime.getExtensionFromMimeType(cR.getType(uri)); # 得到如jpeg的具体格式
                    Log.e("MIME TYPE", cR.getType(uri)); // TODO: 2020/1/12 可以添加对格式的过滤

                    // 显示图片
                    try {
                        Bitmap bm = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), uri);
                        userImage.setImageBitmap(bm);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String pictureString = null;
                    try {
                        InputStream is = getActivity().getContentResolver().openInputStream(uri);
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
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
        }
    }

    // 将bitmap保存到文件
    void saveBitmapToFile(Bitmap bitmap, String fileName, Bitmap.CompressFormat format, int quality) {
        try {
            FileOutputStream out = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
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