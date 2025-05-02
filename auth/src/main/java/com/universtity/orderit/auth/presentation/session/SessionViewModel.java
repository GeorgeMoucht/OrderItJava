package com.universtity.orderit.auth.presentation.session;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.universtity.orderit.auth.data.AuthApiService;
import com.universtity.orderit.auth.data.models.LoginResponse;
import com.universtity.orderit.auth.data.models.RefreshRequest;
import com.universtity.orderit.core.network.ApiClient;
import com.universtity.orderit.core.utils.SessionManager;
import com.universtity.orderit.core.utils.JwtUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionViewModel extends AndroidViewModel {

    private final SessionManager sessionManager;
    private final AuthApiService authApiService;

    private final MutableLiveData<Boolean> isAuthenticated = new MutableLiveData<>(false);

    public SessionViewModel(@NonNull Application application) {
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
        authApiService = ApiClient.getNonAuthClient().create(AuthApiService.class);

        checkSession();
    }

    public LiveData<Boolean> getIsAuthenticated() {
        return isAuthenticated;
    }

    public void checkSession() {
        String accessToken = sessionManager.getAccessToken();

        if (accessToken != null) {
            Log.d("SessionViewModel", "AccessToken exists");
            if (!JwtUtils.isTokenExpired(accessToken)) {
                Log.d("SessionViewModel", "AccessToken is still valid.");
                // Access Token still valid
                isAuthenticated.setValue(true);
            } else {
                Log.d("SessionViewModel", "AccessToken expired, trying to refresh.");
                // Try to refresh
                tryAutoRefreshToken();
            }
        } else {
            // No token at all
            Log.d("SessionViewModel", "No AccessToken found.");
            isAuthenticated.setValue(false);
        }
    }

    private void tryAutoRefreshToken() {
        Log.d("SessionViewModel", "Trying to refresh token...");
        String refreshToken = sessionManager.getRefreshToken();

        if (refreshToken != null) {
            Log.d("SessionViewModel", "RefreshToken found, sending refresh request...");
            authApiService.refreshToken(new RefreshRequest(refreshToken))
                    .enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Log.d("SessionViewModel", "Refresh successful, new AccessToken obtained.");
                                String newAccessToken = response.body().getAccessToken();
                                sessionManager.saveTokens(newAccessToken, refreshToken);

                                isAuthenticated.setValue(true);
                            } else {
                                Log.d("SessionViewModel", "Refresh failed with code: " + response.code());
                                sessionManager.clearSession();
                                isAuthenticated.setValue(false);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                            Log.d("SessionViewModel", "Refresh call failed: " + t.getMessage());
                            sessionManager.clearSession();
                            isAuthenticated.setValue(false);
                        }
                    });
        } else {
            Log.d("SessionViewModel", "No RefreshToken found.");
            isAuthenticated.setValue(false);
        }
    }
}
