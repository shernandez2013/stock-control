package com.stock.control.service.impl;

import com.stock.control.entities.StockAlert;
import com.stock.control.repositories.StockAlertRepository;
import com.stock.control.service.StockAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockAlertServiceImpl implements StockAlertService {

    @Autowired
    private StockAlertRepository stockAlertRepository;

    @Override
    public List<StockAlert> findAll() {
        return stockAlertRepository.findAll();
    }

    @Override
    public Optional<StockAlert> findById(Long id) {
        return stockAlertRepository.findById(id);
    }

    @Override
    public StockAlert save(StockAlert stockAlert) {
        return stockAlertRepository.save(stockAlert);
    }

    @Override
    public Optional<StockAlert> update(Long id, StockAlert updatedStockAlert) {
        return stockAlertRepository.findById(id).map(existingAlert -> {
            existingAlert.setThresholdQuantity(updatedStockAlert.getThresholdQuantity());
            existingAlert.setAlertDate(updatedStockAlert.getAlertDate()); // Asegúrate de que esto esté permitido
            return stockAlertRepository.save(existingAlert);
        });
    }

    @Override
    public void deleteById(Long id) {
        if (stockAlertRepository.existsById(id)) {
            stockAlertRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Stock alert id = " + id + " not found.");
        }
    }
}
