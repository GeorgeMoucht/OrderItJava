package com.universtity.orderit.waiter.data.mapper;

import com.universtity.orderit.waiter.data.model.ProductDto;
import com.universtity.orderit.waiter.domain.model.Product;

public class ProductMapper {

    public static Product toDomain(ProductDto dto) {
        return new Product(
                dto.id,
                dto.name,
                dto.description,
                dto.price,
                dto.image
        );
    }
}
