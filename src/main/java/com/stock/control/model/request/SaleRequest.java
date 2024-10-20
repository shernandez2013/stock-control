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
public class SaleRequest {
    private Long productId;
    private int quantitySold;
    private BigDecimal total;
}
