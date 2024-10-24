package com.stock.control.service.impl;

import com.stock.control.entities.StockAlert;
import com.stock.control.exception.NotFoundException;
import com.stock.control.exception.ProcessingException;
import com.stock.control.repositories.StockAlertRepository;
import com.stock.control.service.StockAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class StockAlertServiceImpl implements StockAlertService {

    private final StockAlertRepository stockAlertRepository;

    @Autowired
    public StockAlertServiceImpl(StockAlertRepository stockAlertRepository) {
        this.stockAlertRepository = stockAlertRepository;
    }

    @Override
    public List<StockAlert> findAll() {
        try {
            return stockAlertRepository.findAll();
        } catch (DataAccessException e) {
            log.error("Error while fetching all StockAlerts", e);
            throw new ProcessingException("An error occurred while fetching StockAlerts from the database", e);
        }
    }

    @Override
    public StockAlert findById(Long id) {

        return stockAlertRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StockAlert not found with id: " + id));
    }

    @Override
    @Transactional
    public StockAlert save(StockAlert stockAlert) {
        if (stockAlert == null) {
            throw new IllegalArgumentException("StockAlert information is missing");
        }
        if (stockAlertRepository.existsById(stockAlert.getAlertId())) {
            throw new IllegalArgumentException("StockAlert already exist: " + stockAlert.getAlertId());
        }
        return stockAlertRepository.save(stockAlert);
    }

    @Override
    @Transactional
    public StockAlert update(Long id, StockAlert updatedStockAlert) {
        if (updatedStockAlert == null) {
            throw new IllegalArgumentException("StockAlert information is missing");
        }
        StockAlert existingAlert = stockAlertRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StockAlert with id " + id + " not found"));
        existingAlert.setThresholdQuantity(updatedStockAlert.getThresholdQuantity());
        existingAlert.setAlertDate(updatedStockAlert.getAlertDate());
        return stockAlertRepository.save(existingAlert);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        StockAlert stockAlert = stockAlertRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));
        if (!stockAlertRepository.existsById(stockAlert.getAlertId())) {
            throw new NotFoundException("Category not found " + stockAlert.getAlertId());
        }
        stockAlertRepository.deleteById(id);
    }
}
