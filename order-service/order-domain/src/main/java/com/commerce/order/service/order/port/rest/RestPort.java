package com.commerce.order.service.order.port.rest;

import com.commerce.order.service.order.usecase.CustomerResponse;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public interface RestPort {

    CustomerResponse getCustomerInfo(Long customerId);
}
