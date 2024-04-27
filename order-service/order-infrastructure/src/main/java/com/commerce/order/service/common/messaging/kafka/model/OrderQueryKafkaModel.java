package com.commerce.order.service.common.messaging.kafka.model;

import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.usecase.OrderQuery;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public record OrderQueryKafkaModel(Long id, OrderStatus orderStatus) implements KafkaModel {

    public OrderQueryKafkaModel(OrderQuery orderQuery) {
        this(orderQuery.id(), orderQuery.orderStatus());
    }
}
