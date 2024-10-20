package com.stock.control.service;

import com.stock.control.entities.StockAlert;

import java.util.List;
import java.util.Optional;

public interface StockAlertService {
    List<StockAlert> findAll();

    Optional<StockAlert> findById(Long id);

    StockAlert save(StockAlert product);

    Optional<StockAlert> update(Long id, StockAlert supplier);

    void deleteById(Long id);
}
