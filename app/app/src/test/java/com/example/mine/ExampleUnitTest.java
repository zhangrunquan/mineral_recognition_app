package com.example.mine;

import com.example.mine.es.EsContent;
import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        EsContent ec = new EsContent();
        Gson gosn = new Gson();
        System.out.println(gosn.toJson(ec));

    }
}