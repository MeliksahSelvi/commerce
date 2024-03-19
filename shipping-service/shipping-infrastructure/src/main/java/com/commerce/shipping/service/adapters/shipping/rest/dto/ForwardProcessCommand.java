package com.commerce.shipping.service.adapters.shipping.rest.dto;

import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.usecase.ForwardProcess;
import jakarta.validation.constraints.NotNull;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public record ForwardProcessCommand(@NotNull Long orderId, @NotNull DeliveryStatus oldStatus,@NotNull DeliveryStatus newStatus) {

    public ForwardProcess toModel() {
        return new ForwardProcess(orderId, oldStatus,newStatus);
    }
}
