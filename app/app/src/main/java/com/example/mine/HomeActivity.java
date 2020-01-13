package com.example.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button butTakePhoto=findViewById(R.id.homeCameraButton);
        butTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCommon.goToTakePhoto(HomeActivity.this);
            }
        });

        Button butChoosePhoto=findViewById(R.id.homeFileButton);
        butChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCommon.goToChooseFile(HomeActivity.this);
            }
        });

        Button butExperiment=findViewById(R.id.homeExperimentButton);
        butExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCommon.goToExperiment(HomeActivity.this);
            }
        });

        Button butSomeKind=findViewById(R.id.homeSomeKind);
        butSomeKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCommon.goToSomeKind(HomeActivity.this);
            }
        });
    }
}
