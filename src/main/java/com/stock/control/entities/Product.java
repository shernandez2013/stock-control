package com.stock.control.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)  // Many products to one category
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;  // This will be the full Category object
    @ManyToOne(fetch = FetchType.LAZY)  // Many products to one category
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
