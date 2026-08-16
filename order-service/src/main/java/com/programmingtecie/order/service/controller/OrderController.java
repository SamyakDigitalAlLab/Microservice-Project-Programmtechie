package com.programmingtecie.order.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmingtecie.order.service.dto.OrderRequest;
import com.programmingtecie.order.service.dto.OrderResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.programmingtecie.order.service.services.OrderService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;






@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {
    
private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
      orderService.placeOrder(orderRequest);
      return "Order Place Sucessfully";
    }
//     @GetMapping
//     public OrderResponse getAllOrder(@RequestParam String param) {
//         return new String();
//     }
    
    
}
