package com.dmtech.app.pcst;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class PostComposeViewModel extends ViewModel {
    private MutableLiveData<List<String>> selectedImagePaths;

    public LiveData<List<String>> getSelectedImagePaths() {
        if (selectedImagePaths == null) {
            selectedImagePaths = new MutableLiveData<>();
        }
        return selectedImagePaths;
    }
}
