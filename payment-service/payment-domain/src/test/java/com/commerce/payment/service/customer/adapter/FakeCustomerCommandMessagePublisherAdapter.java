package com.commerce.payment.service.customer.adapter;

import com.commerce.payment.service.customer.usecase.CustomerInfo;
import com.commerce.payment.service.payment.port.messaging.output.CustomerCommandMessagePublisher;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public class FakeCustomerCommandMessagePublisherAdapter implements CustomerCommandMessagePublisher {
    @Override
    public void publish(CustomerInfo customerInfo) {

    }
}
