package com.universtity.orderit.waiter.domain.repository;

import com.universtity.orderit.waiter.domain.model.Category;
import com.universtity.orderit.waiter.domain.model.Product;

import java.util.List;

public interface MenuRepository {
    interface MenuCallback {
        void onSuccess(List<Category> categories);
        void onError(Throwable t);
    }

    void getCategories(MenuCallback callback);

    interface ProductCallback {
        void onSuccess(List<Product> products);
        void onError(Throwable t);
    }

    void getProductsByCategory(int categoryId, ProductCallback callback);

}