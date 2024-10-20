package com.stock.control.service;

import com.stock.control.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductUploadService {
    List<Product> uploadProducts(MultipartFile file) throws Exception;
}
