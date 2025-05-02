package com.universtity.orderit.auth.data.network;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.universtity.orderit.auth.data.AuthApiService;
import com.universtity.orderit.auth.data.models.LoginResponse;
import com.universtity.orderit.auth.data.models.RefreshRequest;
import com.universtity.orderit.core.utils.SessionManager;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;


public class AuthInterceptor implements Interceptor {

    private final SessionManager sessionManager;
    private final AuthApiService authApiService;

    public AuthInterceptor(Context context) {
        sessionManager = new SessionManager(context);
        authApiService = AuthApiClient.getAuthClient(context).create(AuthApiService.class);
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String accessToken = sessionManager.getAccessToken();

        // Attach token to every request
        if (accessToken != null) {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
        }

        Response response = chain.proceed(request);

        if (response.code() == 401) {
            Log.d("AuthInterceptor", "Access token expired. Trying to refresh...");

            // Try to refresh token
            String refreshToken = sessionManager.getRefreshToken();
            if (refreshToken != null) {
                Call<LoginResponse> call = authApiService.refreshToken(new RefreshRequest(refreshToken));
                retrofit2.Response<LoginResponse> refreshResponse = call.execute();

                if (refreshResponse.isSuccessful() && refreshResponse.body() != null) {
                    String newAccessToken = refreshResponse.body().getAccessToken();

                    // Save new token
                    sessionManager.saveTokens(newAccessToken, refreshToken); // Refresh token usually doesn't change

                    // Retry the original request with new access token
                    Request newRequest = request.newBuilder()
                            .removeHeader("Authorization")
                            .addHeader("Authorization", "Bearer " + newAccessToken)
                            .build();
                    response.close(); // Close previous response
                    return chain.proceed(newRequest);
                } else {
                    Log.e("AuthInterceptor", "Failed to refresh token. Forcing logout.");
                    sessionManager.clearSession();
                }
            }
        }

        return response;
    }
}