package com.stock.control.model.response;

import com.stock.control.entities.Category;
import com.stock.control.entities.Supplier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Represents the response of a product")
public class ProductResponse {
    @Schema(description = "ID of the product", example = "1")
    private Long productId;

    @Schema(description = "Name of the product", example = "Laptop")
    private String productName;

    @Schema(description = "Description of the product", example = "15-inch laptop with 8GB RAM and 256GB SSD")
    private String description;

    @Schema(description = "Price of the product", example = "799.99")
    private BigDecimal price;

    @Schema(description = "Quantity in stock", example = "50")
    private int quantity;

    @Schema(description = "Category information", example = "1")
    private Category category;

    @Schema(description = "supplier information", example = "2")
    private Supplier supplier;

    @Schema(description = "Date when the product is created", example = "2")
    private LocalDateTime createdAt;

    @Schema(description = "Date when the product is updated", example = "2")
    private LocalDateTime updatedAt;
}
