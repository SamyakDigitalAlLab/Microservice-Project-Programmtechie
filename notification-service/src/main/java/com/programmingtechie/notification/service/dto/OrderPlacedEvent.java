package com.programmingtechie.notification.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * Mirrors com.programmingtecie.order.service.dto.OrderPlacedEvent produced by
 * order-service. Kept as a plain, framework-agnostic DTO with matching field
 * names so Jackson can deserialize the JSON payload published to Kafka
 * without the two services sharing a common jar.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent implements Serializable {
    private String orderNumber;
    private List<String> skuCodes;
    private Instant placedAt;
}
