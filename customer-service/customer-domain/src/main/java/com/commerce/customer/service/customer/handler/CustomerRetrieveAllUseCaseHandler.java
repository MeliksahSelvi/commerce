package com.commerce.customer.service.customer.handler;

import com.commerce.customer.service.common.DomainComponent;
import com.commerce.customer.service.common.handler.UseCaseHandler;
import com.commerce.customer.service.customer.entity.Customer;
import com.commerce.customer.service.customer.port.jpa.CustomerDataPort;
import com.commerce.customer.service.customer.usecase.CustomerRetrieveAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author mselvi
 * @Created 10.03.2024
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
