package com.dziedzic.warehouse.web.rest;

import com.dziedzic.warehouse.model.Product;
import com.dziedzic.warehouse.service.ProductService;
import com.dziedzic.warehouse.service.dto.ProductQuantityDTO;
import com.dziedzic.warehouse.service.dto.ProductDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/warehouse/products")
public class ProductResource {
    private static final int NEGATIVE_FACTOR = -1;
    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get_products")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add_product")
    public void addProduct(@Valid @RequestBody Product product) {
        productService.addNewProduct(product);
    }

    @PutMapping("/increase_product_quantity")
    public Optional<Product> increaseProductQuantity(
            @Valid @RequestBody ProductQuantityDTO productQuantityDTO) {
        return productService.changeProductQuantity(productQuantityDTO);
    }

    @PutMapping("/decrease_product_quantity")
    public Optional<Product> decreaseProductQuantity(
            @Valid @RequestBody ProductQuantityDTO productQuantityDTO) {
        productQuantityDTO.setQuantity(productQuantityDTO.getQuantity() * NEGATIVE_FACTOR);
        return productService.changeProductQuantity(productQuantityDTO);
    }

    @DeleteMapping("/remove_product")
    public Optional<Product> removeProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.deactivateProduct(productDTO);
    }

    @PutMapping("/restore_product")
    public Optional<Product> restoreProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.activateProduct(productDTO);
    }
}
