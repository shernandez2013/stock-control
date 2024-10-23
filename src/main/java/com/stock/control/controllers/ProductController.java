package com.stock.control.controllers;

import com.stock.control.entities.Product;
import com.stock.control.service.ProductService;
import com.stock.control.service.ProductUploadService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductUploadService productUploadService;

    @Autowired
    public ProductController(ProductService productService, ProductUploadService productUploadService) {
        this.productService = productService;
        this.productUploadService = productUploadService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProducts(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty() || !Objects.equals(file.getContentType(), "text/csv")) {
            throw new BadRequestException("The file must be a CSV file and cannot be empty.");
        }
        List<Product> products = productUploadService.uploadProducts(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Products loaded successfully: " + products.size());
    }
}
