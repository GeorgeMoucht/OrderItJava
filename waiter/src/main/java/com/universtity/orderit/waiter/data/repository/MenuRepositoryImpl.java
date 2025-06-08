package com.universtity.orderit.waiter.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.universtity.orderit.core.utils.SettingsManager;
import com.universtity.orderit.waiter.data.mapper.CategoryMapper;
import com.universtity.orderit.waiter.data.mapper.ProductMapper;
import com.universtity.orderit.waiter.data.model.CategoryDto;
import com.universtity.orderit.waiter.data.model.CategoryListResponse;
import com.universtity.orderit.waiter.data.model.ProductDto;
import com.universtity.orderit.waiter.data.model.ProductListResponse;
import com.universtity.orderit.waiter.data.network.WaiterApiService;
import com.universtity.orderit.waiter.domain.model.Category;
import com.universtity.orderit.waiter.domain.model.Product;
import com.universtity.orderit.waiter.domain.repository.MenuRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuRepositoryImpl implements MenuRepository {

    private final WaiterApiService api;

    public MenuRepositoryImpl(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new SettingsManager(context).getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(WaiterApiService.class);
    }

    @Override
    public void getCategories(MenuCallback callback) {
        api.getCategories().enqueue(new Callback<CategoryListResponse>() {

            @Override
            public void onResponse(@NonNull Call<CategoryListResponse> call, @NonNull Response<CategoryListResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().data != null) {
                    List<CategoryDto> dtoList = response.body().data;
                    List<Category> categories = new ArrayList<>();
                    for (CategoryDto dto : dtoList) {
                        categories.add(CategoryMapper.toDomain(dto));
                    }
                    callback.onSuccess(categories);
                    Log.d("MenuRepositoryImpl", "Received categories: " + dtoList.size());

                } else {
                    callback.onError(new Exception("Invalid response from server"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryListResponse> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void getProductsByCategory(int categoryId, ProductCallback callback) {
        api.getProductsByCategory(categoryId).enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductListResponse> call, @NonNull Response<ProductListResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().data != null) {
                    List<ProductDto> dtoList = response.body().data;
                    List<Product> products = new ArrayList<>();
                    for (ProductDto dto : dtoList) {
                        products.add(ProductMapper.toDomain(dto));
                    }
                    callback.onSuccess(products);
                } else {
                    callback.onError(new Exception("Invalid product response"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductListResponse> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

}
