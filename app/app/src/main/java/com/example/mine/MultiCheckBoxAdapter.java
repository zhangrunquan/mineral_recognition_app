package com.example.mine;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.es.EditTextData;
import com.example.mine.es.EsContent;
import com.example.mine.es.FromToSpinnerData;

import java.util.List;

public class MultiCheckBoxAdapter extends RecyclerView.Adapter {

    private static final int TEXT_TYPE = 1;
    private static final int CHECK_BOX_TYPE = 2;
    private static final int SPINER_TYPE = 3;
    private static final int EDIT_TEXT_TYPE = 4;
    private static final int FROM_TO_SPINNER_TYPE = 5;

    private List<Object> mList;
    public EsContent mEsContent;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TEXT_TYPE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.es_text, parent, false);
                return new TrxtViewHolder(view);

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

    public MultiCheckBoxAdapter(List<Object> list, EsContent essContent) {
        mList = list;
        mEsContent = essContent;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mList.get(position);
        if (mList.get(position) instanceof String) {
            return TEXT_TYPE;
        } else if (mList.get(position) instanceof CheckBoxData) {
            return CHECK_BOX_TYPE;
        } else if (item instanceof SpinnerData) { // 不恰当的检查方法
            return SPINER_TYPE;
        } else if (item instanceof EditTextData) {
            return EDIT_TEXT_TYPE;
        } else if (item instanceof FromToSpinnerData) {
            return FROM_TO_SPINNER_TYPE;

        } else {
            throw new RuntimeException("Invalid data!");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TEXT_TYPE:
                ((TrxtViewHolder) holder).bindDataToViews((String) mList.get(position));
                break;

            case CHECK_BOX_TYPE:
                ((CheckBoxViewHolder) holder).bindDataToViews((CheckBoxData) mList.get(position));
                break;
            case SPINER_TYPE:
                ((SpinerViewHolder) holder).bindDataToViews((SpinnerData) mList.get(position));
                break;
            case EDIT_TEXT_TYPE:
                ((EditTextHolder) holder).bindDataToViews((EditTextData) mList.get(position));
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

        public void bindDataToViews(String text) {
            mTextView.setText(text);
        }
    }

    class CheckBoxViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckBox;

        public CheckBoxViewHolder(View view) {
            super(view);
            mCheckBox = (CheckBox) view.findViewById(R.id.esCheckBox);
        }

        public void bindDataToViews(CheckBoxData data) {
            mCheckBox.setText(data.mText);
            mCheckBox.setTag(data.mMeta);
            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fieldName = (String) v.getTag();
                    String content = ((CheckBox) v).getText().toString();
                    mEsContent.add(fieldName, content);
                    Log.e("checkbox", mEsContent.toJson());
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

        public void bindDataToViews(final SpinnerData data) {
            mSpiner.setAdapter(data.mAdapter);
            mSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String content = (String) parent.getItemAtPosition(position);
                    mEsContent.add(data.mFieldName, content);

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

        public void bindDataToViews(final EditTextData data) {
            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mEsContent.add(data.mFieldName, s.toString());
                }
            });
        }

        class SpinerViewHolder extends RecyclerView.ViewHolder {

            private Spinner mSpiner;

            public SpinerViewHolder(View view) {
                super(view);
                mSpiner = (Spinner) view.findViewById(R.id.esSpiner);
            }

            public void bindDataToViews(final SpinnerData data) {
                mSpiner.setAdapter(data.mAdapter);
                mSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String content = (String) parent.getItemAtPosition(position);
                        mEsContent.add(data.mFieldName, content);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }

    class FromToViewSpinerHolder extends RecyclerView.ViewHolder {

        private Spinner mSpiner;

        public FromToViewSpinerHolder(View view) {
            super(view);
            mSpiner = (Spinner) view.findViewById(R.id.esSpiner);
        }

        public void bindDataToViews(final FromToSpinnerData data) {
            mSpiner.setAdapter(data.mAdapter);
            mSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String content = (String) parent.getItemAtPosition(position);
                    if (data.isFrom()) {
                        content = "from\t" + content;
                    } else {
                        content = "to\t" + content;
                    }
                    mEsContent.add(data.mFieldName, content);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
