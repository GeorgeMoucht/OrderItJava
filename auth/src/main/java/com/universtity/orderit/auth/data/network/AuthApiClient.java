package com.universtity.orderit.auth.data.network;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthApiClient {
    private static Retrofit retrofit = null;

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
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/api/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
