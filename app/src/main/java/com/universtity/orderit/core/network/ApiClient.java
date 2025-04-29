package com.universtity.orderit.core.network;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit authRetrofit = null;
    private static Retrofit nonAuthRetrofit = null;


    public static Retrofit getNonAuthClient() {
        if (nonAuthRetrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            nonAuthRetrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/api/") // your server address
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return nonAuthRetrofit;
    }

    public static Retrofit getAuthClient(Context context) {
        if (authRetrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
                    .build();

            authRetrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/api/") // your server address
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return authRetrofit;
    }
}