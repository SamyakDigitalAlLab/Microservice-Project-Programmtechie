package com.programmingtech.inventory_service.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.programmingtech.inventory_service.services.InverntoryService;




@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController{


private final  InverntoryService inverntoryService;


    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode){

       return  inverntoryService.isInStock(skuCode);
    }
    

}
