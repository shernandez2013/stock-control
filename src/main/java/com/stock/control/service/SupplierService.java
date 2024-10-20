package com.stock.control.service;

import com.stock.control.entities.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<Supplier> findAll();

    Optional<Supplier> findById(Long id);

    Supplier save(Supplier supplier);

    Optional<Supplier> update(Long id, Supplier supplier);

    void deleteById(Long id);

}
