package com.universtity.orderit.auth.data.models;

import com.google.gson.annotations.SerializedName;

public final class LoginRequest {

    @SerializedName("username")
    private final String username;

    @SerializedName("password")
    private final String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @SuppressWarnings("unused")
    public String getUsername() {
        return username;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }
}