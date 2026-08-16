package com.programmingtech.inventory_service.controller;


import com.programmingtech.inventory_service.dto.InventoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.programmingtech.inventory_service.services.InverntoryService;

import java.util.List;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController{


private final  InverntoryService inverntoryService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(
            @RequestParam("skuCode") List<String> skuCodes) {

        return inverntoryService.isInStock(skuCodes);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

}
