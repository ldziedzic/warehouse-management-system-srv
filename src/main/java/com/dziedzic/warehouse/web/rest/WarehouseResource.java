package com.dziedzic.warehouse.web.rest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseResource {

    @GetMapping("/products")
    public String getProducts() {
        return "index data";
    }
}
