package com.programmingtecie.order.service.services;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;
import java.util.List;

import com.programmingtecie.order.service.client.InventoryClient;
import com.programmingtecie.order.service.dto.InventoryResponse;
import com.programmingtecie.order.service.dto.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmingtecie.order.service.dto.OrderLineItemdto;
import com.programmingtecie.order.service.dto.OrderRequest;
import com.programmingtecie.order.service.model.Order;
import com.programmingtecie.order.service.model.OrderLineItem;
import com.programmingtecie.order.service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private static final String NOTIFICATION_TOPIC = "notificationTopic";

    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItem = orderRequest.getOrderLineItemdto()
                .stream()
                .map(this::maptoDTO)
                .toList();

        order.setOrderLineItem(orderLineItem);

        List<String> skuCode = order.getOrderLineItem().stream()
                .map(OrderLineItem::getSkucod)
                .toList();

        // Step 1: synchronous, circuit-breaker-protected call to inventory-service.
        // (Delegated to a separate bean - see InventoryClient - so the
        // resilience4j proxy is actually invoked; calling an
        // @CircuitBreaker-annotated method on "this" would bypass Spring AOP.)
        InventoryResponse[] inventoryResponses = inventoryClient.checkInventory(skuCode);

        boolean allProductsInStock = Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) {
            // Genuine business rule (not a technical failure) - this happens
            // AFTER the circuit-breaker-protected call returns successfully,
            // so it is never recorded as a circuit breaker failure.
            throw new IllegalArgumentException("Product is not in the stock, please try again later");
        }

        // Step 2: persist the order.
        orderRepository.save(order);

        // Step 3: publish an asynchronous event to Kafka so notification-service
        // can react. Order Service never calls notification-service directly.
        OrderPlacedEvent event = new OrderPlacedEvent(order.getOrderNumber(), skuCode, Instant.now());
        kafkaTemplate.send(NOTIFICATION_TOPIC, order.getOrderNumber(), event);
        log.info("Published OrderPlacedEvent for order {}", order.getOrderNumber());
    }

    private OrderLineItem maptoDTO(OrderLineItemdto orderLineItemdto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemdto.getPrice());
        orderLineItem.setQuantity(orderLineItemdto.getQuantity());
        orderLineItem.setSkucod(orderLineItemdto.getSkucod()); // adjust method name if needed
        return orderLineItem;
    }

}
