package com.universtity.orderit.auth.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.universtity.orderit.auth.data.AuthRepositoryImpl;
import com.universtity.orderit.core.utils.SessionManager;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public LoginViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }


    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(
                    new AuthRepositoryImpl(),
                    new SessionManager(context)
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
