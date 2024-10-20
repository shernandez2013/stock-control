package com.stock.control.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequest {
    private String productName;
    private String description;
    private BigDecimal price;
    private int quantity;
    private Long categoryId;
    private Long supplierId;
}
