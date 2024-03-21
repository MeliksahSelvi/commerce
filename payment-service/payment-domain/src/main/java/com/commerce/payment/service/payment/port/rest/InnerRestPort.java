package com.commerce.payment.service.payment.port.rest;

import com.commerce.payment.service.payment.usecase.CustomerResponse;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */

public interface InnerRestPort {

    CustomerResponse getCustomerInfo(Long customerId);
}
