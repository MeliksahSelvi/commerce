package com.commerce.payment.service.customer.handler;

import com.commerce.payment.service.customer.entity.Customer;
import com.commerce.payment.service.customer.handler.helper.CustomerSaveHelper;
import com.commerce.payment.service.customer.usecase.CustomerSave;
import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.handler.UseCaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

@DomainComponent
public class CustomerSaveUseCaseHandler implements UseCaseHandler<Customer, CustomerSave> {

    private static final Logger logger= LoggerFactory.getLogger(CustomerSaveUseCaseHandler.class);

    private final CustomerSaveHelper customerSaveHelper;

    public CustomerSaveUseCaseHandler(CustomerSaveHelper customerSaveHelper) {
        this.customerSaveHelper = customerSaveHelper;
    }

    @Override
    public Customer handle(CustomerSave useCase) {
        logger.info("Customer save action started by email: {}",useCase.email());
        return customerSaveHelper.save(useCase);
    }
}
