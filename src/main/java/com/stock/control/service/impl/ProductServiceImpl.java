package com.stock.control.service.impl;

import com.stock.control.entities.Product;
import com.stock.control.repositories.ProductRepository;
import com.stock.control.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> update(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            existingProduct.setCategoryId(updatedProduct.getCategoryId());
            existingProduct.setSupplierId(updatedProduct.getSupplierId());
            return productRepository.save(existingProduct);
        });
    }

    @Override
    public void deleteById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Product id = " + id + " not found.");
        }

    }

    @Override
    public void saveAll(List<Product> products) {
         productRepository.saveAll(products);
    }
}
