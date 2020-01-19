package com.dziedzic.warehouse.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductAddDTO {
    @NotNull
    @Size(max=100)
    private String manufacturerName;

    @NotNull
    @Size(max=100)
    private String modelName;

    private int price;

    @NotNull
    private String guid;

    public ProductAddDTO() {
    }

    public ProductAddDTO(@NotNull String manufacturerName, @NotNull String modelName, int price, @NotNull String guid) {
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
        this.price = price;
        this.guid = guid;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getGuid() {
        return guid;
    }

    public int getPrice() {
        return price;
    }
}

