package com.commerce.order.service.order.handler.adapter;

import com.commerce.order.service.order.port.rest.InnerRestPort;
import com.commerce.order.service.order.usecase.CustomerResponse;

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
