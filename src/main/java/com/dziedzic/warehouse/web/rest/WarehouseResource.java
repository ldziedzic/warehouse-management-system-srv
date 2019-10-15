package com.dziedzic.warehouse.web.rest;

import com.dziedzic.warehouse.model.Product;
import com.dziedzic.warehouse.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseResource {
    private final ProductService productService;

    public WarehouseResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        productService.addNewProduct(product);

    }
}
