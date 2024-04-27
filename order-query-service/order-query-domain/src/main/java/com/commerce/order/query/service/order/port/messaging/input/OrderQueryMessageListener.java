package com.commerce.order.query.service.order.port.messaging.input;

import com.commerce.order.query.service.order.model.OrderQuery;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public interface OrderQueryMessageListener {

    void processMessage(OrderQuery orderQuery);
}
