package com.example.mine.es;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.R;
import com.example.mine.es.ViewData.ButtonData;
import com.example.mine.es.ViewData.CheckBoxData;
import com.example.mine.es.ViewData.EditTextData;
import com.example.mine.es.ViewData.FromToSpinnerData;
import com.example.mine.es.ViewData.SpinnerData;
import com.example.mine.es.ViewData.TextViewData;
import com.example.mine.es.ViewData.EsViewData;
import com.google.gson.Gson;

import java.util.List;

import static com.example.mine.ViewData.DataType.BUTTON_TYPE;
import static com.example.mine.ViewData.DataType.CHECK_BOX_TYPE;
import static com.example.mine.ViewData.DataType.EDIT_TEXT_TYPE;
import static com.example.mine.ViewData.DataType.FROM_TO_SPINNER_TYPE;
import static com.example.mine.ViewData.DataType.SPINER_TYPE;
import static com.example.mine.ViewData.DataType.TEXT_TYPE;

public class EsAdapter extends RecyclerView.Adapter {

    private List<EsViewData> mList;
    private EsContent mEsContent;

    public EsContent getmEsContent() {
        return mEsContent;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TEXT_TYPE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.es_text, parent, false);
                return new TrxtViewHolder(view);
            case BUTTON_TYPE:
                View buttonview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ex_button, parent, false);
                return new ButtonHolder(buttonview);
            case CHECK_BOX_TYPE:
                View checkBoxView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.es_checkbox, parent, false);
                return new CheckBoxViewHolder(checkBoxView);

            case SPINER_TYPE:
                View spinerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.es_spiner, parent, false);
                return new SpinerViewHolder(spinerView);

            case EDIT_TEXT_TYPE:
                View editTextView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.es_edit_text, parent, false);
                return new EditTextHolder(editTextView);

            case FROM_TO_SPINNER_TYPE:
                View fromToSpinerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.es_spiner, parent, false);
                return new FromToViewSpinerHolder(fromToSpinerView);

            default:
                throw new RuntimeException("Invalid view type!");
        }
    }

    public EsAdapter(List<EsViewData> list, EsContent esContent) {
        mList = list;
        mEsContent = esContent;
    }

    @Override
    public int getItemViewType(int position) {
        EsViewData data = (EsViewData) mList.get(position);
        return data.getmDataType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EsViewData data = mList.get(position);
        switch (holder.getItemViewType()) {
            case TEXT_TYPE:
                ((TrxtViewHolder) holder).bindDataToViews((TextViewData) data);
                break;
            case BUTTON_TYPE:
                ((ButtonHolder) holder).bindDataToViews((ButtonData) data);
                break;
            case CHECK_BOX_TYPE:
                ((CheckBoxViewHolder) holder).bindDataToViews((CheckBoxData) data);
                break;
            case SPINER_TYPE:
                ((SpinerViewHolder) holder).bindDataToViews((SpinnerData) data);
                break;
            case EDIT_TEXT_TYPE:
                ((EditTextHolder) holder).bindDataToViews((EditTextData) data);
                break;
            case FROM_TO_SPINNER_TYPE:
                ((FromToViewSpinerHolder) holder).bindDataToViews((FromToSpinnerData) data);
                break;
            default:
                throw new RuntimeException("Invalid view type!");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TrxtViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public TrxtViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.esTextView);
        }

        public void bindDataToViews(TextViewData data) {
            mTextView.setText(data.getmText());
        }
    }

    class ButtonHolder extends RecyclerView.ViewHolder {

        private Button mButtonView;

        public ButtonHolder(View view) {
            super(view);
            mButtonView = (Button) view.findViewById(R.id.esButtonView);
        }

        public void bindDataToViews(final ButtonData data) {
            mButtonView.setText(data.getmText());
            mButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Button thebutton = (Button) v;
//                    EsViewData toshow = data.getesViewData();
//                    switch (toshow.getmDataType()){
//                        case CHECK_BOX_TYPE:
//                            (CheckBoxData)toshow.getView(View.GONE);
//                    }
//
//                    String content = data.getmText();
                }
            });
        }
    }
    class CheckBoxViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckBox;
        public CheckBoxViewHolder(View view) {
            super(view);
            mCheckBox = (CheckBox) view.findViewById(R.id.esCheckBox);
        }
        public View getView(){
            return mCheckBox;
        }
        public void bindDataToViews(final CheckBoxData data) {
            mCheckBox.setText(data.getmText());
            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                    String fieldName = data.getmFieldName();
                    String content = data.getmText();
                    if (checkBox.isChecked()) {
                        mEsContent.add(fieldName, content);
                    } else {
                        mEsContent.remove(fieldName, content);
                    }

                    // TODO: 2020/1/11 删除下方测试用代码 点击示例中的checkbox,logcat中会显示当前Escontent对应的json
                    Gson gson = new Gson();
                    EsContentForJsonification obj = mEsContent.getObjForGsonJsonification();
                    Log.e("checkbox", gson.toJson(obj));
                }
            });
        }
    }

    class SpinerViewHolder extends RecyclerView.ViewHolder {

        private Spinner mSpiner;

        public SpinerViewHolder(View view) {
            super(view);
            mSpiner = (Spinner) view.findViewById(R.id.esSpiner);
        }
        public View getView(){
            return mSpiner;
        }
        public void bindDataToViews(final SpinnerData data) {
            mSpiner.setAdapter(data.getmAdapter());

            mSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String content = (String) parent.getItemAtPosition(position);
                    mEsContent.add(data.getmFieldName(), content);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    class EditTextHolder extends RecyclerView.ViewHolder {

        private EditText mEditText;


        public EditTextHolder(View view) {
            super(view);
            mEditText = (EditText) view.findViewById(R.id.esEditText);
        }
        public View getView(){
            return mEditText;
        }
        public void bindDataToViews(final EditTextData data) {
            mEditText.setHint(data.gethint());
            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mEsContent.add(data.getmFieldName(), s.toString());
                }
            });
        }

    }

    class FromToViewSpinerHolder extends RecyclerView.ViewHolder {

        private Spinner mSpiner;


        public FromToViewSpinerHolder(View view) {
            super(view);
            mSpiner = (Spinner) view.findViewById(R.id.esSpiner);
        }
        public View getView(){
            return mSpiner;
        }
        public void bindDataToViews(final FromToSpinnerData data) {
            mSpiner.setAdapter(data.getmAdapter());
            mSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String content = (String) parent.getItemAtPosition(position);

                    if (data.isFrom()) {
                        content = "from\t" + content;
                    } else {
                        content = "to\t" + content;
                    }

                    mEsContent.add(data.getmFieldName(), content);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
