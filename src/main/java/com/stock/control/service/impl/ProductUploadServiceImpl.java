package com.stock.control.service.impl;

import com.stock.control.entities.Product;
import com.stock.control.service.ProductService;
import com.stock.control.service.ProductUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductUploadServiceImpl implements ProductUploadService {
    private final ProductService productService;

    @Autowired
    public ProductUploadServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Product> uploadProducts(MultipartFile file) throws Exception {
        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv")) {
            throw new Exception("The file is empty or not a CSV file.");
        }
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            if ((line = reader.readLine()) != null) {
                // Esto puede ser usado para leer encabezados si es necesario
            }
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) {
                    continue;
                }
                Product product = new Product();
                product.setProductName(data[0].trim());
                product.setDescription(data[1].trim());
                product.setPrice(new BigDecimal(data[2].trim()));
                product.setQuantity(Integer.parseInt(data[3].trim()));
//                product.setCategoryId(Long.parseLong(data[4].trim()));
                products.add(product);
            }
        } catch (IOException e) {
            throw new Exception("Error processing the file.", e);
        } catch (NumberFormatException e) {
            throw new Exception("Error in the number format in the file..", e);
        }
        productService.saveAll(products);
        return products;
    }
}
