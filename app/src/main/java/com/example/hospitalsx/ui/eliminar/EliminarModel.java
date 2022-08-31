package com.example.hospitalsx.ui.eliminar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EliminarModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EliminarModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is eliminar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}