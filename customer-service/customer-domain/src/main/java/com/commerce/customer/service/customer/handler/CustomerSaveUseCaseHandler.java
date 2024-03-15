package com.commerce.customer.service.customer.handler;

import com.commerce.customer.service.common.DomainComponent;
import com.commerce.customer.service.common.handler.UseCaseHandler;
import com.commerce.customer.service.customer.entity.Customer;
import com.commerce.customer.service.customer.port.jpa.CustomerDataPort;
import com.commerce.customer.service.customer.port.security.EncryptingPort;
import com.commerce.customer.service.customer.usecase.CustomerSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

@DomainComponent
public class CustomerSaveUseCaseHandler implements UseCaseHandler<Customer, CustomerSave> {

    private static final Logger logger= LoggerFactory.getLogger(CustomerSaveUseCaseHandler.class);
    private final CustomerDataPort customerDataPort;
    private final EncryptingPort encryptingPort;

    public CustomerSaveUseCaseHandler(CustomerDataPort customerDataPort, EncryptingPort encryptingPort) {
        this.customerDataPort = customerDataPort;
        this.encryptingPort = encryptingPort;
    }

    @Override
    public Customer handle(CustomerSave useCase) {
        String encryptedPassword = encryptingPort.encrypt(useCase.password());
        Customer customer= Customer.builder()
                .firstName(useCase.firstName())
                .lastName(useCase.lastName())
                .identityNo(useCase.identityNo())
                .email(useCase.email())
                .password(encryptedPassword)
                .build();
        logger.info("Customer Saved for firstname and lastname: {} {}",useCase.firstName(),useCase.lastName());
        return customerDataPort.save(customer);
    }
}
