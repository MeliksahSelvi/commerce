package com.commerce.notification.service.notification.adapters.messaging.adapter;

import com.commerce.notification.service.notification.port.rest.InnerRestPort;
import com.commerce.notification.service.notification.usecase.CustomerResponse;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeInnerRestAdapter implements InnerRestPort {
    private static final Long EXIST_CUSTOMER_ID = 1L;

    @Override
    public CustomerResponse getCustomerInfo(Long customerId) {
        return EXIST_CUSTOMER_ID == customerId ? new CustomerResponse(1L, "name", "surname", "123", "email") : null;
    }
}
