package com.commerce.order.query.service.order.adapters.messaging.helper;

import com.commerce.order.query.service.common.DomainComponent;
import com.commerce.order.query.service.order.model.OrderQuery;
import com.commerce.order.query.service.order.port.data.OrderQueryDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@DomainComponent
public class OrderQuerySaveHelper {

    private static final Logger logger = LoggerFactory.getLogger(OrderQuerySaveHelper.class);
    private final OrderQueryDataPort orderQueryDataPort;

    public OrderQuerySaveHelper(OrderQueryDataPort orderQueryDataPort) {
        this.orderQueryDataPort = orderQueryDataPort;
    }

    @Transactional
    public void save(OrderQuery orderQuery) {
        orderQueryDataPort.save(orderQuery);
        logger.info("OrderQuery saved by order id: {} and orderStatus: {}", orderQuery.id(), orderQuery.orderStatus());
    }
}
