package com.commerce.customer.service.customer.handler;

import com.commerce.customer.service.common.DomainComponent;
import com.commerce.customer.service.common.handler.UseCaseHandler;
import com.commerce.customer.service.customer.port.jpa.CustomerDataPort;
import com.commerce.customer.service.customer.usecase.CustomerRetrieve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */

@DomainComponent
public class CustomerExistUseCaseHandler implements UseCaseHandler<Boolean, CustomerRetrieve> {

    private static final Logger logger = LoggerFactory.getLogger(CustomerExistUseCaseHandler.class);
    private final CustomerDataPort customerDataPort;

    public CustomerExistUseCaseHandler(CustomerDataPort customerDataPort) {
        this.customerDataPort = customerDataPort;
    }

    @Override
    public Boolean handle(CustomerRetrieve useCase) {
        Long customerId = useCase.customerId();
        boolean isExist = customerDataPort.existById(useCase);
        logger.info("Customer existence was questioned for id : {}", customerId);
        return isExist;
    }
}
