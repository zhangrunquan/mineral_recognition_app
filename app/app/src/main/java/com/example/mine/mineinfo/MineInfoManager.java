package com.example.mine.mineinfo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineInfoManager {

    private Map<String, MineInfo> map;

    public MineInfoManager(Context context) {
        map = load(context);
    }

    public MineInfo getMineInfo(String key) {
        Log.d("Mineinfomanager", String.format("iscontainskey%b", map.containsKey(key)));
        if (map.containsKey(key)){
            return map.get(key);
        }
        else{
            return getMineInfo("notexist");
        }
    }

    private Map<String, MineInfo> load(Context context) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("mine_data.json"))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        MineInfoList mineInfoList = gson.fromJson(reader, MineInfoList.class);

        List<MineInfo> list = mineInfoList.getList();

        Map<String, MineInfo> map = new HashMap<>();
        for (MineInfo mineInfo : list) {
            map.put(mineInfo.EnglishName, mineInfo);
            map.put(mineInfo.ChineseName, mineInfo);
        }
        return map;
    }

    public abstract static class MineInfoFilter{
        public abstract boolean filter(MineInfo mineInfo);
    }

    //MineInfoFilter返回true的元素会放在列表中返回
    public List<MineInfo> filter(MineInfoFilter mineInfoFilter){
        List<MineInfo> list=new ArrayList<>();

        for(MineInfo mineInfo:map.values()){
            if(mineInfoFilter.filter(mineInfo)){
                list.add(mineInfo);
            }
        }

        return list;
    }
}
