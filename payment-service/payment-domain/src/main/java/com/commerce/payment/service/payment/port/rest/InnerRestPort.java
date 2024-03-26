package com.commerce.payment.service.payment.port.rest;

import com.commerce.payment.service.payment.usecase.CustomerInfo;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */

public interface InnerRestPort {

    CustomerInfo getCustomerInfo(Long customerId);
}
