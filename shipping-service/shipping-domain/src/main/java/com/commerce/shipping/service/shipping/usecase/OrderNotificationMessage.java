package com.commerce.shipping.service.shipping.usecase;

import com.commerce.shipping.service.common.model.UseCase;
import com.commerce.shipping.service.common.valueobject.NotificationType;
import com.commerce.shipping.service.shipping.model.Address;
import com.commerce.shipping.service.shipping.model.OrderItem;
import com.commerce.shipping.service.shipping.model.Shipping;

import java.util.List;


/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public record OrderNotificationMessage(Long orderId, Long customerId, Address address,
                                       List<OrderItem> items, NotificationType notificationType,
                                       String message) implements UseCase {

    public OrderNotificationMessage(Shipping shipping, NotificationType notificationType, String message) {
        this(shipping.getOrderId(), shipping.getCustomerId(), shipping.getAddress(), shipping.getItems(), notificationType, message);
    }
}
