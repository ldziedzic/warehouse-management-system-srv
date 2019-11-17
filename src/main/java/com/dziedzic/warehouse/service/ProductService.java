package com.dziedzic.warehouse.service;

import com.dziedzic.warehouse.model.Product;
import com.dziedzic.warehouse.model.User;
import com.dziedzic.warehouse.repository.ProductRepository;
import com.dziedzic.warehouse.service.dto.ProductEditDTO;
import com.dziedzic.warehouse.service.dto.ProductQuantityDTO;
import com.dziedzic.warehouse.service.dto.ProductDTO;
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

    public List<Product> getAllProducts(User user) {
        log.info("Getting all products.");
        if (user.getRole().equals("manager"))
            return productRepository.findAll();
        return productRepository.findAllByActive(true);
    }

    public void addNewProduct(Product product) {
        log.info("Adding new product: " + product.getModelName() + " " + product.getManufacturerName());

        if (getProduct(product.getModelName(), product.getManufacturerName()).isPresent()) {
            log.info("Error. Product " + product.getModelName() + " " + product.getManufacturerName() + "exists");
        } else {
            productRepository.save(product);
        }

    }

    public Optional<Product> editProduct(ProductEditDTO productEditDTO) {
        String modelName = productEditDTO.getModelName();
        String manufacturerName = productEditDTO.getManufacturerName();

        Optional<Product> product = getProduct(modelName, manufacturerName);
        if (product.isEmpty()) return Optional.empty();

        if (getProduct(productEditDTO.getNewModelName(), productEditDTO.getNewManufacturerName()).isPresent()) {
            log.info("Product " + productEditDTO.getNewModelName() + " " + productEditDTO.getNewManufacturerName() + "exists");
            return Optional.empty();
        }

        product.get().setModelName(productEditDTO.getNewModelName());
        product.get().setManufacturerName(productEditDTO.getNewManufacturerName());
        product.get().setPrice(productEditDTO.getPrice());

        productRepository.save(product.get());
        return product;
    }

    public Optional<Product> changeProductQuantity(ProductQuantityDTO productQuantityDTO) {
        String modelName = productQuantityDTO.getModelName();
        String manufacturerName = productQuantityDTO.getManufacturerName();
        int quantity = productQuantityDTO.getQuantity();
        log.info("Changing quantity of product " + manufacturerName + " " + modelName + "by " + quantity);

        Optional<Product> product = getProduct(modelName, manufacturerName);
        if (product.isEmpty()) return Optional.empty();

        product.get().changeQuantity(quantity);

        productRepository.save(product.get());
        return product;
    }

    public Optional<Product> deactivateProduct(ProductDTO productDTO) {
        String modelName = productDTO.getModelName();
        String manufacturerName = productDTO.getManufacturerName();

        log.info("Deactivating product " + manufacturerName + " " + modelName);

        return changeProductActivation(modelName, manufacturerName, false);
    }

    public Optional<Product> activateProduct(ProductDTO productDTO) {
        String modelName = productDTO.getModelName();
        String manufacturerName = productDTO.getManufacturerName();

        log.info("Activating product " + manufacturerName + " " + modelName);

        return changeProductActivation(modelName, manufacturerName, true);
    }

    private Optional<Product> changeProductActivation(String modelName, String manufacturerName, boolean isActive) {
        Optional<Product> product = getProduct(modelName, manufacturerName);
        if (product.isEmpty()) return Optional.empty();

        product.get().setActive(isActive);

        productRepository.save(product.get());
        return product;
    }

    private Optional<Product> getProduct(String modelName, String manufacturerName) {
        Optional<Product> product = productRepository.findOneByManufacturerNameAndModelName(manufacturerName, modelName);

        if (product.isEmpty()) {
            log.warn("Failed to get product " + manufacturerName + " " + modelName);
            return Optional.empty();
        }
        return product;
    }
}
