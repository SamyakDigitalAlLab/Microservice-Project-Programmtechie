package com.programmingtecie.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * Event published to the "notificationTopic" Kafka topic once an order has
 * been saved. A proper event object is used (instead of just the order
 * number string) so notification-service has enough context to build a
 * real notification later without calling back into order-service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent implements Serializable {
    private String orderNumber;
    private List<String> skuCodes;
    private Instant placedAt;
}
