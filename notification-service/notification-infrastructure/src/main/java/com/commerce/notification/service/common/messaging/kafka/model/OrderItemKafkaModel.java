package com.commerce.notification.service.common.messaging.kafka.model;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record OrderItemKafkaModel(Long id, Long orderId, Long productId, int quantity, BigDecimal price,
                                  BigDecimal totalPrice) implements KafkaModel {

}
