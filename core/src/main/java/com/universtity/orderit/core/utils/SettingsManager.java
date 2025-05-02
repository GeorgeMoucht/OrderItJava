package com.universtity.orderit.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
    private static final String PREFS_NAME = "settings_prefs";
    private static final String KEY_BASE_URL = "key_base_url";
    private static final String DEFAULT_BASE_URL = "http://10.0.2.2:8000/api/";

    private final SharedPreferences sharedPreferences;

    public SettingsManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setBaseUrl(String baseUrl) {
        sharedPreferences.edit().putString(KEY_BASE_URL, baseUrl).apply();
    }

    public String getBaseUrl() {
        return sharedPreferences.getString(KEY_BASE_URL, DEFAULT_BASE_URL);
    }
}
