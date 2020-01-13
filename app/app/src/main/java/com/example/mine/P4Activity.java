package com.example.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.mine.ViewData.DataType;
import com.example.mine.ViewData.ViewData;
import com.example.mine.mineinfo.MineInfo;
import com.example.mine.mineinfo.MineInfoManager;
import com.example.mine.p4.P4ImageData;
import com.example.mine.p4.P4ImageRecyclerAdapter;
import com.example.mine.p4.P4ImageRecyclerData;
import com.example.mine.p4.P4MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

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

        dataList.add(imageRecyclerData);


        mainRecycler=findViewById(R.id.p4MainRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecycler.setAdapter(new P4MainRecyclerAdapter(dataList));

    }
}
