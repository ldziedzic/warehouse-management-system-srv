package com.dziedzic.warehouse.repository;

import com.dziedzic.warehouse.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findOneByManufacturerNameAndModelName(String manufacturer, String model);
    List<Product> findAll();
    List<Product> findAllByActive(Boolean active);
}
