package com.commerce.customer.service.customer.handler.helper;

import com.commerce.customer.service.common.DomainComponent;
import com.commerce.customer.service.customer.entity.Customer;
import com.commerce.customer.service.customer.port.jpa.CustomerDataPort;
import com.commerce.customer.service.customer.port.security.EncryptingPort;
import com.commerce.customer.service.customer.usecase.CustomerSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author mselvi
 * @Created 20.03.2024
 */

@DomainComponent
public class CustomerSaveHelper {

    private static final Logger logger = LoggerFactory.getLogger(CustomerSaveHelper.class);
    private final CustomerDataPort customerDataPort;
    private final EncryptingPort encryptingPort;

    public CustomerSaveHelper(CustomerDataPort customerDataPort, EncryptingPort encryptingPort) {
        this.customerDataPort = customerDataPort;
        this.encryptingPort = encryptingPort;
    }

    @Transactional
    public Customer handle(CustomerSave useCase) {
        String encryptedPassword = encryptingPort.encrypt(useCase.password());
        Customer customer = buildCustomer(useCase, encryptedPassword);
        logger.info("Customer Saved for firstname and lastname: {} {}", useCase.firstName(), useCase.lastName());
        return customerDataPort.save(customer);
    }

    private Customer buildCustomer(CustomerSave useCase, String encryptedPassword) {
        return Customer.builder()
                .firstName(useCase.firstName())
                .lastName(useCase.lastName())
                .identityNo(useCase.identityNo())
                .email(useCase.email())
                .password(encryptedPassword)
                .build();
    }
}
