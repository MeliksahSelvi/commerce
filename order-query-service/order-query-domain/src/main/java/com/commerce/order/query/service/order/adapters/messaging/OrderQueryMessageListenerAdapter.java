package com.commerce.order.query.service.order.adapters.messaging;

import com.commerce.order.query.service.common.DomainComponent;
import com.commerce.order.query.service.order.adapters.messaging.helper.OrderQuerySaveHelper;
import com.commerce.order.query.service.order.model.OrderQuery;
import com.commerce.order.query.service.order.port.messaging.input.OrderQueryMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@DomainComponent
public class OrderQueryMessageListenerAdapter implements OrderQueryMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderQueryMessageListenerAdapter.class);
    private final OrderQuerySaveHelper orderQuerySaveHelper;

    public OrderQueryMessageListenerAdapter(OrderQuerySaveHelper orderQuerySaveHelper) {
        this.orderQuerySaveHelper = orderQuerySaveHelper;
    }

    @Override
    public void processMessage(OrderQuery orderQuery) {
        logger.info("OrderQuery save action started by orderId: {} and orderStatus: {}", orderQuery.id(), orderQuery.orderStatus());
        orderQuerySaveHelper.save(orderQuery);
    }
}
