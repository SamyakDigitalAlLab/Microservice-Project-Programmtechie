package com.programmingtechie.notification.service.consumer;

import com.programmingtechie.notification.service.dto.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Consumes order-placed events published by order-service on the
 * "notificationTopic" topic and performs the (simulated) notification.
 *
 * This is the asynchronous leg of the flow:
 *   Order Service --Kafka--> Notification Service
 * Order Service never calls this service directly over HTTP.
 */
@Service
@Slf4j
public class NotificationConsumer {

    @KafkaListener(topics = "notificationTopic", groupId = "notificationService")
    public void consumeOrderPlacedEvent(OrderPlacedEvent orderPlacedEvent) {
        log.info("Received notification event for order {} (skuCodes={}, placedAt={})",
                orderPlacedEvent.getOrderNumber(),
                orderPlacedEvent.getSkuCodes(),
                orderPlacedEvent.getPlacedAt());

        // Simulated notification (e.g. email/SMS) send. Replace with a real
        // email/SMS provider integration when one is available.
        sendNotification(orderPlacedEvent);

        log.info("Notification sent for order {}", orderPlacedEvent.getOrderNumber());
    }

    private void sendNotification(OrderPlacedEvent orderPlacedEvent) {
        log.info("Sending order confirmation notification for order number: {}",
                orderPlacedEvent.getOrderNumber());
    }
}
