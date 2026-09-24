package com.programmingtecie.order.service.controller;

import com.programmingtecie.order.service.exception.InventoryServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {

    // Business rule: requested product(s) not in stock. 400 - the client can
    // fix this by changing the request; it does not affect the circuit breaker.
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleOutOfStock(IllegalArgumentException e) {
        return e.getMessage();
    }

    // Technical failure: inventory-service is down or the circuit is OPEN.
    // 503 - the client should retry later.
    @ExceptionHandler(InventoryServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String handleInventoryUnavailable(InventoryServiceUnavailableException e) {
        return "Order could not be placed right now, please try again in a moment";
    }
}
