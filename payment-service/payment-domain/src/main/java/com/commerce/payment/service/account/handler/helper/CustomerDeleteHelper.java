package com.commerce.payment.service.account.handler.helper;

import com.commerce.payment.service.account.entity.Customer;
import com.commerce.payment.service.account.port.jpa.CustomerDataPort;
import com.commerce.payment.service.account.usecase.CustomerDelete;
import com.commerce.payment.service.account.usecase.CustomerRetrieve;
import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.CustomerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

@DomainComponent
public class CustomerDeleteHelper {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDeleteHelper.class);

    private final CustomerDataPort customerDataPort;

    public CustomerDeleteHelper(CustomerDataPort customerDataPort) {
        this.customerDataPort = customerDataPort;
    }

    @Transactional
    public void delete(CustomerDelete customerDelete) {
        checkCustomerExist(customerDelete);
        customerDataPort.deleteById(customerDelete);
        logger.info("Customer deleted by id: {}",customerDelete.customerId());
    }

    private void checkCustomerExist(CustomerDelete customerDelete) {
        Long customerId = customerDelete.customerId();
        Optional<Customer> customerOptional = customerDataPort.findById(new CustomerRetrieve(customerId));
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException(String.format("Customer Not Found By id: %d", customerId));
        }
    }
}
