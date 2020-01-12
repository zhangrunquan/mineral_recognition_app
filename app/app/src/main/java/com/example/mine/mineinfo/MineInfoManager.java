package com.example.mine.mineinfo;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineInfoManager {

    private Map<String, MineInfo> map;

    public MineInfoManager(Context context) {
        map = load(context);
    }

    public MineInfo getMineInfo(String key) {
        return map.get(key);
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
        }
        return map;
    }

    public List<MineInfo> filter() {
        return null;
    }

}
