package com.example.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mine.ViewData.DataType;
import com.example.mine.ViewData.ViewData;
import com.example.mine.mineinfo.MineInfo;
import com.example.mine.mineinfo.MineInfoManager;
import com.example.mine.p4.P4ImageData;
import com.example.mine.p4.P4ImageRecyclerAdapter;
import com.example.mine.p4.P4ImageRecyclerData;
import com.example.mine.p4.P4MainRecyclerAdapter;
import com.example.mine.p4.P4TextData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P4Activity extends AppCompatActivity {

    private RecyclerView mainRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p4);

        String mineName = getIntent().getStringExtra("mineName");// 英文名

        // 构造imageRecycler的Adapter
        MineInfoManager manager=new MineInfoManager(this);
        MineInfo mineInfo=manager.getMineInfo(mineName);

        List<ViewData> imageDataList=new ArrayList<>();

        for(String imagePath: mineInfo.images){
            imageDataList.add(new P4ImageData(DataType.P4_IMAGE_TYPE,imagePath,this));
        }

        P4ImageRecyclerAdapter adapter=new P4ImageRecyclerAdapter(imageDataList);

        P4ImageRecyclerData imageRecyclerData=new P4ImageRecyclerData(DataType.P4_IMAGE_RECYCLER_TYPE,adapter,this);

        // 构造MainRecycler的adapter
        List<ViewData> dataList=new ArrayList<>();

        // 添加textData
        Map<String,String> textMap=new HashMap<>();
        textMap.put("mineName",mineName);
        dataList.add(new P4TextData(DataType.P4_TEXT_TYPE,textMap));

        dataList.add(imageRecyclerData);

        mainRecycler=findViewById(R.id.p4MainRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecycler.setAdapter(new P4MainRecyclerAdapter(dataList));


        // 注册button功能

        Button butTakePhoto=findViewById(R.id.p4ButtonTakeMorePhoto);
        butTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCommon.goToTakePhoto(P4Activity.this);
            }
        });

        Button butExperiment=findViewById(R.id.p4ButtonExperiment);
        butExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCommon.goToExperiment(P4Activity.this);
            }
        });

        Button butBack = findViewById(R.id.p4ButtonBack);
        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
