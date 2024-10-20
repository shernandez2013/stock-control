package com.stock.control.controllers;

import com.stock.control.entities.PurchaseOrder;
import com.stock.control.service.PurchaseOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable Long id) {
        return purchaseOrderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PurchaseOrder createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.save(purchaseOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrder> updateSupplier(@PathVariable Long id, @RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.update(id, purchaseOrder)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deletePurchaseOrder(@PathVariable Long id) {
        purchaseOrderService.deleteById(id);

    }
}
