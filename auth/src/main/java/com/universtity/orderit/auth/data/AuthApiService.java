package com.universtity.orderit.auth.data;

import com.universtity.orderit.auth.data.models.LoginRequest;
import com.universtity.orderit.auth.data.models.LoginResponse;
import com.universtity.orderit.auth.data.models.RefreshRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("authentication/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("authentication/refresh/")
    Call<LoginResponse> refreshToken(@Body RefreshRequest refreshRequest);
}