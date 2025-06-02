package com.universtity.orderit.waiter.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.universtity.orderit.waiter.data.repository.MenuRepositoryImpl;
import com.universtity.orderit.waiter.domain.model.Category;
import com.universtity.orderit.waiter.domain.model.Product;
import com.universtity.orderit.waiter.domain.repository.MenuRepository;

import java.util.List;

public class MenuViewModel extends AndroidViewModel {

    private final MenuRepository repository;

    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public MenuViewModel(@NonNull Application application) {
        super(application);
        this.repository = new MenuRepositoryImpl(application.getApplicationContext());
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchCategories() {
        isLoading.setValue(true);
        repository.getCategories(new MenuRepository.MenuCallback() {
            @Override
            public void onSuccess(List<Category> data) {
                isLoading.postValue(false);
                categories.postValue(data);
            }

            @Override
            public void onError(Throwable t) {
                isLoading.postValue(false);
                error.postValue("Αποτυχία φόρτωσης κατηγοριών. Προσπαθήστε ξανά.");
            }
        });
    }

    public void fetchProductsByCategory(int categoryId) {
        isLoading.setValue(true);
        repository.getProductsByCategory(categoryId, new MenuRepository.ProductCallback() {
            @Override
            public void onSuccess(List<Product> data) {
                isLoading.postValue(false);
                products.postValue(data);
            }

            @Override
            public void onError(Throwable t) {
                isLoading.postValue(false);
                error.postValue("Αποτυχία φόρτωσης προϊόντων.");
            }
        });
    }

}
