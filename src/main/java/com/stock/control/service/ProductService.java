package com.stock.control.service;

import com.stock.control.entities.Product;
import com.stock.control.model.request.ProductRequest;
import com.stock.control.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    ProductResponse save(ProductRequest product);

    ProductResponse update(Long id, ProductRequest product);

    void deleteById(Long id);

    void saveAll(List<ProductRequest> products);
}
