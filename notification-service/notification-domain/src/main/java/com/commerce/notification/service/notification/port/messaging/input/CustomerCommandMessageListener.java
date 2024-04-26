package com.commerce.notification.service.notification.port.messaging.input;

import com.commerce.notification.service.notification.usecase.CustomerInfo;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public interface CustomerCommandMessageListener {

    void processMessage(CustomerInfo customerInfo);
}
