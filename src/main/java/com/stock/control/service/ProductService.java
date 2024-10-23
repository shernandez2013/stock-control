package com.stock.control.service;

import com.stock.control.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(Long id);

    Product save(Product product);

    Product update(Long id, Product product);

    void deleteById(Long id);

    void saveAll(List<Product> products);
}
