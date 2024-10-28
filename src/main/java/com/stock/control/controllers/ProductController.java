package com.stock.control.controllers;

import com.stock.control.model.request.ProductRequest;
import com.stock.control.model.response.ProductResponse;
import com.stock.control.service.ProductService;
import com.stock.control.service.ProductUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {

    private final ProductService productService;
    private final ProductUploadService productUploadService;

    @Autowired
    public ProductController(ProductService productService, ProductUploadService productUploadService) {
        this.productService = productService;
        this.productUploadService = productUploadService;
    }

    @Operation(
            summary = "Get all products",
            description = "Returns a list of all available products.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of products returned successfully"),
                    @ApiResponse(responseCode = "404", description = "No products found")
            }
    )
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.findAll();
    }

    @Operation(
            summary = "Get product by id",
            description = "Returns an available product by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A product returned successfully"),
                    @ApiResponse(responseCode = "404", description = "No product found")
            }
    )
    @GetMapping("/{id}")
    public ProductResponse getProductById(@Parameter(description = "Product Id") @PathVariable Long id) {
        return productService.findById(id);
    }

    @Operation(
            summary = "Create product",
            description = "Create a product from product request",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A product returned successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    public ProductResponse createProduct(@Parameter(description = "Product to save") @RequestBody ProductRequest product) {
        return productService.save(product);
    }

    @Operation(
            summary = "Update product by id",
            description = "Update a product by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A product returned successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@Parameter(description = "Product id") @PathVariable Long id,
                                         @Parameter(description = "Product to update") @RequestBody ProductRequest product) {
        return productService.update(id, product);
    }

    @Operation(
            summary = "Delete product by id",
            description = "Delete a product by id, and product request",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A product returned successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public void deleteProduct(@Parameter(description = "Product id") @PathVariable Long id) {
        productService.deleteById(id);
    }

    @Operation(
            summary = "Upload and save products by file",
            description = "Upload and save product list from csv file",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Products loaded successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/upload")
    public ResponseEntity<String> uploadProducts(@Parameter(description = "file with product list to save")
                                                 @RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty() || !Objects.equals(file.getContentType(), "text/csv")) {
            throw new BadRequestException("The file must be a CSV file and cannot be empty.");
        }
        List<ProductRequest> products = productUploadService.uploadProducts(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Products loaded successfully: " + products.size());
    }
}
