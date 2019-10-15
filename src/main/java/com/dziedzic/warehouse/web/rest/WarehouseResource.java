package com.dziedzic.warehouse.web.rest;

import com.dziedzic.warehouse.model.Product;
import com.dziedzic.warehouse.service.ProductService;
import com.dziedzic.warehouse.service.dto.ChangeProductQuantityDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public void addProduct(@Valid @RequestBody Product product) {
        productService.addNewProduct(product);
    }

    @PutMapping("/increase_product_quantity")
    public Optional<Product> increaseProductQuantity(
            @Valid @RequestBody ChangeProductQuantityDTO changeProductQuantityDTO) {
        return productService.changeProductQuantity(changeProductQuantityDTO);
    }

    @PutMapping("/decrease_product_quantity")
    public Optional<Product> decreaseProductQuantity(
            @Valid @RequestBody ChangeProductQuantityDTO changeProductQuantityDTO) {
        changeProductQuantityDTO.setQuantity(changeProductQuantityDTO.getQuantity() * (-1));
        return productService.changeProductQuantity(changeProductQuantityDTO);
    }
}
