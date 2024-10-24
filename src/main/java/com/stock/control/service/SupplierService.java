package com.stock.control.service;

import com.stock.control.entities.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> findAll();

    Supplier findById(Long id);

    Supplier save(Supplier supplier);

    Supplier update(Long id, Supplier supplier);

    void deleteById(Long id);

}
