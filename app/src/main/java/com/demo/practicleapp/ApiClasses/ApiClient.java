package com.demo.practicleapp.ApiClasses;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    private static Retrofit retrofitNew;

    private static final String URL = "https://www.iroidsolutions.com/interview/";


    public static Retrofit getRetrofitInstanceGET() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getRequestHeaderGET())
                    .build();
        }
        return retrofit;
    }


    private static OkHttpClient getRequestHeaderGET() {

        return new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(new MyInterceptorGET())
                .retryOnConnectionFailure(true)
                .build();
    }

}
