package com.dziedzic.warehouse.service;

import com.dziedzic.warehouse.model.Product;
import com.dziedzic.warehouse.repository.ProductRepository;
import com.dziedzic.warehouse.service.dto.ChangeProductQuantityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        String modelName = product.getModelName();
        String manufacturerName = product.getManufacturerName();
        log.info("Adding new product: " + manufacturerName + " " + modelName);
        productRepository.save(product);
    }

    public Optional<Product> changeProductQuantity(ChangeProductQuantityDTO productQuantityDTO) {
        String modelName = productQuantityDTO.getModelName();
        String manufacturerName = productQuantityDTO.getManufacturerName();
        int quantity = productQuantityDTO.getQuantity();
        log.info("Changing quantity of product" + manufacturerName + " " + modelName + "by " + quantity);

        Optional<Product> product = productRepository.findOneByManufacturerNameAndModelName(manufacturerName, modelName);

        if (product.isEmpty()) {
            log.warn("Failed to get product " + manufacturerName + " " + modelName);
            return Optional.empty();
        }
        product.get().changeQuantity(quantity);

        productRepository.save(product.get());
        return product;
    }
}
