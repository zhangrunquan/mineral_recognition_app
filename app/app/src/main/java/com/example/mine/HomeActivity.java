package com.example.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        final EditText editText=findViewById(R.id.homeEditText);

        Button butSearch=findViewById(R.id.homeSearch);
        butSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword=editText.getText().toString();
                ActivityCommon.goToInfoPage(HomeActivity.this,keyword);
            }
        });

    }
}
