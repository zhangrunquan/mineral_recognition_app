package com.example.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.mine.ViewData.DataType;
import com.example.mine.ViewData.ViewData;
import com.example.mine.mineinfo.MineInfo;
import com.example.mine.mineinfo.MineInfoManager;
import com.example.mine.p4.P4ImageData;
import com.example.mine.p4.P4ImageRecyclerAdapter;
import com.example.mine.p4.P4ImageRecyclerData;
import com.example.mine.p4.P4MainRecyclerAdapter;
import com.example.mine.p4.P4TextData;
import com.example.mine.p4.P4WikiAdapter;
import com.example.mine.ui.experiment.ExperimentFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P4Activity extends AppCompatActivity {

    private RecyclerView mainRecycler;
    private List<Pair<String,String>> pairlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p4);

        String mineName = getIntent().getStringExtra("mineName");// 英文名
        Log.d("P4Activity", String.format("whatismineName%s", mineName));
        // 构造imageRecycler的Adapter
        MineInfoManager manager=new MineInfoManager(this);
        MineInfo mineInfo=manager.getMineInfo(mineName);
//        Log.d("P4Activity", String.format("whatisBases%s", mineInfo.Bases));
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

        //小百科部分
        initpairs(mineInfo);
//        Log.d("P4Activity", String.format("whatisp4wikinumber%d",R.layout.p4_wiki ));
        P4WikiAdapter adapter2=new P4WikiAdapter(P4Activity.this,R.layout.p4_wiki,pairlist);

        ListView listView2=(ListView) findViewById(R.id.p4WikiRecycler);
        listView2.setAdapter(adapter2);
        // 注册button功能

//        Button butTakePhoto=findViewById(R.id.p4ButtonTakeMorePhoto);
////        butTakePhoto.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                ActivityCommon.goToTakePhoto(P4Activity.this);
////            }
////        });

        Button butExperiment=findViewById(R.id.p4ButtonExperiment);
        butExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityCommon.goToExperiment(P4Activity.this);
                changeToExperimentFragment();
            }
        });

//        Button butBack = findViewById(R.id.p4ButtonBack);
//        butBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
    private void changeToExperimentFragment(){
        //如果是用的v4的包，则用getActivity().getSuppoutFragmentManager();
        FragmentManager fm = getSupportFragmentManager();
        //注意v4包的配套使用
        ExperimentFragment fragment = new ExperimentFragment();
        fm.beginTransaction().replace(this.getTaskId(),fragment).addToBackStack(null).commit();
    }
    private void initpairs(MineInfo mineInfo){
        Pair<String,String> apair=new Pair<>("中文名",mineInfo.ChineseName);
        pairlist.add(apair);
        Pair<String,String> apair2=new Pair<>("英文名",mineInfo.EnglishName);
        pairlist.add(apair2);
        Pair<String,String> apair3=new Pair<>("类别",mineInfo.chemistry);
        pairlist.add(apair3);
        Pair<String,String> apair4=new Pair<>("主要成分",mineInfo.Bases);
        pairlist.add(apair4);
        Pair<String,String> apair5=new Pair<>("颜色",mineInfo.Color);
        pairlist.add(apair5);
        Pair<String,String> apair6=new Pair<>("形态",mineInfo.Shape);
        pairlist.add(apair6);
        Pair<String,String> apair7=new Pair<>("条痕",mineInfo.Streaking);
        pairlist.add(apair7);
        Pair<String,String> apair8=new Pair<>("光泽",mineInfo.Lightness);
        pairlist.add(apair8);
        Pair<String,String> apair9=new Pair<>("透明度",mineInfo.Streaks);
        pairlist.add(apair9);
        Pair<String,String> apair10=new Pair<>("硬度",mineInfo.Transparence);
        pairlist.add(apair10);
        Pair<String,String> apair11=new Pair<>("解理",mineInfo.Cleavage);
        pairlist.add(apair11);
        Pair<String,String> apair12=new Pair<>("断口",mineInfo.Fracture);
        pairlist.add(apair12);
        Pair<String,String> apair13=new Pair<>("比重",mineInfo.Proportion);
        pairlist.add(apair13);
        Pair<String,String> apair14=new Pair<>("晶面条纹",mineInfo.Stripes);
        pairlist.add(apair14);
        Pair<String,String> apair15=new Pair<>("弹性或挠性",mineInfo.Elasticity);
        pairlist.add(apair15);
        Pair<String,String> apair16=new Pair<>("磁性",mineInfo.Magnetism);
        pairlist.add(apair16);
        Pair<String,String> apair17=new Pair<>("发光性",mineInfo.Photism);
        pairlist.add(apair17);
        Pair<String,String> apair18=new Pair<>("滑腻感",mineInfo.Greasiness);
        pairlist.add(apair18);
        Pair<String,String> apair19=new Pair<>("是否染手",mineInfo.Dye);
        pairlist.add(apair19);
        Pair<String,String> apair20=new Pair<>("特殊性质",mineInfo.OtherProperty);
        pairlist.add(apair20);

    }
}
