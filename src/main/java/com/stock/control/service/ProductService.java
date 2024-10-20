package com.stock.control.service;

import com.stock.control.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    Optional<Product> update(Long id, Product product);

    void deleteById(Long id);

    void saveAll(List<Product> products);
}
