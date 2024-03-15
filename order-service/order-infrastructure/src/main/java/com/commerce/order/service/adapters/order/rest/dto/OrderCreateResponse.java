package com.commerce.order.service.adapters.order.rest.dto;

import com.commerce.order.service.common.valueobject.OrderStatus;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

public record OrderCreateResponse(OrderStatus orderStatus, String message) {

    public OrderCreateResponse(OrderStatus orderStatus) {
        this(orderStatus,"Order Created Successfully");
    }
}
