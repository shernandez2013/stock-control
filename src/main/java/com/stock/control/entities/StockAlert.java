package com.stock.control.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_alerts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private int thresholdQuantity;
    @Column(updatable = false)
    private LocalDateTime alertDate;
}
