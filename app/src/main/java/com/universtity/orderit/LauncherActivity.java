package com.universtity.orderit;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.universtity.orderit.auth.presentation.LoginActivity;
import com.universtity.orderit.auth.presentation.session.SessionViewModel;

import com.universtity.orderit.core.utils.JwtUtils;
import com.universtity.orderit.navigation.RoleNavigator;
import com.universtity.orderit.core.utils.SessionManager;


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
                        launchByRole();
//                        startActivity(new Intent(this, MainActivity.class));
//                        finish();
                    }
                }
        );

        SessionViewModel sessionViewModel = new ViewModelProvider(this).get(SessionViewModel.class);

        sessionViewModel.getIsAuthenticated().observe(this, isAuthenticated -> {
            if (isAuthenticated) {
                launchByRole();
//                startActivity(new Intent(this, MainActivity.class));
//                finish();
            } else {
                loginLauncher.launch(new Intent(this, LoginActivity.class));
//                Intent loginIntent = new Intent(this, LoginActivity.class);
//                loginLauncher.launch(loginIntent);
            }
        });
    }

    private void launchByRole() {
        SessionManager sessionManager = new SessionManager(this);
        String token = sessionManager.getAccessToken();

        if (token != null) {
            String role = JwtUtils.extractUserRole(token);
            assert role != null;
            Intent intent = RoleNavigator.getStartIntent(this, role);
            startActivity(intent);
            finish();
        } else {
            // Force login again
            loginLauncher.launch(new Intent(this, LoginActivity.class));
        }
    }
}
