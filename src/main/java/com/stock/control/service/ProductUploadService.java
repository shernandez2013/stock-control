package com.stock.control.service;

import com.stock.control.entities.Product;
import com.stock.control.model.request.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductUploadService {
    List<ProductRequest> uploadProducts(MultipartFile file) throws Exception;
}
