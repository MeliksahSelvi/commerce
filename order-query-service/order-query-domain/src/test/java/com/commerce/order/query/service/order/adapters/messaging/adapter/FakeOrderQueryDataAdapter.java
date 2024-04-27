package com.commerce.order.query.service.order.adapters.messaging.adapter;

import com.commerce.order.query.service.common.valueobject.OrderStatus;
import com.commerce.order.query.service.order.model.OrderQuery;
import com.commerce.order.query.service.order.port.data.OrderQueryDataPort;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public class FakeOrderQueryDataAdapter implements OrderQueryDataPort {

    private static final Long EXIST_ORDER_ID=1L;

    @Override
    public Optional<OrderQuery> findById(Long orderId) {
        if (!EXIST_ORDER_ID.equals(orderId)){
            return Optional.empty();
        }
        return Optional.of(new OrderQuery(EXIST_ORDER_ID, OrderStatus.CHECKING));
    }

    @Override
    public OrderQuery save(OrderQuery orderQuery) {
        return new OrderQuery(orderQuery.id(),orderQuery.orderStatus());
    }
}
