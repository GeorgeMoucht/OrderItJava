package com.universtity.orderit.auth.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.universtity.orderit.auth.data.models.LoginRequest;
import com.universtity.orderit.auth.data.models.LoginResponse;
import com.universtity.orderit.auth.domain.AuthRepository;
import com.universtity.orderit.core.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;

public class LoginViewModel extends ViewModel {

    private final AuthRepository authRepository;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> loginResult = new MutableLiveData<>();
    private final SessionManager sessionManager;
    private String accessToken;
    private String refreshToken;
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();



    // Constructor expects repository
    public LoginViewModel(AuthRepository authRepository, SessionManager sessionManager) {
        this.authRepository = authRepository;
        this.sessionManager = sessionManager;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    @SuppressWarnings("unused")
    public String getAccessToken() {
        return accessToken;
    }

    @SuppressWarnings("unused")
    public String getRefreshToken() {
        return refreshToken;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void login(String username, String password) {
        isLoading.setValue(true);

        LoginRequest loginRequest = new LoginRequest(username, password);

        authRepository.login(loginRequest).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    accessToken = response.body().getAccessToken();
                    refreshToken = response.body().getRefreshToken();
                    sessionManager.saveTokens(accessToken, refreshToken);
                    loginResult.setValue("success");
                    toastMessage.setValue("Login successful!");
                } else {
                    // Log the error body or response code
                    loginResult.setValue("error");
                    toastMessage.setValue("Login failed. Check your credentials.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                Log.e("LoginViewModel", "Login request failed: " + t.getMessage());
                loginResult.setValue("error");
            }
        });
    }
}
