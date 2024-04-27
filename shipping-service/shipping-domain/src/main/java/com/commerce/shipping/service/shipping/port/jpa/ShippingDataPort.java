package com.commerce.shipping.service.shipping.port.jpa;

import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.model.Shipping;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public interface ShippingDataPort {

    Shipping save(Shipping shipping);

    Optional<Shipping> findByOrderId(Long orderId);

    Optional<Shipping> findByOrderIdAndDeliveryStatus(Long orderId,DeliveryStatus deliveryStatus);
}
