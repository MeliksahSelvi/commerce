package com.commerce.customer.service.customer.handler;

import com.commerce.customer.service.common.DomainComponent;
import com.commerce.customer.service.common.handler.UseCaseHandler;
import com.commerce.customer.service.customer.entity.Customer;
import com.commerce.customer.service.customer.handler.helper.CustomerSaveHelper;
import com.commerce.customer.service.customer.usecase.CustomerSave;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

@DomainComponent
public class CustomerSaveUseCaseHandler implements UseCaseHandler<Customer, CustomerSave> {

    private final CustomerSaveHelper customerSaveHelper;

    public CustomerSaveUseCaseHandler(CustomerSaveHelper customerSaveHelper) {
        this.customerSaveHelper = customerSaveHelper;
    }

    @Override
    public Customer handle(CustomerSave useCase) {
        return customerSaveHelper.save(useCase);
    }
}
