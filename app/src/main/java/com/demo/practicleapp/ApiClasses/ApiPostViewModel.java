package com.demo.practicleapp.ApiClasses;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ApiPostViewModel extends AndroidViewModel {

    private Repository repository;

    public ApiPostViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<ClsLoginParams> callLoginAPIModel(String username, String password) {
        return repository.callLoginAPI(username,password);
    }

    public LiveData<ClsCategoriesResponse> getCategoriesAPIList() {
        return repository.getCategoriesAPIResponse();
    }

}
