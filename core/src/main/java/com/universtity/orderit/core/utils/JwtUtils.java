package com.universtity.orderit.core.utils;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
public class JwtUtils {
    public static boolean isTokenExpired(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                return true; // Invalid token structure
            }

            String payloadJson = new String(Base64.decode(parts[1], Base64.URL_SAFE), StandardCharsets.UTF_8);
            JSONObject payload = new JSONObject(payloadJson);

            long exp = payload.getLong("exp");
            long now = System.currentTimeMillis() / 1000;

            Log.d("JwtUtils", "Token expiration : " + exp + ", now: " + now);

            return exp < now;
        } catch (JSONException | IllegalArgumentException e) {
            Log.e("JwtUtils", "Error decoding or parsing JWT", e);
            return true;
        }
    }

    public static String extractUserRole(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                return null;
            }

            String payloadJson = new String(Base64.decode(parts[1], Base64.URL_SAFE), StandardCharsets.UTF_8);
            JSONObject payload = new JSONObject(payloadJson);

            return payload.optString("role", null);
        } catch (JSONException | IllegalArgumentException e) {
            Log.e("JwtUtils", "Error extracting role from JWT", e);
            return null;
        }
    }
}
