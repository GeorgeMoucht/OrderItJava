package com.universtity.orderit.waiter.data.network;

import com.universtity.orderit.waiter.data.model.TableListResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WaiterApiService {
    @GET("tables/")
    Call<TableListResponse> getTables();

}
