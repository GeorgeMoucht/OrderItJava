package com.universtity.orderit;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.universtity.orderit.auth.presentation.LoginActivity;
import com.universtity.orderit.auth.presentation.session.SessionViewModel;

public class LauncherActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> loginLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register the launcher before any flow start
        loginLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                }
        );

        SessionViewModel sessionViewModel = new ViewModelProvider(this).get(SessionViewModel.class);

        sessionViewModel.getIsAuthenticated().observe(this, isAuthenticated -> {
            if (isAuthenticated) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginLauncher.launch(loginIntent);
            }
        });
    }
}
