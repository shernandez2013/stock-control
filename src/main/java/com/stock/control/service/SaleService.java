package com.stock.control.service;

import com.stock.control.entities.Sale;

import java.util.List;

public interface SaleService {

    List<Sale> findAll();

    Sale findById(Long id);

    Sale save(Sale product);

    Sale update(Long id, Sale product);

    void deleteById(Long id);
}
