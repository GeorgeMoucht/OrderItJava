package com.universtity.orderit.waiter.data.mapper;

import com.universtity.orderit.waiter.data.model.CategoryDto;
import com.universtity.orderit.waiter.data.model.ProductDto;
import com.universtity.orderit.waiter.domain.model.Category;
import com.universtity.orderit.waiter.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static Category toDomain(CategoryDto dto) {
        List<Product> products = new ArrayList<>();
        if (dto.products != null) {
            for (ProductDto productDto : dto.products) {
                products.add(ProductMapper.toDomain(productDto));
            }
        }

        return new Category(dto.id, dto.name, products);
    }
}
