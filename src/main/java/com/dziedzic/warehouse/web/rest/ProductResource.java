package com.dziedzic.warehouse.web.rest;

import com.dziedzic.warehouse.model.Product;
import com.dziedzic.warehouse.model.User;
import com.dziedzic.warehouse.security.TokenProvider;
import com.dziedzic.warehouse.service.ProductService;
import com.dziedzic.warehouse.service.RequestService;
import com.dziedzic.warehouse.service.UserService;
import com.dziedzic.warehouse.service.dto.ProductAddDTO;
import com.dziedzic.warehouse.service.dto.ProductEditDTO;
import com.dziedzic.warehouse.service.dto.ProductQuantityDTO;
import com.dziedzic.warehouse.service.dto.ProductDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/warehouse/products")
public class ProductResource {
    private static final int NEGATIVE_FACTOR = -1;
    private final ProductService productService;
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RequestService requestService;

    public ProductResource(ProductService productService, UserService userService, TokenProvider tokenProvider, RequestService requestService) {
        this.productService = productService;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.requestService = requestService;
    }

    @GetMapping("/get_products")
    public List<Product> getProducts(HttpServletRequest request) {
        String token = tokenProvider.getJwtFromRequest(request);
        User user = userService.getUserByJwtToken(token);
        return productService.getAllProducts(user);
    }

    @PostMapping("/add_product")
    public void addProduct(@RequestBody ProductAddDTO product) {
        if (requestService.checkIfRequestPerformed(product.getGuid()))
            return;
        requestService.markRequestAsPerformed(product.getGuid());
        productService.addNewProduct(new Product(product.getManufacturerName(), product.getModelName(), product.getPrice(), 0, true));
    }

    @PutMapping("/edit_product")
    public Optional<Product> decreaseProductQuantity(@RequestBody ProductEditDTO productEditDTO) {
        if (requestService.checkIfRequestPerformed(productEditDTO.getGuid()))
            return Optional.empty();
        requestService.markRequestAsPerformed(productEditDTO.getGuid());
        return productService.editProduct(productEditDTO);
    }

    @PutMapping("/increase_product_quantity")
    public Optional<Product> increaseProductQuantity(
            @RequestBody ProductQuantityDTO productQuantityDTO) {
        if (requestService.checkIfRequestPerformed(productQuantityDTO.getGuid()))
            return Optional.empty();
        requestService.markRequestAsPerformed(productQuantityDTO.getGuid());
        return productService.changeProductQuantity(productQuantityDTO);
    }

    @PutMapping("/decrease_product_quantity")
    public Optional<Product> decreaseProductQuantity(
            @RequestBody ProductQuantityDTO productQuantityDTO) {
        if (requestService.checkIfRequestPerformed(productQuantityDTO.getGuid()))
            return Optional.empty();
        requestService.markRequestAsPerformed(productQuantityDTO.getGuid());
        productQuantityDTO.setQuantity(productQuantityDTO.getQuantity() * NEGATIVE_FACTOR);
        return productService.changeProductQuantity(productQuantityDTO);
    }

    @PutMapping("/remove_product")
    public Optional<Product> removeProduct(@RequestBody ProductDTO productDTO) {
        if (requestService.checkIfRequestPerformed(productDTO.getGuid()))
            return Optional.empty();
        requestService.markRequestAsPerformed(productDTO.getGuid());
        return productService.deactivateProduct(productDTO);
    }

    @PutMapping("/restore_product")
    public Optional<Product> restoreProduct(@RequestBody ProductDTO productDTO) {
        if (requestService.checkIfRequestPerformed(productDTO.getGuid()))
            return Optional.empty();
        requestService.markRequestAsPerformed(productDTO.getGuid());
        return productService.activateProduct(productDTO);
    }
}
