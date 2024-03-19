package com.commerce.order.service.saga.helper;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

@DomainComponent
public class SagaHelper {

    public SagaStatus orderStatusToSagaStatus(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case CHECKING -> SagaStatus.CHECKING;
            case PENDING -> SagaStatus.PAYING;
            case PAID -> SagaStatus.PROCESSING;
            case APPROVED -> SagaStatus.SUCCEEDED;
            case CANCELLING -> SagaStatus.CANCELLING;
            case CANCELLED -> SagaStatus.CANCELLED;
        };
    }
}
