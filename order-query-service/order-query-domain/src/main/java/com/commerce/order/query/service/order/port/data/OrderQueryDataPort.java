package com.commerce.order.query.service.order.port.data;

import com.commerce.order.query.service.order.model.OrderQuery;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public interface OrderQueryDataPort {

    Optional<OrderQuery> findById(Long orderId);

    OrderQuery save(OrderQuery orderQuery);
}
