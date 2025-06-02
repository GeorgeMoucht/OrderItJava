package com.universtity.orderit.waiter.data.model;

import com.google.gson.annotations.SerializedName;

public class ProductDto {
    public int id;
    public String name;
    public String description;
    public double price;

    @SerializedName("category_id")
    public int categoryId;
    @SerializedName("image")
    public String image;
}