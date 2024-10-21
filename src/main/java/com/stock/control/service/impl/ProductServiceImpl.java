package com.stock.control.service.impl;

import com.stock.control.entities.Category;
import com.stock.control.entities.Product;
import com.stock.control.repositories.CategoryRepository;
import com.stock.control.repositories.ProductRepository;
import com.stock.control.repositories.SupplierRepository;
import com.stock.control.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        if (!categoryRepository.existsById(product.getCategory().getCategoryId())) {
            throw new IllegalArgumentException("Invalid category");
        }
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> update(Long id, Product updatedProduct) {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid product id " + id);
        }
        Product existingProduct = existingProductOpt.get();
        if (!categoryRepository.existsById(updatedProduct.getCategory().getCategoryId())) {
            throw new IllegalArgumentException("Invalid category.");
        }

        if (!supplierRepository.existsById(updatedProduct.getSupplier().getSupplierId())) {
            throw new IllegalArgumentException("Invalid supplier.");
        }

            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setSupplier(updatedProduct.getSupplier());
            existingProduct.setUpdatedAt(LocalDateTime.now());
            return Optional.of(productRepository.save(existingProduct));

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
           Optional<Category> category = categoryRepository.findById(product.get().getCategory().getCategoryId());
           if (category.isPresent()) {
               productRepository.deleteById(id);
           }else{
               throw new IllegalArgumentException("Invalid category");
           }
        }else{
            throw new IllegalArgumentException("Invalid product");
        }
    }

    @Override
    @Transactional
    public void saveAll(List<Product> products) {
         productRepository.saveAll(products);
    }
}
