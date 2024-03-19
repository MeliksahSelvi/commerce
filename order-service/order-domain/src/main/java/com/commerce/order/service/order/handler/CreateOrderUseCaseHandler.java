package com.commerce.order.service.order.handler;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.handler.UseCaseHandler;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.handler.helper.CreateOrderHelper;
import com.commerce.order.service.order.usecase.CreateOrder;


/**
 * @Author mselvi
 * @Created 01.03.2024
 */

@DomainComponent
public class CreateOrderUseCaseHandler implements UseCaseHandler<Order, CreateOrder> {

    private final CreateOrderHelper createOrderHelper;

    public CreateOrderUseCaseHandler(CreateOrderHelper createOrderHelper) {
        this.createOrderHelper = createOrderHelper;
    }

    @Override
    public Order handle(CreateOrder useCase) {
        return createOrderHelper.createOrder(useCase);
    }
}
