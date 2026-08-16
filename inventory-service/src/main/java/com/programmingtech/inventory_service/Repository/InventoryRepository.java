package com.programmingtech.inventory_service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmingtech.inventory_service.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
