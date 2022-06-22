package com.demo.practicleapp.ApiClasses;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceLogin {

    @POST("mobileapp/login.php")
    Call<ClsLoginParams> callLoginAPIBody(@Query("username") String username,
                                          @Query("password") String password);

}
