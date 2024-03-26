package com.commerce.order.service.order.port.rest;

import com.commerce.order.service.order.usecase.CustomerInfo;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public interface InnerRestPort {

    CustomerInfo getCustomerInfo(Long customerId);
}
