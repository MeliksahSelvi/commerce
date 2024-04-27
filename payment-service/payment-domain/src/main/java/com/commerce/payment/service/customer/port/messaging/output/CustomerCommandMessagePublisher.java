package com.commerce.payment.service.customer.port.messaging.output;

import com.commerce.payment.service.customer.usecase.CustomerInfo;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public interface CustomerCommandMessagePublisher {

    void publish(CustomerInfo customerInfo);
}
