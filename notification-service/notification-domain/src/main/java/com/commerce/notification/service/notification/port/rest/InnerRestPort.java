package com.commerce.notification.service.notification.port.rest;

import com.commerce.notification.service.notification.usecase.CustomerInfo;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */


public interface InnerRestPort {

    CustomerInfo getCustomerInfo(Long customerId);
}
