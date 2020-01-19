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

    @NotNull
    private String guid;

    public ProductDTO() {
    }

    public ProductDTO(@NotNull String manufacturerName, @NotNull String modelName, @NotNull String guid) {
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
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
}

