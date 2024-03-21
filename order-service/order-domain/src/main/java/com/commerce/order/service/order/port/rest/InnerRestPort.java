package com.commerce.order.service.order.port.rest;

import com.commerce.order.service.order.usecase.CustomerResponse;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public interface InnerRestPort {

    CustomerResponse getCustomerInfo(Long customerId);
}
