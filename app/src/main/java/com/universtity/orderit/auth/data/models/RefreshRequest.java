package com.universtity.orderit.auth.data.models;

import com.google.gson.annotations.SerializedName;

public final class RefreshRequest {
    @SerializedName("refresh")
    private final String refreshToken;

    public RefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @SuppressWarnings("unused")
    public String getRefreshToken() {
        return refreshToken;
    }
}
