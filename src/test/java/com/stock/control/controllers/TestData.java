package com.stock.control.controllers;

import com.stock.control.entities.Category;
import com.stock.control.entities.Product;
import com.stock.control.entities.Supplier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestData {
    public static Product getProduct(Long id, String name, String price) {
        Product product = new Product();
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
