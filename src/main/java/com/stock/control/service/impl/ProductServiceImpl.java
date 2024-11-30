package com.stock.control.service.impl;

import com.stock.control.entities.Category;
import com.stock.control.entities.Product;
import com.stock.control.entities.Supplier;
import com.stock.control.exception.NotFoundException;
import com.stock.control.exception.ProcessingException;
import com.stock.control.model.request.ProductRequest;
import com.stock.control.model.response.ProductResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ProductResponse> findAll() {
        try {
            List<Product> products = productRepository.findAll();
            return mapToResponseList(products);
        } catch (DataAccessException e) {
            log.error("Error while fetching all products", e);
            throw new ProcessingException("An error occurred while fetching products from the database", e);
        }
    }

    @Override
    public ProductResponse findById(Long id) {
        return mapToResponse(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id)));
    }

    @Override
    @Transactional
    public ProductResponse save(ProductRequest productRequest) throws IllegalArgumentException {
        if (productRequest == null) {
            throw new IllegalArgumentException("Product information is missing");
        }
        Product product = generateProductFromProductRequest(productRequest);
        return mapToResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest productRequest) {
        if (productRequest == null) {
            throw new IllegalArgumentException("Updated product information is missing");
        }
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));
        validateCategoryAndSupplier(productRequest, existingProduct);
        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(productRepository.save(existingProduct));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public void saveAll(List<ProductRequest> productRequests) {
        if (productRequests == null || productRequests.isEmpty()) {
            throw new IllegalArgumentException("The product list cannot be null or empty");
        }
        List<Product> products = new ArrayList<>();
        productRequests.forEach(productRequest -> products.add(generateProductFromProductRequest(productRequest)));
        productRepository.saveAll(products);
    }

    private Product generateProductFromProductRequest(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        validateCategoryAndSupplier(productRequest, product);
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }

    private void validateCategoryAndSupplier(ProductRequest productRequest, Product existingProduct) {
        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId()).orElse(null);
            existingProduct.setCategory(category);
        } else {
            existingProduct.setCategory(null);
        }
        if (productRequest.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productRequest.getSupplierId()).orElse(null);
            existingProduct.setSupplier(supplier);
        } else {
            existingProduct.setSupplier(null);
        }
    }

    private List<ProductResponse> mapToResponseList(List<Product> products) {
        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setCategory(product.getCategory());
        response.setSupplier(product.getSupplier());
        return response;
    }
}
