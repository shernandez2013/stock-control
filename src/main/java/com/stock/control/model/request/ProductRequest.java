package com.stock.control.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Represents a request to create or update a product")
public class ProductRequest {

    @Schema(description = "Name of the product", example = "Laptop")
    @NotNull
    private String productName;

    @Schema(description = "Brief description of the product", example = "15-inch laptop with 8GB RAM and 256GB SSD")
    private String description;

    @Schema(description = "Price of the product", example = "799.99")
    @NotNull
    private BigDecimal price;

    @Schema(description = "Quantity of the product in stock", example = "50")
    @NotNull
    private int quantity;

    @Schema(description = "ID of the category to which the product belongs", example = "1")
    private Long categoryId;

    @Schema(description = "ID of the product supplier", example = "2")
    private Long supplierId;
}
