package com.stock.control.controllers;

import com.stock.control.entities.StockAlert;
import com.stock.control.service.StockAlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-alerts")
public class StockAlertController {

    private final StockAlertService stockAlertService;

    public StockAlertController(StockAlertService stockAlertService) {
        this.stockAlertService = stockAlertService;
    }

    @GetMapping
    public List<StockAlert> getAllStockAlerts() {
        return stockAlertService.findAll();
    }

    @GetMapping("/{id}")
    public StockAlert getStockAlertById(@PathVariable Long id) {
        return stockAlertService.findById(id);
    }

    @PostMapping
    public StockAlert createStockAlert(@RequestBody StockAlert stockAlert) {
        return stockAlertService.save(stockAlert);
    }


    @PutMapping("/{id}")
    public StockAlert updateSupplier(@PathVariable Long id, @RequestBody StockAlert stockAlert) {
        return stockAlertService.update(id, stockAlert);
    }


    @DeleteMapping("/{id}")
    public void deleteStockAlert(@PathVariable Long id) {
        stockAlertService.deleteById(id);
    }
}
