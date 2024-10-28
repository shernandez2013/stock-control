package com.stock.control.service.impl;

import com.stock.control.model.request.ProductRequest;
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
    public List<ProductRequest> uploadProducts(MultipartFile file) throws Exception {
        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv")) {
            throw new Exception("The file is empty or not a CSV file.");
        }
        List<ProductRequest> productRequests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            if ((line = reader.readLine()) != null) {
                // To remove headers if necessary
            }
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) {
                    continue;
                }
                ProductRequest productRequest = new ProductRequest();
                productRequest.setProductName(data[0].trim());
                productRequest.setDescription(data[1].trim());
                productRequest.setPrice(new BigDecimal(data[2].trim()));
                productRequest.setQuantity(Integer.parseInt(data[3].trim()));
//                productRequest.setCategoryId(Long.parseLong(data[4].trim()));
//                productRequest.setSupplierId(Long.parseLong(data[4].trim()));
                productRequests.add(productRequest);
            }
        } catch (IOException e) {
            throw new Exception("Error processing the file.", e);
        } catch (NumberFormatException e) {
            throw new Exception("Error in the number format in the file..", e);
        }
        productService.saveAll(productRequests);
        return productRequests;
    }
}
