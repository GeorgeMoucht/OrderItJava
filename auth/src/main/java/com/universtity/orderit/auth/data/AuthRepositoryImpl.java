package com.universtity.orderit.auth.data;


import android.content.Context;

import com.universtity.orderit.auth.data.models.LoginRequest;
import com.universtity.orderit.auth.data.models.LoginResponse;
import com.universtity.orderit.auth.data.network.AuthApiClient;
import com.universtity.orderit.auth.domain.AuthRepository;

import retrofit2.Call;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthApiService authApiService;


    public AuthRepositoryImpl(Context context) {
        authApiService = AuthApiClient.getNonAuthClient(context).create(AuthApiService.class);
    }

    @Override
    public Call<LoginResponse> login(LoginRequest loginRequest) {
        return authApiService.login(loginRequest);
    }
}
