package com.stock.control.service.impl;

import com.stock.control.entities.Sale;
import com.stock.control.exception.NotFoundException;
import com.stock.control.exception.ProcessingException;
import com.stock.control.repositories.SaleRepository;
import com.stock.control.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;


    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> findAll() {
        try {
            return saleRepository.findAll();
        } catch (DataAccessException e) {
            log.error("Error while fetching all sales", e);
            throw new ProcessingException("An error occurred while fetching sales from the database", e);
        }
    }

    @Override
    public Sale findById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale not found with id: " + id));
    }

    @Override
    @Transactional
    public Sale save(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("Sale information is missing");
        }
        if (saleRepository.existsById(sale.getSaleId())) {
            throw new IllegalArgumentException("Sale already exist: " + sale.getSaleId());
        }
        return saleRepository.save(sale);
    }

    @Override
    @Transactional
    public Sale update(Long id, Sale updatedSale) {
        if (updatedSale == null || updatedSale.getSaleId() == null) {
            throw new IllegalArgumentException("Sale information is missing");
        }
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));

        existingSale.setQuantitySold(updatedSale.getQuantitySold());
        existingSale.setTotal(updatedSale.getTotal());
        existingSale.setSaleDate(updatedSale.getSaleDate()); // Asegúrate de que esto esté permitido
        return saleRepository.save(existingSale);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale with id " + id + " not found"));
        if (!saleRepository.existsById(sale.getSaleId())) {
            throw new NotFoundException("Sale not found " + sale.getSaleId());
        }
        saleRepository.deleteById(id);
    }
}
