package com.stock.control.service.impl;

import com.stock.control.entities.PurchaseOrder;
import com.stock.control.exception.NotFoundException;
import com.stock.control.exception.ProcessingException;
import com.stock.control.repositories.PurchaseOrderRepository;
import com.stock.control.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public List<PurchaseOrder> findAll() {
        try {
            return purchaseOrderRepository.findAll();
        } catch (DataAccessException e) {
            log.error("Error while fetching all Purchase Orders", e);
            throw new ProcessingException("An error occurred while fetching Purchase Orders from the database", e);
        }
    }

    @Override
    public PurchaseOrder findById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("categories not found with id: " + id));
    }

    @Override
    @Transactional
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        if (purchaseOrder == null) {
            throw new IllegalArgumentException("Purchase Order information is missing");
        }
        if (purchaseOrderRepository.existsById(purchaseOrder.getOrderId())) {
            throw new IllegalArgumentException("PurchaseOrder already exist: " + purchaseOrder.getOrderId());
        }
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrder update(Long id, PurchaseOrder updatedOrder) {
        if (updatedOrder == null || updatedOrder.getOrderId() == null) {
            throw new IllegalArgumentException("Purchase Order information is missing");
        }
        PurchaseOrder existingOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));

        existingOrder.setQuantity(updatedOrder.getQuantity());
        existingOrder.setTotal(updatedOrder.getTotal());
        existingOrder.setOrderDate(updatedOrder.getOrderDate());
//          existingOrder.setSupplierId(updatedOrder.getSupplierId());
        return purchaseOrderRepository.save(existingOrder);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));
        if (!purchaseOrderRepository.existsById(purchaseOrder.getOrderId())) {
            throw new NotFoundException("Category not found " + purchaseOrder.getOrderId());
        }
        purchaseOrderRepository.deleteById(id);
    }
}
