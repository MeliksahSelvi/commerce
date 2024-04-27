package com.commerce.order.query.service.common.messaging.kafka.model;

import com.commerce.order.query.service.common.valueobject.OrderStatus;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public record OrderQueryKafkaModel(Long id, OrderStatus orderStatus) implements KafkaModel {
}
