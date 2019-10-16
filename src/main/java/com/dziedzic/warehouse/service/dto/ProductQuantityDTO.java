package com.dziedzic.warehouse.service.dto;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductQuantityDTO {
    @NotNull
    @Size(max=100)
    private String manufacturerName;

    @NotNull
    @Size(max=100)
    private String modelName;

    @NotNull
    @Min(value = 0, message = "The value must be positive")
    private int quantity;

    public ProductQuantityDTO() {
    }

    public ProductQuantityDTO(@NotNull String manufacturerName, @NotNull String modelName,
                              @NotNull Integer quantity) {
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
        this.quantity = quantity;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getModelName() {
        return modelName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

