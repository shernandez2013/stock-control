package com.stock.control.controllers;

import com.stock.control.entities.Sale;
import com.stock.control.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        return saleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sale createSale(@RequestBody Sale sale) {
        return saleService.save(sale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSupplier(@PathVariable Long id, @RequestBody Sale sale) {
        return saleService.update(id, sale)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable Long id) {
        saleService.deleteById(id);
    }
}
