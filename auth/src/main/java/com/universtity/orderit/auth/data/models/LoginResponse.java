package com.universtity.orderit.auth.data.models;

import com.google.gson.annotations.SerializedName;

public final class LoginResponse {
    @SerializedName("access")
    private final String accessToken;

    @SerializedName("refresh")
    private final String refreshToken;

    private LoginResponse() {
        this.accessToken = null;
        this.refreshToken = null;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
