package com.example.mine.ui.experiment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.ActivityCommon;
import com.example.mine.P5Activity;
import com.example.mine.R;
import com.example.mine.ViewData.DataType;
import com.example.mine.es.EsAdapter;
import com.example.mine.es.EsContent;
import com.example.mine.es.EsContentForJsonification;
import com.example.mine.es.ViewData.ButtonData;
import com.example.mine.es.ViewData.CheckBoxData;
import com.example.mine.es.ViewData.EditTextData;
import com.example.mine.es.ViewData.EsViewData;
import com.example.mine.es.ViewData.FromToSpinnerData;
import com.example.mine.es.ViewData.SpinnerData;
import com.example.mine.es.ViewData.TextViewData;
import com.example.mine.network.MineTypeResponse;
import com.example.mine.network.RetrofitClientInstance;
import com.example.mine.network.UploadStringService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class ExperimentFragment extends Fragment {
//
//    private ExperimentModel experimentViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                                  ViewGroup container, Bundle savedInstanceState) {
//        experimentViewModel =
//                ViewModelProviders.of(this).get(ExperimentModel.class);
//        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        experimentViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
//}
public class ExperimentFragment extends Fragment {

    private ExperimentModel experimentViewModel;
    private RecyclerView recycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        experimentViewModel =
//                ViewModelProviders.of(this).get(ExperimentModel.class);
//        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        experimentViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        super.onCreate(savedInstanceState);
        View root=inflater.inflate(R.layout.activity_p5,container,false);

        ArrayAdapter<CharSequence> spinnerAdaptertmd = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.tmd_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> spinnerAdapteryyw = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.yyw_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> spinnerAdapteryw = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.yw_array, android.R.layout.simple_spinner_item);

        // 设置recycler示例
        recycler = root.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recycler.setLayoutManager(layoutManager);

        // 组织recycler中的内容
        List<EsViewData> list = new ArrayList<>();

//        TextViewData color = new TextViewData("", DataType.TEXT_TYPE, "颜色（用、分隔）");

//        ButtonData colorbutton = new ButtonData(color,"", DataType.BUTTON_TYPE, "颜色（用、分隔）");
//        list.add(colorbutton);
        list.add(new TextViewData("", DataType.TEXT_TYPE, "颜色（多种用、分隔）"));
//        (Text) color.setVisibility(View.VISIBLE);
        list.add(new EditTextData("颜色", DataType.EDIT_TEXT_TYPE,"如：无色、白色"));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "形态（多种用、分隔）"));
        list.add(new EditTextData("形态", DataType.EDIT_TEXT_TYPE,"如：鳞片状"));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "条痕（多种用、分隔）"));
        list.add(new EditTextData("条痕", DataType.EDIT_TEXT_TYPE,"如：浅褐色"));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "硬度"));
        list.add(new EditTextData("硬度", DataType.EDIT_TEXT_TYPE,"如：2~3"));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "比重"));
        list.add(new EditTextData("比重", DataType.EDIT_TEXT_TYPE,"如：2.09~2.23"));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "光泽"));
        list.add(new CheckBoxData("光泽", DataType.CHECK_BOX_TYPE, "金属光泽"));
        list.add(new CheckBoxData("光泽", DataType.CHECK_BOX_TYPE, "丝绢光泽"));
        list.add(new CheckBoxData("光泽", DataType.CHECK_BOX_TYPE, "玻璃光泽"));
        list.add(new CheckBoxData("光泽", DataType.CHECK_BOX_TYPE, "土状光泽"));
        list.add(new CheckBoxData("光泽", DataType.CHECK_BOX_TYPE, "油脂光泽"));
        list.add(new CheckBoxData("光泽", DataType.CHECK_BOX_TYPE, "半金属光泽"));
        list.add(new CheckBoxData("光泽", DataType.CHECK_BOX_TYPE, "非金属光泽"));
        list.add(new CheckBoxData("光泽", DataType.CHECK_BOX_TYPE, "金刚光泽"));
        list.add(new CheckBoxData("光泽", DataType.CHECK_BOX_TYPE, "珍珠光泽"));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "透明度"));
        list.add(new SpinnerData("透明度", DataType.SPINER_TYPE, spinnerAdaptertmd));
//        list.add(new TextViewData("", DataType.TEXT_TYPE, "硬度"));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "解理"));
        list.add(new CheckBoxData("解理", DataType.CHECK_BOX_TYPE, "极完全解理"));
        list.add(new CheckBoxData("解理", DataType.CHECK_BOX_TYPE, "完全解理"));
        list.add(new CheckBoxData("解理", DataType.CHECK_BOX_TYPE, "中等解理"));
        list.add(new CheckBoxData("解理", DataType.CHECK_BOX_TYPE, "不完全解理"));
        list.add(new CheckBoxData("解理", DataType.CHECK_BOX_TYPE, "极不完全解理"));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "断口"));
        list.add(new CheckBoxData("断口", DataType.CHECK_BOX_TYPE, "贝壳状"));
        list.add(new CheckBoxData("断口", DataType.CHECK_BOX_TYPE, "平坦状"));
        list.add(new CheckBoxData("断口", DataType.CHECK_BOX_TYPE, "土状"));
        list.add(new CheckBoxData("断口", DataType.CHECK_BOX_TYPE, "锯齿状"));
        list.add(new CheckBoxData("断口", DataType.CHECK_BOX_TYPE, "参差状"));

        list.add(new TextViewData("", DataType.TEXT_TYPE, "弹性或挠性"));
        list.add(new SpinnerData("弹性或挠性", DataType.SPINER_TYPE, spinnerAdapteryyw));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "磁性"));
        list.add(new SpinnerData("磁性", DataType.SPINER_TYPE, spinnerAdapteryw));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "发光性"));
        list.add(new SpinnerData("发光性", DataType.SPINER_TYPE, spinnerAdapteryw));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "滑腻感"));
        list.add(new SpinnerData("滑腻感", DataType.SPINER_TYPE, spinnerAdapteryw));
        list.add(new TextViewData("", DataType.TEXT_TYPE, "染手"));
        list.add(new SpinnerData("染手", DataType.SPINER_TYPE, spinnerAdapteryw));






        EsContent esContent = new EsContent();
        final EsAdapter adapter1 = new EsAdapter(list, esContent);

        recycler.setAdapter(adapter1);

        // 设置按钮
        Button butSubmit = root.findViewById(R.id.p5ButtonSubmit);
        butSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EsContentForJsonification obj = adapter1.getmEsContent().getObjForGsonJsonification();
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                Log.d("ESactivity", String.format("uploading22%s", json));
                UploadStringService service = RetrofitClientInstance.getRetrofitInstance().create(UploadStringService.class);
                Call<MineTypeResponse> call = service.uploadString(json);

                call.enqueue(new Callback<MineTypeResponse>() {
                    @Override
                    public void onResponse(Call<MineTypeResponse> call, Response<MineTypeResponse> response) {
                        String result = response.body().getType();
//                        Log.d("EXP", String.format("whatisresult%s", result));
                        ActivityCommon.goToInfoPage(ExperimentFragment.this.getActivity(), result);
                    }

                    @Override
                    public void onFailure(Call<MineTypeResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
        return root;
    }
}