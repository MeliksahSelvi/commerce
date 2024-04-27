package com.commerce.order.query.service.order.handler;

import com.commerce.order.query.service.common.DomainComponent;
import com.commerce.order.query.service.common.exception.OrderNotFoundException;
import com.commerce.order.query.service.common.handler.UseCaseHandler;
import com.commerce.order.query.service.order.model.OrderQuery;
import com.commerce.order.query.service.order.port.data.OrderQueryDataPort;
import com.commerce.order.query.service.order.usecase.TrackOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@DomainComponent
public class TrackOrderUseCaseHandler implements UseCaseHandler<OrderQuery, TrackOrder> {

    private static final Logger logger = LoggerFactory.getLogger(TrackOrderUseCaseHandler.class);
    private final OrderQueryDataPort orderQueryDataPort;

    public TrackOrderUseCaseHandler(OrderQueryDataPort orderQueryDataPort) {
        this.orderQueryDataPort = orderQueryDataPort;
    }

    @Override
    public OrderQuery handle(TrackOrder useCase) {
        Long orderId = useCase.orderId();
        Optional<OrderQuery> orderOptional = orderQueryDataPort.findById(orderId);
        return orderOptional.orElseThrow(() -> {
            throw new OrderNotFoundException(String.format("Could not find order with id: %d", orderId));
        });
    }
}
