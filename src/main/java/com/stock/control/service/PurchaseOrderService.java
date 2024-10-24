package com.stock.control.service;

import com.stock.control.entities.PurchaseOrder;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderService {

    List<PurchaseOrder> findAll();

    PurchaseOrder findById(Long id);

    PurchaseOrder save(PurchaseOrder product);

    PurchaseOrder update(Long id, PurchaseOrder product);

    void deleteById(Long id);
}
