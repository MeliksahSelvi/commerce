package com.commerce.payment.service.customer.handler;

import com.commerce.payment.service.customer.entity.Customer;
import com.commerce.payment.service.customer.port.jpa.CustomerDataPort;
import com.commerce.payment.service.customer.usecase.CustomerRetrieve;
import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.CustomerNotFoundException;
import com.commerce.payment.service.common.handler.UseCaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

@DomainComponent
public class CustomerRetrieveUseCaseHandler implements UseCaseHandler<Customer, CustomerRetrieve> {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRetrieveUseCaseHandler.class);
    private final CustomerDataPort customerDataPort;

    public CustomerRetrieveUseCaseHandler(CustomerDataPort customerDataPort) {
        this.customerDataPort = customerDataPort;
    }

    @Override
    public Customer handle(CustomerRetrieve useCase) {
        Customer customer = findCustomer(useCase);
        logger.info("Customer Retrieved for id : {}", useCase.customerId());
        return customer;
    }

    private Customer findCustomer(CustomerRetrieve useCase) {
        Optional<Customer> customerOptional = customerDataPort.findById(useCase);
        return customerOptional.orElseThrow(
                () -> new CustomerNotFoundException(String.format("Customer Not Found By id: %d", useCase.customerId())));
    }
}
