package com.demo.practicleapp.ApiClasses;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceCategories {

    @GET("mobileapp/categories.json")
    Call<ClsCategoriesResponse> getCategoryList();

}
