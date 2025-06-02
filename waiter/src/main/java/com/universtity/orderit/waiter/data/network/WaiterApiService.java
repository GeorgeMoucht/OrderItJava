package com.universtity.orderit.waiter.data.network;

import com.universtity.orderit.waiter.data.model.ProductListResponse;
import com.universtity.orderit.waiter.data.model.TableListResponse;
import com.universtity.orderit.waiter.data.model.CategoryListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WaiterApiService {

    @GET("tables/")
    Call<TableListResponse> getTables();

    @GET("menu-categories/")
    Call<CategoryListResponse> getCategories();

    @GET("menu-categories/{id}/products/")
    Call<ProductListResponse> getProductsByCategory(@Path("id") int categoryId);



}
