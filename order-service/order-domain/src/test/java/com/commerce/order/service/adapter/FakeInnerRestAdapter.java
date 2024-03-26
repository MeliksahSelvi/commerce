package com.commerce.order.service.adapter;

import com.commerce.order.service.order.port.rest.InnerRestPort;
import com.commerce.order.service.order.usecase.CustomerInfo;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeInnerRestAdapter implements InnerRestPort {

    private static final Long EXIST_CUSTOMER_ID = 1L;

    @Override
    public CustomerInfo getCustomerInfo(Long customerId) {
        return EXIST_CUSTOMER_ID == customerId ? new CustomerInfo(1L, "name", "surname", "123", "email") : null;
    }
}
