package com.universtity.orderit.waiter.domain.repository;

import com.universtity.orderit.waiter.domain.model.Table;

import java.util.List;

public interface WaiterRepository {
    interface TableCallback {
        void onSuccess(List<Table> tables);
        void onError(Throwable t);
    }

    void getTables(TableCallback callback);
}