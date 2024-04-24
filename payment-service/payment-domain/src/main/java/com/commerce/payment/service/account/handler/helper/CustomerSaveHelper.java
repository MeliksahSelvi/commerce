package com.commerce.payment.service.account.handler.helper;

import com.commerce.payment.service.account.entity.Customer;
import com.commerce.payment.service.account.port.jpa.CustomerDataPort;
import com.commerce.payment.service.account.port.security.EncryptingPort;
import com.commerce.payment.service.account.usecase.CustomerSave;
import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 24.04.2024
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
    public Customer save(CustomerSave useCase) {
        validateCustomerUniqueness(useCase);
        String encryptedPassword = encryptingPort.encrypt(useCase.password());
        Customer customer = buildCustomer(useCase, encryptedPassword);
        logger.info("Customer Saved for firstname and lastname: {} {}", useCase.firstName(), useCase.lastName());
        return customerDataPort.save(customer);
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
}
