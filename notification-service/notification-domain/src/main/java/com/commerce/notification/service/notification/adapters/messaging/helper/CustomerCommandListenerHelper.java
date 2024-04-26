package com.commerce.notification.service.notification.adapters.messaging.helper;

import com.commerce.notification.service.common.DomainComponent;
import com.commerce.notification.service.notification.entity.Customer;
import com.commerce.notification.service.notification.port.jpa.CustomerDataPort;
import com.commerce.notification.service.notification.usecase.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

@DomainComponent
public class CustomerCommandListenerHelper {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCommandListenerHelper.class);
    private final CustomerDataPort customerDataPort;

    public CustomerCommandListenerHelper(CustomerDataPort customerDataPort) {
        this.customerDataPort = customerDataPort;
    }

    @Transactional
    public void save(CustomerInfo customerInfo) {
        Customer customer = buildCustomer(customerInfo);
        customerDataPort.save(customer);
        logger.info("Customer saved by email: {}", customerInfo.email());
    }

    private Customer buildCustomer(CustomerInfo customerInfo) {
        return Customer.builder()
                .id(customerInfo.id())
                .firstName(customerInfo.firstName())
                .lastName(customerInfo.lastName())
                .email(customerInfo.email())
                .build();
    }
}
