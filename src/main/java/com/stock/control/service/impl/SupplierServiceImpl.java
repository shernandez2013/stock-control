package com.stock.control.service.impl;

import com.stock.control.entities.Supplier;
import com.stock.control.exception.NotFoundException;
import com.stock.control.exception.ProcessingException;
import com.stock.control.repositories.SupplierRepository;
import com.stock.control.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> findAll() {
        try {
            return supplierRepository.findAll();
        } catch (DataAccessException e) {
            log.error("Error while fetching all Suppliers", e);
            throw new ProcessingException("An error occurred while fetching Suppliers from the database", e);
        }
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with id: " + id));
    }

    @Override
    @Transactional
    public Supplier save(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier information is missing");
        }
        if (supplierRepository.existsById(supplier.getSupplierId())) {
            throw new IllegalArgumentException("Supplier already exist: " + supplier.getSupplierId());
        }
        return supplierRepository.save(supplier);
    }

    @Override
    @Transactional
    public Supplier update(Long id, Supplier updatedSupplier) {
        if (updatedSupplier == null) {
            throw new IllegalArgumentException("Supplier information is missing");
        }
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier with id " + id + " not found"));
        existingSupplier.setSupplierName(updatedSupplier.getSupplierName());
        existingSupplier.setContactInfo(updatedSupplier.getContactInfo());
        return supplierRepository.save(existingSupplier);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier with id " + id + " not found"));
        if (!supplierRepository.existsById(existingSupplier.getSupplierId())) {
            throw new NotFoundException("Supplier not found " + existingSupplier.getSupplierId());
        }
        supplierRepository.deleteById(id);
    }
}
