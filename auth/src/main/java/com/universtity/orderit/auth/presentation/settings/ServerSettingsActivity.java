package com.universtity.orderit.auth.presentation.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.universtity.orderit.auth.R;
import com.universtity.orderit.auth.data.network.AuthApiClient;
import com.universtity.orderit.core.utils.SettingsManager;


public class ServerSettingsActivity extends AppCompatActivity {
    private EditText editTextBaseUrl;
    private SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.universtity.orderit.auth.R.layout.activity_server_settings);

        applyFadeInAnimation();
        settingsManager = new SettingsManager(this);
        setupViews();
    }

    private void setupViews() {
        editTextBaseUrl = findViewById(R.id.editTextBaseUrl);
        Button buttonSave = findViewById(R.id.buttonSaveUrl);
        ImageView imageViewBack = findViewById(R.id.imageViewBack);

        editTextBaseUrl.setText(settingsManager.getBaseUrl());

        buttonSave.setOnClickListener(v -> handleSave());
        imageViewBack.setOnClickListener(v -> finish());
    }

    private void handleSave() {
        String inputUrl = editTextBaseUrl.getText().toString().trim();
        String sanitizedUrl = inputUrl.endsWith("/") ? inputUrl : inputUrl + "/";

        if (isValidUrl(sanitizedUrl)) {
            settingsManager.setBaseUrl(sanitizedUrl);
            AuthApiClient.reset();
            Snackbar.make(findViewById(android.R.id.content), "Base URL updated. Restarting app...", Snackbar.LENGTH_LONG).show();
            restartApp();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Invalid URL format.", Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    private void restartApp() {
        Intent intent = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity(); // Finish all activities
        }
    }

    private void applyFadeInAnimation() {
        View root = findViewById(android.R.id.content);
        root.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }
}
