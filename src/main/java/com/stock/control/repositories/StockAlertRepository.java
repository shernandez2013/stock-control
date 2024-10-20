package com.stock.control.repositories;

import com.stock.control.entities.StockAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockAlertRepository extends JpaRepository<StockAlert, Long> {
}
