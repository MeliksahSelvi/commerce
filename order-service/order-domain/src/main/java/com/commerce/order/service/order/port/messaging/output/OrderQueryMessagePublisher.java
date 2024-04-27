package com.commerce.order.service.order.port.messaging.output;

import com.commerce.order.service.order.usecase.OrderQuery;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public interface OrderQueryMessagePublisher {

    void publish(OrderQuery orderQuery);
}
