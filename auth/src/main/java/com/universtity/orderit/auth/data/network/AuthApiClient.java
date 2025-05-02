package com.universtity.orderit.auth.data.network;

import android.content.Context;

import com.universtity.orderit.core.utils.SettingsManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthApiClient {
    private static Retrofit retrofit = null;

    private static Retrofit nonAuthRetrofit = null;

    public static Retrofit getNonAuthClient(Context context) {
        if (nonAuthRetrofit == null) {
            String baseUrl = new SettingsManager(context).getBaseUrl();

            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            nonAuthRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return nonAuthRetrofit;
    }

    public static Retrofit getAuthClient(Context context) {
        if (retrofit == null) {
            String baseUrl = new SettingsManager(context).getBaseUrl();

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void reset() {
        retrofit = null;
        nonAuthRetrofit = null;
    }
}
