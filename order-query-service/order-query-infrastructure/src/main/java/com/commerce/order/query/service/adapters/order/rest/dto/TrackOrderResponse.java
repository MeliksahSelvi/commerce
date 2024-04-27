package com.commerce.order.query.service.adapters.order.rest.dto;

import com.commerce.order.query.service.common.valueobject.OrderStatus;
import com.commerce.order.query.service.order.model.OrderQuery;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public record TrackOrderResponse(Long orderId, OrderStatus orderStatus, String message) {

    public TrackOrderResponse(OrderQuery orderQuery) {
        this(orderQuery.id(), orderQuery.orderStatus(), "Order Retrieved");
    }
}
