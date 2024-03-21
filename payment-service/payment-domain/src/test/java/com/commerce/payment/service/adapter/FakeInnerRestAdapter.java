package com.commerce.payment.service.adapter;

import com.commerce.payment.service.payment.port.rest.InnerRestPort;
import com.commerce.payment.service.payment.usecase.CustomerResponse;

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
