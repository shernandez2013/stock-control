package com.stock.control.service.impl;

import com.stock.control.entities.Product;
import com.stock.control.exception.NotFoundException;
import com.stock.control.exception.ProcessingException;
import com.stock.control.repositories.CategoryRepository;
import com.stock.control.repositories.ProductRepository;
import com.stock.control.repositories.SupplierRepository;
import com.stock.control.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Product> findAll() {
        try {
            return productRepository.findAll();
        } catch (DataAccessException e) {
            log.error("Error while fetching all products", e);
            throw new ProcessingException("An error occurred while fetching products from the database", e);
        }

    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    }

    @Override
    @Transactional
    public Product save(Product product) throws IllegalArgumentException {
        if (product == null || product.getCategory() == null || product.getCategory().getCategoryId() == null) {
            throw new IllegalArgumentException("Product or category information is missing");
        }

        if (!categoryRepository.existsById(product.getCategory().getCategoryId())) {
            throw new IllegalArgumentException("Invalid category ID: " + product.getCategory().getCategoryId());
        }
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(Long id, Product updatedProduct) {

        if (updatedProduct == null || updatedProduct.getCategory() == null || updatedProduct.getSupplier() == null) {
            throw new IllegalArgumentException("Updated product or its dependencies (category or supplier) are missing");
        }

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));

        if (!categoryRepository.existsById(updatedProduct.getCategory().getCategoryId())) {
            throw new NotFoundException("Invalid category ID: " + updatedProduct.getCategory().getCategoryId());
        }

        if (!supplierRepository.existsById(updatedProduct.getSupplier().getSupplierId())) {
            throw new NotFoundException("Invalid supplier ID: " + updatedProduct.getSupplier().getSupplierId());
        }

        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setSupplier(updatedProduct.getSupplier());
        existingProduct.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(existingProduct);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));

        if (!categoryRepository.existsById(product.getCategory().getCategoryId())) {
            throw new NotFoundException("Invalid category with id " + product.getCategory().getCategoryId());
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveAll(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("The product list cannot be null or empty");
        }
        products.forEach(product -> {
            if (product.getCategory() == null || !categoryRepository.existsById(product.getCategory().getCategoryId())) {
                throw new NotFoundException("Invalid category for product: " + product.getProductName());
            }

            if (product.getSupplier() == null || !supplierRepository.existsById(product.getSupplier().getSupplierId())) {
                throw new NotFoundException("Invalid supplier for product: " + product.getProductName());
            }

            if (product.getCreatedAt() == null) {
                product.setCreatedAt(LocalDateTime.now());
            }
        });
        productRepository.saveAll(products);
    }
}
