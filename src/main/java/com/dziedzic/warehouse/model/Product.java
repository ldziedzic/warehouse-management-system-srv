package com.dziedzic.warehouse.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="manufacturer_name", length=100)
    private String manufacturerName;

    @NotNull
    @Column(name="model_name", length=100)
    private String modelName;

    @NotNull
    @Column(name="price")
    private float price;

    @NotNull
    @Column(name="quantity")
    private int quantity;
}

