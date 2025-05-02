package com.universtity.orderit.auth.domain;

import retrofit2.Call;

import com.universtity.orderit.auth.data.models.LoginRequest;
import com.universtity.orderit.auth.data.models.LoginResponse;

public interface AuthRepository {
    Call<LoginResponse> login(LoginRequest loginRequest);
}