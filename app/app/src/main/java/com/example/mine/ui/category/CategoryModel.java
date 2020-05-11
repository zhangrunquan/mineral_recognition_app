package com.example.mine.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CategoryModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is 111111 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}