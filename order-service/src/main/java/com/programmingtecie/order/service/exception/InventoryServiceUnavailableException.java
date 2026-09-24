package com.programmingtecie.order.service.exception;

/**
 * Thrown by the resilience4j fallback when inventory-service cannot be
 * reached (timeout, connection error, or the circuit breaker is OPEN).
 * Kept distinct from IllegalArgumentException (used for the legitimate
 * business case of "product is out of stock") so the two failure modes are
 * never confused, and so that only genuine technical failures are recorded
 * as circuit breaker failures - see OrderService.checkInventory.
 */
public class InventoryServiceUnavailableException extends RuntimeException {
    public InventoryServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
