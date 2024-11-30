package com.stock.control.controllers;

import com.stock.control.entities.Category;
import com.stock.control.entities.Supplier;
import com.stock.control.model.request.ProductRequest;
import com.stock.control.model.response.ProductResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestData {
    public static ProductResponse getProductResponse(Long id, String name, String price) {
        ProductResponse product = new ProductResponse();
        product.setProductId(id);
        product.setProductName(name);
        product.setDescription("This is a mocked product description.");
        product.setPrice(new BigDecimal(price));
        product.setQuantity(10);
        product.setCategory(TestData.getCategory());
        product.setSupplier(TestData.getSupplier());
        product.setCreatedAt(LocalDateTime.now().minusDays(1));
        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }

    public static ProductRequest getProductRequest(String name, String price) {
        ProductRequest product = new ProductRequest();
        product.setProductName(name);
        product.setDescription("This is a mocked product description.");
        product.setPrice(new BigDecimal(price));
        product.setQuantity(10);
        product.setCategoryId(1L);
        product.setSupplierId(1L);
//        product.setCreatedAt(LocalDateTime.now().minusDays(1));
//        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }

    public static Category getCategory() {
        Category category = new Category();
        category.setCategoryId(10L);
        category.setCategoryName("category 1");
        category.setDescription("category description");
        return category;
    }

    public static Supplier getSupplier() {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(10L);
        supplier.setSupplierName("supplier 1");
        supplier.setContactInfo("supplier contact description");
        return supplier;
    }
}
