package com.example.islamicapp.WebServices;

import com.example.islamicapp.AppController;
import com.google.gson.Gson;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Gson gson;
    static final OkHttpClient okHttpClient = new Builder().readTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS).build();
    private static Retrofit retrofit = null;

    public static Retrofit getList() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(Level.BODY);
        retrofit = new Retrofit.Builder().baseUrl(AppController.WEB_SERVICE_URL).addConverterFactory(GsonConverterFactory.create()).client(new Builder().addInterceptor(interceptor).build()).build();
        return retrofit;
    }
}
