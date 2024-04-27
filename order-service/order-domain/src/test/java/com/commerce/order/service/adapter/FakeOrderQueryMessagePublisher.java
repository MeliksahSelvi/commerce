package com.commerce.order.service.adapter;

import com.commerce.order.service.order.port.messaging.output.OrderQueryMessagePublisher;
import com.commerce.order.service.order.usecase.OrderQuery;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public class FakeOrderQueryMessagePublisher implements OrderQueryMessagePublisher {
    @Override
    public void publish(OrderQuery orderQuery) {

    }
}
