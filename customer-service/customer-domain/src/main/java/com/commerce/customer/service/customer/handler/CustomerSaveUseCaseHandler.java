package com.commerce.customer.service.customer.handler;

import com.commerce.customer.service.common.DomainComponent;
import com.commerce.customer.service.common.handler.UseCaseHandler;
import com.commerce.customer.service.customer.entity.Customer;
import com.commerce.customer.service.customer.handler.helper.CustomerSaveHelper;
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
