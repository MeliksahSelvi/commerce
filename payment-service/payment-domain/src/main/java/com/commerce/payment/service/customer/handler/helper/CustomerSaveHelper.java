package com.commerce.payment.service.customer.handler.helper;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import com.commerce.payment.service.customer.entity.Customer;
import com.commerce.payment.service.customer.port.jpa.CustomerDataPort;
import com.commerce.payment.service.customer.port.security.EncryptingPort;
import com.commerce.payment.service.customer.usecase.CustomerInfo;
import com.commerce.payment.service.customer.usecase.CustomerSave;
import com.commerce.payment.service.customer.port.messaging.output.CustomerCommandMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

@DomainComponent
public class CustomerSaveHelper {

    private static final Logger logger = LoggerFactory.getLogger(CustomerSaveHelper.class);
    private final CustomerCommandMessagePublisher customerCommandMessagePublisher;
    private final CustomerDataPort customerDataPort;
    private final EncryptingPort encryptingPort;

    public CustomerSaveHelper(CustomerCommandMessagePublisher customerCommandMessagePublisher, CustomerDataPort customerDataPort,
                              EncryptingPort encryptingPort) {
        this.customerCommandMessagePublisher = customerCommandMessagePublisher;
        this.customerDataPort = customerDataPort;
        this.encryptingPort = encryptingPort;
    }

    @Transactional
    public Customer save(CustomerSave useCase) {
        validateCustomerUniqueness(useCase);
        String encryptedPassword = encryptingPort.encrypt(useCase.password());
        Customer customer = buildCustomer(useCase, encryptedPassword);
        Customer savedCustomer = customerDataPort.save(customer);
        logger.info("Customer Saved for firstname and lastname: {} {}", savedCustomer.getFirstName(), savedCustomer.getLastName());
        sendCustomerCommandToQueue(savedCustomer);
        return savedCustomer;
    }

    private void validateCustomerUniqueness(CustomerSave useCase) {
        Optional<Customer> customerOptional = customerDataPort.findByEmailOrIdentityNo(useCase.email(), useCase.identityNo());
        if (customerOptional.isPresent()) {
            throw new PaymentDomainException("Email or Identity No must be unique!");
        }
    }

    private Customer buildCustomer(CustomerSave useCase, String encryptedPassword) {
        return Customer.builder()
                .id(useCase.customerId())
                .firstName(useCase.firstName())
                .lastName(useCase.lastName())
                .identityNo(useCase.identityNo())
                .email(useCase.email())
                .password(encryptedPassword)
                .build();
    }

    private void sendCustomerCommandToQueue(Customer savedCustomer) {
        CompletableFuture.runAsync(() -> customerCommandMessagePublisher.publish(new CustomerInfo(savedCustomer)));
    }
}
