package com.commerce.notification.service.notification.port.rest;

import com.commerce.notification.service.notification.usecase.CustomerResponse;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */


public interface InnerRestPort {

    CustomerResponse getCustomerInfo(Long customerId);
}
