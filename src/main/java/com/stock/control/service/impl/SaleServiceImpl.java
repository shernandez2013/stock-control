package com.stock.control.service.impl;

import com.stock.control.entities.Sale;
import com.stock.control.repositories.SaleRepository;
import com.stock.control.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;


    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }

    @Override
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public Optional<Sale> update(Long id, Sale updatedSale) {
        return saleRepository.findById(id).map(existingSale -> {
            existingSale.setQuantitySold(updatedSale.getQuantitySold());
            existingSale.setTotal(updatedSale.getTotal());
            existingSale.setSaleDate(updatedSale.getSaleDate()); // Asegúrate de que esto esté permitido
            return saleRepository.save(existingSale);
        });
    }

    @Override
    public void deleteById(Long id) {
        if (saleRepository.existsById(id)) {
            saleRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Sale id = " + id + " not found.");
        }
    }
}
