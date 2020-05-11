package com.example.mine;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.ViewData.DataType;
import com.example.mine.ViewData.ViewData;
import com.example.mine.mineinfo.MineInfo;
import com.example.mine.mineinfo.MineInfoManager;
import com.example.mine.p6.P6CardViewData;
import com.example.mine.p6.P6RecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p6);

        RecyclerView recycler = findViewById(R.id.p6Recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        // 组织recycler中的内容
        List<ViewData> list = new ArrayList<>();
        final String actionCode = getIntent().getStringExtra("chemistry");

//        // 从矿物信息获取指定类别
//        String type = actionCode;

        MineInfoManager manager = new MineInfoManager(this);
        List<MineInfo> infoList = manager.filter(new MineInfoManager.MineInfoFilter() {
            @Override
            public boolean filter(MineInfo mineInfo) {
                return mineInfo.chemistry.equals(actionCode);
            }
        });

        for (MineInfo mineInfo : infoList) {
            for (String imagePath:mineInfo.images){
//                String imagePath = mineInfo.images.get(0);
                String englishName = mineInfo.EnglishName;

                Map<String, String> textMap = new HashMap<>();
                textMap.put("englishName", englishName);

                list.add(new P6CardViewData(DataType.P6_CARD_VIEW_DATA, imagePath, textMap, this));
            }

        }

        P6RecyclerAdapter recyclerAdapter = new P6RecyclerAdapter(list);
        recycler.setAdapter(recyclerAdapter);
    }
}
