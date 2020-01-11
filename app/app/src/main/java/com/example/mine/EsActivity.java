package com.example.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mine.es.EditTextData;
import com.example.mine.es.EsContent;
import com.example.mine.es.FromToSpinnerData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static String[] names = {"a", "b", "c"};

    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_es);

        // 设置spiner
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        // check box
//        CheckBox cbox=findViewById(R.id.checkbox_meat);
//        cbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean checked = ((CheckBox) v).isChecked();
//                Log.e("checkbox: ", v.toString());
//            }
//        });

        //
        recycler=findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        List<Object> list=new ArrayList<>();
        list.addAll(Arrays.asList(names));
        list.add(new CheckBoxData("片状","形态"));
        list.add(new CheckBoxData("aaa","a"));
        list.add(new SpinnerData(adapter, "透明度"));

        list.add(new EditTextData("颜色"));

        list.add(new FromToSpinnerData("硬度",adapter,true));
        list.add(new FromToSpinnerData("硬度",adapter,false));

        EsContent esContent=new EsContent();
        MultiCheckBoxAdapter adapter1=new MultiCheckBoxAdapter(list,esContent);
        recycler.setAdapter(adapter1);


//        LinearLayout l=(LinearLayout) buildMultiCheckBox("ok",names);
//        recycler.addView(l);

    }


    /**
     * 从给定参数生成CheckBox
     *
     * @param meta will be saved by view.setTag()
     * @return CheckBox
     */
    public CheckBox buildCheckBox(String text, Object meta) {
        return null;
    }

    /**
     * 生成对一个属性的复选框
     *
     * @param parent 属性名
     * @param arr    checkBox显示的文字
     * @return parent view
     */
    public View buildMultiCheckBox(String parent, String[] arr) {
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);// 定义文本显示组件

        TextView txt = new TextView(this);
        txt.setLayoutParams(text);// 配置文本显示组件的参数
        txt.setText("");// 配置显示文字
        txt.setTextSize(20);
        layout.addView(txt, text);
        return layout;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //在选中时读值
        String s = (String) parent.getItemAtPosition(position);

        //直接从view读值
        TextView v = new TextView(this);
        AppCompatTextView new_view = (AppCompatTextView) view;
        Log.e("string ", new_view.getText().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
