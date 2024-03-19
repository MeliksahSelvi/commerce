package com.commerce.shipping.service.adapters.shipping.rest.dto;

import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.entity.Shipping;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public record ForwardProcessResponse(Long orderId, DeliveryStatus deliveryStatus) {

    public ForwardProcessResponse(Shipping shipping) {
        this(shipping.getOrderId(), shipping.getDeliveryStatus());
    }
}
