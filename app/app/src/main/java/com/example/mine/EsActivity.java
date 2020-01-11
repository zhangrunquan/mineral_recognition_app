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

import com.example.mine.es.EsContent;
import com.example.mine.es.ViewData.CheckBoxData;
import com.example.mine.es.ViewData.DataType;
import com.example.mine.es.ViewData.EditTextData;
import com.example.mine.es.ViewData.FromToSpinnerData;
import com.example.mine.es.ViewData.SpinnerData;
import com.example.mine.es.ViewData.TextViewData;
import com.example.mine.es.ViewData.ViewData;

import java.util.ArrayList;
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
        recycler = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        List<ViewData> list = new ArrayList<>();

        list.add(new TextViewData("", DataType.TEXT_TYPE, "aaa"));

        list.add(new CheckBoxData("形态", DataType.CHECK_BOX_TYPE, "片状"));
//        list.add(new CheckBoxData("aaa","a"));

        list.add(new SpinnerData("透明度", DataType.SPINER_TYPE, adapter));

        list.add(new EditTextData("颜色", DataType.EDIT_TEXT_TYPE));
//
        list.add(new FromToSpinnerData("硬度", DataType.FROM_TO_SPINNER_TYPE, adapter, true));
        list.add(new FromToSpinnerData("硬度", DataType.FROM_TO_SPINNER_TYPE, adapter, false));

        EsContent esContent = new EsContent();
        EsAdapter adapter1 = new EsAdapter(list, esContent);
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
