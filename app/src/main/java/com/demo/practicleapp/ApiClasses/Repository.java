package com.demo.practicleapp.ApiClasses;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {


    private Context context;
    private AppExecutor mAppExecutor;

    public Repository(Context context) {
        this.mAppExecutor = new AppExecutor();
        this.context = context;
    }


    //LoginAPI
    public LiveData<ClsLoginParams> callLoginAPI(String username, String password) {
        final MutableLiveData<ClsLoginParams> mutableLiveData = new MutableLiveData<>();
        InterfaceLogin myInterface = ApiClient.getRetrofitInstanceGET().create(InterfaceLogin.class);
        Call<ClsLoginParams> call = myInterface.callLoginAPIBody(username, password);
        Log.e("--login--", "call: " + call.request().url());

        call.enqueue(new Callback<ClsLoginParams>() {
            @Override
            public void onResponse(Call<ClsLoginParams> call, Response<ClsLoginParams> response) {
                Log.d("--login--", "onResponse: " + new Gson().toJson(response.body()));
                if (response.body() != null && response.code() == 200) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ClsLoginParams> call, Throwable t) {
                try {
                    mutableLiveData.setValue(null);
                    Log.d("--login--", "onFailure: " + t.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return mutableLiveData;
    }

    //Category
    public LiveData<ClsCategoriesResponse> getCategoriesAPIResponse() {

        final MutableLiveData<ClsCategoriesResponse> mCategoriesLst = new MutableLiveData<>();
        InterfaceCategories myInterface =
                ApiClient.getRetrofitInstanceGET().create(InterfaceCategories.class);
        Call<ClsCategoriesResponse> call = myInterface.getCategoryList();

        Log.e("--repo--", "URL: " + call.request().url());

        call.enqueue(new Callback<ClsCategoriesResponse>() {
            @Override
            public void onResponse(Call<ClsCategoriesResponse> call, Response<ClsCategoriesResponse> response) {
                Log.e("--repo--", "onRequestResponse: " + response.code());
                if (response.body() != null && response.code() == 200) {
                    mCategoriesLst.setValue(response.body());
                    Log.d("--repo--", "onResponse: " + new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<ClsCategoriesResponse> call, Throwable t) {
                try {
                    mCategoriesLst.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--repo--", "onFailure: " + t.toString());
            }
        });
        return mCategoriesLst;
    }


}
