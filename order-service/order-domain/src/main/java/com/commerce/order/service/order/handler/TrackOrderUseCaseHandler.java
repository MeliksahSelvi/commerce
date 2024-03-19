package com.commerce.order.service.order.handler;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.handler.UseCaseHandler;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.port.jpa.OrderDataPort;
import com.commerce.order.service.order.usecase.TrackOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

@DomainComponent
public class TrackOrderUseCaseHandler implements UseCaseHandler<Order, TrackOrder> {

    private static final Logger logger = LoggerFactory.getLogger(TrackOrderUseCaseHandler.class);
    private final OrderDataPort orderDataPort;

    public TrackOrderUseCaseHandler(OrderDataPort orderDataPort) {
        this.orderDataPort = orderDataPort;
    }

    @Override
    public Order handle(TrackOrder useCase) {
        Long orderId = useCase.orderId();
        Optional<Order> orderOptional = orderDataPort.findById(orderId);
        return orderOptional.orElseThrow(() -> {
            throw new OrderNotFoundException(String.format("Could not find order with id: %d", orderId));
        });
    }
}
