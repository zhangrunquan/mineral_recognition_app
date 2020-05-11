package com.example.mine.ui.experiment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExperimentModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExperimentModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is 111 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}