package com.example.mine.es;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EsContent {

    private static String[] singleValues = {"颜色", "透明度"};
    private static String[] multiValues = {"形态"};
    private static String[] fromToValues = {"硬度"};

    private Set<String> single, multi, fromTo;

    private Map<String, String> singleValueMap;
    private Map<String, Set<String>> multiValueMap;
    private Map<String, Map<String, String>> fromToMap;

    public EsContent() {
        single = new HashSet<String>(Arrays.asList(singleValues));
        multi = new HashSet<String>(Arrays.asList(multiValues));
        fromTo = new HashSet<String>(Arrays.asList(fromToValues));

        singleValueMap = new HashMap<>();
        multiValueMap = new HashMap<>();
        fromToMap = new HashMap<>();

    }

    public void add(String fieldName,String content){
        if(single.contains(fieldName)){
            singleValueMap.put(fieldName,content);
        }else if (multi.contains(fieldName)){
            Set<String> set=multiValueMap.get(fieldName);
            if(set==null){
                set=new HashSet<>();
                set.add(content);
                multiValueMap.put(fieldName,set);
            }else {
                set.add(content);
            }
        }else if(fromTo.contains(fieldName)){ //content应为类似 "from\tcontent" 的形式
            String[] contents=content.split("\t");

            if(content.length()!=2){
                throw new RuntimeException(String.format("FromTo Illegal Field Num: %d",contents.length));
            }

            Map<String,String> map=fromToMap.get(fieldName);
            if (map==null){
                map=new HashMap<>();
                map.put(contents[0],contents[1]);
                fromToMap.put(fieldName,map);
            }else {
                map.put(contents[0],contents[1]);
                fromToMap.put(fieldName,map);
            }

        }else {
            String msg=String.format("Illegal fieldName: %s",fieldName);

            throw new RuntimeException(msg);
        }
    }

    /**
     * 是否所有字段都已有值
     */
    public boolean allFilled(){
        // TODO: 2020/1/11 通过比较字段array和Map的长度,以及遍历(fromTo)检查所有字段都已有值
        return true;
    }
    public String toJson(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }

}
