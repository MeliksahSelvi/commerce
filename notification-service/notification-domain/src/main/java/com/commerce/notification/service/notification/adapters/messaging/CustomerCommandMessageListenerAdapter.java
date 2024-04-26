package com.commerce.notification.service.notification.adapters.messaging;

import com.commerce.notification.service.common.DomainComponent;
import com.commerce.notification.service.notification.adapters.messaging.helper.CustomerCommandListenerHelper;
import com.commerce.notification.service.notification.port.messaging.input.CustomerCommandMessageListener;
import com.commerce.notification.service.notification.usecase.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

@DomainComponent
public class CustomerCommandMessageListenerAdapter implements CustomerCommandMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCommandMessageListenerAdapter.class);
    private final CustomerCommandListenerHelper customerCommandListenerHelper;

    public CustomerCommandMessageListenerAdapter(CustomerCommandListenerHelper customerCommandListenerHelper) {
        this.customerCommandListenerHelper = customerCommandListenerHelper;
    }

    @Override
    public void processMessage(CustomerInfo customerInfo) {
        logger.info("CustomerInfo save action started by email: {}", customerInfo.email());
        customerCommandListenerHelper.save(customerInfo);
    }
}
