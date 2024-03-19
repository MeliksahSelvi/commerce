package com.commerce.order.service.adapters.order.rest.dto;

import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.entity.Order;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

public record OrderCreateResponse(Long orderId,OrderStatus orderStatus, String message) {

    public OrderCreateResponse(Order order) {
        this(order.getId(), order.getOrderStatus(),"Order Created Successfully");
    }
}
