package com.example.mine.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mine.ActivityCommon;
import com.example.mine.HomeActivity;
import com.example.mine.P6Activity;
import com.example.mine.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;


//
//        super.onCreate(savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_home, container,false);
////        setContentView(R.layout.activity_home);
//
//        Button butTakePhoto=view.findViewById(R.id.homeCameraButton);
//        butTakePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityCommon.goToTakePhoto(HomeFragment.this.getActivity());
//            }
//        });
//
//        Button butChoosePhoto=view.findViewById(R.id.homeFileButton);
//        butChoosePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityCommon.goToChooseFile(HomeFragment.this.getActivity());
//            }
//        });
//
//        Button butExperiment=view.findViewById(R.id.homeExperimentButton);
//        butExperiment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityCommon.goToExperiment(HomeFragment.this.getActivity());
//            }
//        });
//
//        Button butSomeKind=view.findViewById(R.id.homeSomeKind);
//        butSomeKind.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityCommon.goToSomeKind(HomeFragment.this.getActivity());
//            }
//        });
//
//        final EditText editText=view.findViewById(R.id.homeEditText);
//
//        Button butSearch=view.findViewById(R.id.homeSearch);
//        butSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String keyword=editText.getText().toString();
//                ActivityCommon.goToInfoPage(HomeFragment.this.getActivity(),keyword);
//            }
//        });
//        return view;
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        final EditText editText=view.findViewById(R.id.homeEditText);

        Button butSearch=view.findViewById(R.id.homeSearch);
        butSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword=editText.getText().toString();
                Log.d("HomeFragment", String.format("whatiskeyword%s", keyword));
                ActivityCommon.goToInfoPage(HomeFragment.this.getActivity(),keyword);
            }
        });
        Button chemical_button_1=view.findViewById(R.id.chemical_button_1);
        chemical_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeFragment.this.getActivity(), P6Activity.class);
                intent.putExtra("chemistry","含盐类矿物");
                startActivity(intent);
            }
        });
        Button chemical_button_2=view.findViewById(R.id.chemical_button_2);
        chemical_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeFragment.this.getActivity(),P6Activity.class);
                intent.putExtra("chemistry","硫化物类矿物");
                startActivity(intent);
            }
        });
        Button chemical_button_3=view.findViewById(R.id.chemical_button_3);
        chemical_button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeFragment.this.getActivity(),P6Activity.class);
                intent.putExtra("chemistry","自然元素矿物");
                startActivity(intent);
            }
        });
        Button chemical_button_4=view.findViewById(R.id.chemical_button_4);
        chemical_button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeFragment.this.getActivity(),P6Activity.class);
                intent.putExtra("chemistry","氧化物与氢氧化物类矿物");
                startActivity(intent);
            }
        });
        return view;
    }
}