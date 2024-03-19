package com.commerce.order.service.order.handler;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.handler.VoidUseCaseHandler;
import com.commerce.order.service.order.handler.helper.CancelOrderHelper;
import com.commerce.order.service.order.usecase.CancelOrder;

/**
 * @Author mselvi
 * @Created 11.03.2024
 */

@DomainComponent
public class CancelOrderVoidUseCaseHandler implements VoidUseCaseHandler<CancelOrder> {

    private final CancelOrderHelper cancelOrderHelper;

    public CancelOrderVoidUseCaseHandler(CancelOrderHelper cancelOrderHelper) {
        this.cancelOrderHelper = cancelOrderHelper;
    }

    @Override
    public void handle(CancelOrder useCase) {
        cancelOrderHelper.cancelOrder(useCase);
    }

}
