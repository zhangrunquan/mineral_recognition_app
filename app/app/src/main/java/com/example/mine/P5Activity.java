package com.example.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.ViewData.DataType;
import com.example.mine.es.EsAdapter;
import com.example.mine.es.EsContent;
import com.example.mine.es.EsContentForJsonification;
import com.example.mine.es.ViewData.CheckBoxData;
import com.example.mine.es.ViewData.EditTextData;
import com.example.mine.es.ViewData.EsViewData;
import com.example.mine.es.ViewData.FromToSpinnerData;
import com.example.mine.es.ViewData.SpinnerData;
import com.example.mine.es.ViewData.TextViewData;
import com.example.mine.network.MineTypeResponse;
import com.example.mine.network.RetrofitClientInstance;
import com.example.mine.network.UploadStringService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class P5Activity extends AppCompatActivity {

    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p5);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);

        // 设置recycler示例
        recycler = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        // 组织recycler中的内容
        List<EsViewData> list = new ArrayList<>();

        list.add(new TextViewData("", DataType.TEXT_TYPE, "aaa"));

        list.add(new CheckBoxData("形态", DataType.CHECK_BOX_TYPE, "片状"));

        list.add(new SpinnerData("透明度", DataType.SPINER_TYPE, spinnerAdapter));

        list.add(new EditTextData("颜色", DataType.EDIT_TEXT_TYPE));

        list.add(new FromToSpinnerData("硬度", DataType.FROM_TO_SPINNER_TYPE, spinnerAdapter, true));
        list.add(new FromToSpinnerData("硬度", DataType.FROM_TO_SPINNER_TYPE, spinnerAdapter, false));

        EsContent esContent = new EsContent();
        final EsAdapter adapter1 = new EsAdapter(list, esContent);

        recycler.setAdapter(adapter1);

        // 设置按钮
        Button butSubmit = findViewById(R.id.p5ButtonSubmit);
        butSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EsContentForJsonification obj = adapter1.getmEsContent().getObjForGsonJsonification();
                Gson gson = new Gson();
                String json = gson.toJson(obj);

                UploadStringService service = RetrofitClientInstance.getRetrofitInstance().create(UploadStringService.class);
                Call<MineTypeResponse> call = service.uploadString(json);

                call.enqueue(new Callback<MineTypeResponse>() {
                    @Override
                    public void onResponse(Call<MineTypeResponse> call, Response<MineTypeResponse> response) {
                        String result = response.body().getType();

                        ActivityCommon.goToInfoPage(P5Activity.this, result);
                    }

                    @Override
                    public void onFailure(Call<MineTypeResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
