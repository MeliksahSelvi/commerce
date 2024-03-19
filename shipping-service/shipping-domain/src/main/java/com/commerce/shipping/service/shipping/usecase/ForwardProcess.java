package com.commerce.shipping.service.shipping.usecase;

import com.commerce.shipping.service.common.exception.ShippingDomainException;
import com.commerce.shipping.service.common.model.UseCase;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public record ForwardProcess(Long orderId, DeliveryStatus oldStatus, DeliveryStatus newStatus) implements UseCase {

    public ForwardProcess {
        if (DeliveryStatus.APPROVED == newStatus) {
            throw new ShippingDomainException("New Status can't be APPROVED in Forward processing action");
        }
    }
}
