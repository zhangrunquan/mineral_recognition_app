package com.example.mine.es;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EsContentForJsonification {
    /**
     * 用来json序列化
     */
    private List<Map<String, String>> singleValueFields;
    private List<Map<String, Set<String>>> multiValueFields;
    private List<Map<String, Map<String, String>>> fromToFields;

    public EsContentForJsonification(List<Map<String, String>> single,
                                     List<Map<String, Set<String>>> multi,
                                     List<Map<String, Map<String, String>>> fromTo) {
        singleValueFields=single;
        multiValueFields=multi;
        fromToFields=fromTo;
    }

}
