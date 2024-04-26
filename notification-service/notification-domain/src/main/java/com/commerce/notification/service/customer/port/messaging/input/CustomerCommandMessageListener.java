package com.commerce.notification.service.customer.port.messaging.input;

import com.commerce.notification.service.customer.usecase.CustomerInfo;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public interface CustomerCommandMessageListener {

    void processMessage(CustomerInfo customerInfo);
}
