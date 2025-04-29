package com.universtity.orderit.auth.data;

import android.content.Context;

import com.universtity.orderit.auth.data.models.LoginRequest;
import com.universtity.orderit.auth.data.models.LoginResponse;
import com.universtity.orderit.auth.domain.AuthRepository;
import com.universtity.orderit.core.network.ApiClient;

import retrofit2.Call;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthApiService authApiService;


    public AuthRepositoryImpl(Context context) {
        authApiService = ApiClient.getNonAuthClient().create(AuthApiService.class);
    }

    @Override
    public Call<LoginResponse> login(LoginRequest loginRequest) {
        return authApiService.login(loginRequest);
    }
}
