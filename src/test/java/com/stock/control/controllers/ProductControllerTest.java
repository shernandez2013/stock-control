package com.stock.control.controllers;


import com.stock.control.entities.Product;
import com.stock.control.service.ProductService;
import com.stock.control.service.ProductUploadService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private ProductUploadService productUploadService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testGetAllProducts() throws Exception {
        Product product1 = TestData.getProduct(1L, "Product 1", "100");
        Product product2 = TestData.getProduct(2L, "Product 2", "200");
        when(productService.findAll()).thenReturn(Arrays.asList(product1, product2));
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(1L))
                .andExpect(jsonPath("$[0].productName").value("Product 1"))
                .andExpect(jsonPath("$[1].productId").value(2L))
                .andExpect(jsonPath("$[1].productName").value("Product 2"));
        verify(productService, times(1)).findAll();
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = TestData.getProduct(1L, "Product 1", "200");
        when(productService.findById(1L)).thenReturn(product);
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.productName").value("Product 1"));
        verify(productService, times(1)).findById(1L);
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = TestData.getProduct(1L, "Product 1", "100");
        when(productService.save(any(Product.class))).thenReturn(product);
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Product 1\",\"price\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.productName").value("Product 1"));
        verify(productService, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product updatedProduct = TestData.getProduct(1L, "Updated Product", "120");
        when(productService.update(eq(1L), any(Product.class))).thenReturn(updatedProduct);
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Product\",\"price\":120.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.productName").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(120.0));
        verify(productService, times(1)).update(eq(1L), any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteById(1L);
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk());
        verify(productService, times(1)).deleteById(1L);
    }

    @Test
    void testUploadProducts() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "products.csv",
                "text/csv",
                "product data".getBytes()
        );
        when(productUploadService.uploadProducts(any(MultipartFile.class)))
                .thenReturn(Arrays.asList(new Product(), new Product()));
        mockMvc.perform(multipart("/api/products/upload")
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(content().string("Products loaded successfully: 2"));
        verify(productUploadService).uploadProducts(any(MultipartFile.class));
    }

    @Test
    void testUploadProductsBadRequest() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Some data".getBytes());
        assertThrows(BadRequestException.class, () -> productController.uploadProducts(file));
    }

    @Test
    void testUploadProducts_EmptyFile() {
        MultipartFile file = new MockMultipartFile("file", "", "text/csv", new byte[0]);
        assertThrows(BadRequestException.class, () -> productController.uploadProducts(file));
    }
}
