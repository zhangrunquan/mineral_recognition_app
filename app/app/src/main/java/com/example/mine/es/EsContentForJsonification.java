package com.example.mine.es;

import java.util.Map;
import java.util.Set;

public class EsContentForJsonification {
    /**
     * 用来json序列化
     */
    private Map<String, String> singleValueMap;
    private Map<String, Set<String>> multiValueMap;
    private Map<String, Map<String, String>> fromToMap;


    public EsContentForJsonification(Map<String, String> singleValueMap, Map<String, Set<String>> multiValueMap, Map<String, Map<String, String>> fromToMap) {
        this.singleValueMap = singleValueMap;
        this.multiValueMap = multiValueMap;
        this.fromToMap = fromToMap;
    }
}
