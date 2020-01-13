package com.example.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.constraintlayout.widget.ConstraintLayout;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static com.example.mine.MainActivity.REQUEST_IMAGE_CAPTURE;

public class ActivityCommon {

    public static final int ACTION_TAKE_PHOTO=1;
    public static final int ACTION_CHOOSE_PHOTO=2;

    public static void goToHomePage(Context context){
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static void goToTakePhoto(Context context){
        Intent intent = new Intent(context, P3Activity.class);
        intent.putExtra("action",ACTION_TAKE_PHOTO);
        context.startActivity(intent);
    }

    public static void goToExperiment(Context context){
        Intent intent = new Intent(context, P5Activity.class);
        context.startActivity(intent);
    }

    public static void goToChooseFile(Context context){
        Intent intent = new Intent(context, P3Activity.class);
        intent.putExtra("action",ACTION_CHOOSE_PHOTO);
        context.startActivity(intent);
    }

    public static void goToSomeKind(Context context){
        Intent intent = new Intent(context, P6Activity.class);
        context.startActivity(intent);
    }

    public static void goToInfoPage(Context context,String mineName){
        Intent intent = new Intent(context, P4Activity.class);
        intent.putExtra("mineName",mineName);
        context.startActivity(intent);
    }
}
