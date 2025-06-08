package com.universtity.orderit.waiter.domain.model;

import java.util.List;
import java.util.Objects;

public class Category {
    private final int id;
    private final String name;
    private final List<Product> products;

    public Category(int id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<Product> getProducts() { return products; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}