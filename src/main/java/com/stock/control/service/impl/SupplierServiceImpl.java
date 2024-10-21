package com.stock.control.service.impl;

import com.stock.control.entities.Supplier;
import com.stock.control.repositories.SupplierRepository;
import com.stock.control.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Optional<Supplier> update(Long id, Supplier updatedSupplier) {

        return supplierRepository.findById(id).map(existingSupplier -> {
            existingSupplier.setSupplierName(updatedSupplier.getSupplierName());
            existingSupplier.setContactInfo(updatedSupplier.getContactInfo());
            return supplierRepository.save(existingSupplier);
        });
    }

    @Override
    public void deleteById(Long id) {
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Stock alert id = " + id + " not found.");
        }
    }
}
