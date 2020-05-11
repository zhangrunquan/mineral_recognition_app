package com.example.mine;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.es.EsAdapter;
import com.example.mine.es.EsContent;
import com.example.mine.es.ViewData.CheckBoxData;
import com.example.mine.ViewData.DataType;
import com.example.mine.es.ViewData.EditTextData;
import com.example.mine.es.ViewData.FromToSpinnerData;
import com.example.mine.es.ViewData.SpinnerData;
import com.example.mine.es.ViewData.TextViewData;
import com.example.mine.es.ViewData.EsViewData;

import java.util.ArrayList;
import java.util.List;

public class EsActivity extends AppCompatActivity {

    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Esactivity", "hereisesactivity");
        setContentView(R.layout.activity_es);

        // 设置spiner示例
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.yyw_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);


        // 设置recycler示例
        recycler = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        // 组织recycler中的内容
        List<EsViewData> list = new ArrayList<>();

//        list.add(new TextViewData("", DataType.TEXT_TYPE, "aaa"));
//
//        list.add(new CheckBoxData("形态", DataType.CHECK_BOX_TYPE, "片状"));
//
//        list.add(new SpinnerData("透明度", DataType.SPINER_TYPE, adapter));
//
//        list.add(new EditTextData("颜色", DataType.EDIT_TEXT_TYPE,""));
//
//        list.add(new FromToSpinnerData("硬度", DataType.FROM_TO_SPINNER_TYPE, adapter, true));
//        list.add(new FromToSpinnerData("硬度", DataType.FROM_TO_SPINNER_TYPE, adapter, false));

        EsContent esContent = new EsContent();
        EsAdapter adapter1 = new EsAdapter(list, esContent);

        recycler.setAdapter(adapter1);

    }

}
