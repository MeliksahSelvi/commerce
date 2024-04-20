package com.commerce.order.service.order.handler;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.handler.UseCaseHandler;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.handler.helper.CreateOrderHelper;
import com.commerce.order.service.order.usecase.CreateOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author mselvi
 * @Created 01.03.2024
 */

@DomainComponent
public class CreateOrderUseCaseHandler implements UseCaseHandler<Order, CreateOrder> {

    private static final Logger logger = LoggerFactory.getLogger(CreateOrderUseCaseHandler.class);
    private final CreateOrderHelper createOrderHelper;

    public CreateOrderUseCaseHandler(CreateOrderHelper createOrderHelper) {
        this.createOrderHelper = createOrderHelper;
    }

    @Override
    public Order handle(CreateOrder useCase) {
        logger.info("Creatin order action started by customerId: {} ", useCase.customerId());
        return createOrderHelper.createOrder(useCase);
    }
}
