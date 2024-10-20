package com.stock.control.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockAlertRequest {
    private Long productId;
    private int thresholdQuantity;
}
