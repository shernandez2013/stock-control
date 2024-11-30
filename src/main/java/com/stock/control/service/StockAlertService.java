package com.stock.control.service;

import com.stock.control.entities.StockAlert;

import java.util.List;

public interface StockAlertService {
    List<StockAlert> findAll();

    StockAlert findById(Long id);

    StockAlert save(StockAlert product);

    StockAlert update(Long id, StockAlert supplier);

    void deleteById(Long id);
}
