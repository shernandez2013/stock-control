package com.stock.control.service.impl;

import com.stock.control.entities.PurchaseOrder;
import com.stock.control.repositories.PurchaseOrderRepository;
import com.stock.control.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public Optional<PurchaseOrder> findById(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    @Override
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public Optional<PurchaseOrder> update(Long id, PurchaseOrder updatedOrder) {
        return purchaseOrderRepository.findById(id).map(existingOrder -> {
            existingOrder.setQuantity(updatedOrder.getQuantity());
            existingOrder.setTotal(updatedOrder.getTotal());
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
//            existingOrder.setSupplierId(updatedOrder.getSupplierId());
            return purchaseOrderRepository.save(existingOrder);
        });
    }

    @Override
    public void deleteById(Long id) {
        if (purchaseOrderRepository.existsById(id)) {
            purchaseOrderRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Purchase Order id = " + id + " not found.");
        }
    }
}
