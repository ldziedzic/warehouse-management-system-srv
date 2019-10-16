package com.dziedzic.warehouse.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDTO {
    @NotNull
    @Size(max=100)
    private String manufacturerName;

    @NotNull
    @Size(max=100)
    private String modelName;

    public ProductDTO() {
    }

    public ProductDTO(@NotNull String manufacturerName, @NotNull String modelName) {
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getModelName() {
        return modelName;
    }
}

