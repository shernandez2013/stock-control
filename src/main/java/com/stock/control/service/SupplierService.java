package com.stock.control.service;

import com.stock.control.entities.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<Supplier> findAll();

    Supplier findById(Long id);

    Supplier save(Supplier supplier);

    Supplier update(Long id, Supplier supplier);

    void deleteById(Long id);

}
