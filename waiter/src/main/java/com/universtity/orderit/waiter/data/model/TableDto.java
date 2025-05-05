package com.universtity.orderit.waiter.data.model;

import com.google.gson.annotations.SerializedName;

public class TableDto {
    public int id;
    public String name;
    public int state;
    public String status;
    public ReservedDto reserved;

    public static class ReservedDto {
        public int id;
        public String name;
        public String time;

        @SerializedName("created_at")
        public String createdAt;

        @SerializedName("updated_at")
        public String updatedAt;
    }
}
