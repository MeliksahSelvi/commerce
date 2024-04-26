package com.commerce.payment.service.customer.handler;

import com.commerce.payment.service.customer.handler.helper.CustomerDeleteHelper;
import com.commerce.payment.service.customer.usecase.CustomerDelete;
import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.handler.VoidUseCaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

@DomainComponent
public class CustomerDeleteVoidUseCaseHandler implements VoidUseCaseHandler<CustomerDelete> {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDeleteVoidUseCaseHandler.class);

    private final CustomerDeleteHelper customerDeleteHelper;

    public CustomerDeleteVoidUseCaseHandler(CustomerDeleteHelper customerDeleteHelper) {
        this.customerDeleteHelper = customerDeleteHelper;
    }

    @Override
    public void handle(CustomerDelete useCase) {
        logger.info("Customer delete action started by id: {}", useCase.customerId());
        customerDeleteHelper.delete(useCase);
    }
}
