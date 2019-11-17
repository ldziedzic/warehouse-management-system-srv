package com.dziedzic.warehouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length=100)
    private String manufacturerName;

    @NotNull
    @Column(length=100)
    private String modelName;

    @NotNull
    private float price;

    @NotNull
    private int quantity = 0;

    @NotNull
    private boolean active = true;

    public Product() {
        active = true;
    }

    public Product(@NotNull String manufacturerName, @NotNull String modelName, @NotNull float price, @NotNull int quantity, @NotNull boolean active) {
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
        this.price = price;
        this.quantity = quantity;
        this.active = active;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getModelName() {
        return modelName;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void changeQuantity(int quantity) {
        this.quantity += quantity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

