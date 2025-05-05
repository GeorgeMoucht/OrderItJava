package com.universtity.orderit.waiter.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.universtity.orderit.core.utils.SettingsManager;
import com.universtity.orderit.waiter.data.mapper.TableMapper;
import com.universtity.orderit.waiter.data.model.TableDto;
import com.universtity.orderit.waiter.data.model.TableListResponse;
import com.universtity.orderit.waiter.data.network.WaiterApiService;
import com.universtity.orderit.waiter.domain.model.Table;
import com.universtity.orderit.waiter.domain.repository.WaiterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class WaiterRepositoryImpl implements WaiterRepository {
    private final WaiterApiService api;

    public WaiterRepositoryImpl(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new SettingsManager(context).getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(WaiterApiService.class);
    }

    @Override
    public void getTables(TableCallback callback) {
        api.getTables().enqueue(new Callback<TableListResponse>() {
            @Override
            public void onResponse(@NonNull Call<TableListResponse> call, @NonNull Response<TableListResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().data != null) {
                    List<TableDto> dtoList = response.body().data;
                    List<Table> tables = new ArrayList<>();
//                    for (TableDto dto : dtoList) {
//                        Table.Reserved reserved = dto.reserved != null
//                                ? new Table.Reserved(dto.reserved.id, dto.reserved.name, dto.reserved.time)
//                                : null;
//                        tables.add(new Table(dto.id, dto.name, dto.state, dto.status, reserved));
//                    }
                    for (TableDto dto: dtoList) {
                        tables.add(TableMapper.toDomain(dto));
                    }
                    callback.onSuccess(tables);
                } else {
                    callback.onError(new Exception("Invalid response structure"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TableListResponse> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }
}
