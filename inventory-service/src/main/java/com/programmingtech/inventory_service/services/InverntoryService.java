package com.programmingtech.inventory_service.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmingtech.inventory_service.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InverntoryService {

private final InventoryRepository inventoryRepository;

@Transactional(readOnly=true)
    public boolean isInStock(String skuCode){
return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
