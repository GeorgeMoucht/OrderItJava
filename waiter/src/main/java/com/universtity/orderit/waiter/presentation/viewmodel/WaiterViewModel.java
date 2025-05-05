package com.universtity.orderit.waiter.presentation.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.universtity.orderit.waiter.data.repository.WaiterRepositoryImpl;
import com.universtity.orderit.waiter.domain.model.Table;
import com.universtity.orderit.waiter.domain.repository.WaiterRepository;

import java.util.List;

public class WaiterViewModel extends AndroidViewModel {
    private final WaiterRepository repository;

    private final MutableLiveData<List<Table>> tables = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public WaiterViewModel(@NonNull Application application) {
        super(application);
        this.repository = new WaiterRepositoryImpl(application.getApplicationContext());
    }

    public LiveData<List<Table>> getTables() {
        return tables;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void fetchTables() {
        isLoading.setValue(true);
        repository.getTables(new WaiterRepository.TableCallback() {
            @Override
            public void onSuccess(List<Table> data) {
                isLoading.postValue(false);
                tables.postValue(data);
            }

            @Override
            public void onError(Throwable t) {
                isLoading.postValue(false);
                error.postValue("Failed to fetch tables. Please try again.");
                Log.e("WaiterViewModel", "Fetch error", t);
            }
        });
    }
}
