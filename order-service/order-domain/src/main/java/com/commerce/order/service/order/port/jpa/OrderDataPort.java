package com.commerce.order.service.order.port.jpa;

import com.commerce.order.service.order.model.Order;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

public interface OrderDataPort {

    Order save(Order order);

    Optional<Order> findById(Long orderId);
}
