package com.universtity.orderit.auth.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.universtity.orderit.auth.R;
import com.universtity.orderit.auth.presentation.settings.ServerSettingsActivity;

public class LoginActivity extends AppCompatActivity {

    private int tapCount = 0;
    private long lastTapTime = 0;
    private LoginViewModel loginViewModel;
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        startFadeInAnimation();
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(this)).get(LoginViewModel.class);

        setupViews();
    }

    private void setupViews() {
        TextView textViewTitle = findViewById(R.id.textViewTitle);

        textViewTitle.setOnClickListener(v -> {
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastTapTime <= 1500) {
                tapCount++;
            } else {
                tapCount = 1;
            }

            lastTapTime = currentTime;

            if (tapCount == 5) {
                tapCount = 0; // reset

                // Launch the hidden settings activity
                startActivity(new Intent(LoginActivity.this, ServerSettingsActivity.class));
            }
        });


        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressBar);

        // Disable login button initially
        buttonLogin.setEnabled(false);

        // Progressive fade-in animations
        animateView(textViewTitle, 0);
        animateView(editTextUsername, 150);
        animateView(editTextPassword, 300);
        animateView(buttonLogin, 450);

        observeLoginViewModel();

        buttonLogin.setOnClickListener(v -> {
            buttonLogin.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click));
            loginViewModel.login(
                    editTextUsername.getText().toString().trim(),
                    editTextPassword.getText().toString().trim()
            );
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateFields();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editTextUsername.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);

        editTextUsername.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                editTextPassword.requestFocus();
                return true;
            }
            return false;
        });
    }

    private void observeLoginViewModel() {
        loginViewModel.getIsLoading().observe(this, isLoading -> {
            boolean loading = isLoading != null && isLoading;
            if (isLoading != null) {
//                boolean loading = isLoading;

                if (loading) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.animate().alpha(1f).setDuration(300).start();
                    hideKeyboard();
                } else {
                    progressBar.animate().alpha(0f).setDuration(300)
                            .withEndAction(() -> progressBar.setVisibility(View.GONE)).start();
                }

                editTextUsername.setEnabled(!loading);
                editTextPassword.setEnabled(!loading);
                buttonLogin.setEnabled(!loading && areFieldsValid());
            }
        });

        loginViewModel.getLoginResult().observe(this, result -> {
            if (result != null && result.equals("success")) {
                setResult(RESULT_OK);
                finish();
            }
        });

        loginViewModel.getToastMessage().observe(this, message -> {
            if (message != null) {
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void startFadeInAnimation() {
        View rootView = findViewById(android.R.id.content);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        rootView.startAnimation(fadeIn);
    }

    private void animateView(View view, int delay) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animation.setStartOffset(delay);
        view.startAnimation(animation);
    }

    private void validateFields() {
        buttonLogin.setEnabled(areFieldsValid());
    }

    private boolean areFieldsValid() {
        return !editTextUsername.getText().toString().trim().isEmpty() &&
                !editTextPassword.getText().toString().trim().isEmpty();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
