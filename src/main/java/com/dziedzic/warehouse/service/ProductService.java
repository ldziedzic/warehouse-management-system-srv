package com.dziedzic.warehouse.service;

import com.dziedzic.warehouse.model.Product;
import com.dziedzic.warehouse.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        log.info("Getting all products.");
        return productRepository.findAll();
    }

    public void addNewProduct(Product product) {
        String productName = product.getModelName();
        String productManufacturer = product.getManufacturerName();
        log.info("Adding new product: " + productManufacturer + " " + productName);
        productRepository.save(product);
    }
}
