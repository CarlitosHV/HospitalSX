package com.example.hospitalsx.ui.editar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditarModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EditarModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is editar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}