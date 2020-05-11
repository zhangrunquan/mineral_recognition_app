package com.example.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
//
//        Button butTakePhoto=findViewById(R.id.homeCameraButton);
//        butTakePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityCommon.goToTakePhoto(HomeActivity.this);
//            }
//        });
//
//        Button butChoosePhoto=findViewById(R.id.homeFileButton);
//        butChoosePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityCommon.goToChooseFile(HomeActivity.this);
//            }
//        });
//
//        Button butExperiment=findViewById(R.id.homeExperimentButton);
//        butExperiment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityCommon.goToExperiment(HomeActivity.this);
//            }
//        });
//
//        Button butSomeKind=findViewById(R.id.homeSomeKind);
//        butSomeKind.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityCommon.goToSomeKind(HomeActivity.this);
//            }
//        });

        final EditText editText=findViewById(R.id.homeEditText);

        Button butSearch=findViewById(R.id.homeSearch);
        butSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword=editText.getText().toString();
                ActivityCommon.goToInfoPage(HomeActivity.this,keyword);
            }
        });
        Button chemical_button_1=findViewById(R.id.chemical_button_1);
        chemical_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,P6Activity.class);
                intent.putExtra("chemistry","含盐类矿物");
                startActivity(intent);
            }
        });
        Button chemical_button_2=findViewById(R.id.chemical_button_2);
        chemical_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,P6Activity.class);
                intent.putExtra("chemistry","硫化物类矿物");
                startActivity(intent);
            }
        });
        Button chemical_button_3=findViewById(R.id.chemical_button_3);
        chemical_button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,P6Activity.class);
                intent.putExtra("chemistry","自然元素矿物");
                startActivity(intent);
            }
        });
        Button chemical_button_4=findViewById(R.id.chemical_button_4);
        chemical_button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,P6Activity.class);
                intent.putExtra("chemistry","氧化物与氢氧化物类矿物");
                startActivity(intent);
            }
        });
    }
}
