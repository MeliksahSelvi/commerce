package com.commerce.payment.service.customer.handler;

import com.commerce.payment.service.customer.model.Customer;
import com.commerce.payment.service.customer.port.jpa.CustomerDataPort;
import com.commerce.payment.service.customer.usecase.CustomerRetrieveAll;
import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.handler.UseCaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

@DomainComponent
public class CustomerRetrieveAllUseCaseHandler implements UseCaseHandler<List<Customer>, CustomerRetrieveAll> {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRetrieveAllUseCaseHandler.class);
    private final CustomerDataPort customerDataPort;

    public CustomerRetrieveAllUseCaseHandler(CustomerDataPort customerDataPort) {
        this.customerDataPort = customerDataPort;
    }

    @Override
    public List<Customer> handle(CustomerRetrieveAll useCase) {
        List<Customer> customerList = customerDataPort.findAll(useCase);
        logger.info("Customers Retrieved for page and size values : {} , {} ", useCase.page(), useCase.size());
        return customerList;
    }
}
