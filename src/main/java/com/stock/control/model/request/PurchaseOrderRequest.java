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
public class PurchaseOrderRequest {
    private Long productId;
    private Long supplierId;
    private int quantity;
    private BigDecimal total;
}
