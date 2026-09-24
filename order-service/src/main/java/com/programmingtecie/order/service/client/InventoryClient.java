package com.programmingtecie.order.service.client;

import com.programmingtecie.order.service.dto.InventoryResponse;
import com.programmingtecie.order.service.exception.InventoryServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

/**
 * Wraps the single synchronous, network-bound call from Order Service to
 * Inventory Service. Deliberately kept as its own Spring bean, NOT a method
 * on OrderService: resilience4j's @CircuitBreaker (and Resilience4j's
 * Spring AOP-based circuit breaker/@CircuitBreaker in general) works via a
 * dynamic proxy wrapped around this bean. If this method lived on
 * OrderService and OrderService called it via `this.checkInventory(...)`,
 * that call would bypass the proxy entirely (the classic Spring AOP
 * "self-invocation" pitfall) and the circuit breaker would silently never
 * engage. Calling it through an injected, separate bean guarantees the
 * proxy - and therefore the circuit breaker - is actually used.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryClient {

    private final WebClient.Builder webClientBuilder;

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackCheckInventory")
    public InventoryResponse[] checkInventory(List<String> skuCode) {
        return webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder
                        .queryParam("skuCode", skuCode)
                        .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }

    /**
     * Triggered when inventory-service call fails technically (timeout,
     * connection refused, 5xx, etc.) or when the "inventory" circuit is OPEN.
     * Deliberately throws a distinct exception rather than returning a
     * default InventoryResponse[] so OrderService can tell "inventory
     * unavailable" (503, retry later) apart from "product out of stock"
     * (400, legitimate business rule) - see OrderExceptionHandler.
     */
    private InventoryResponse[] fallbackCheckInventory(List<String> skuCode, Throwable throwable) {
        log.warn("inventory-service call failed/unavailable, circuit breaker fallback triggered: {}",
                throwable.toString());
        throw new InventoryServiceUnavailableException(
                "Inventory Service is currently unavailable, please try again shortly", throwable);
    }
}
