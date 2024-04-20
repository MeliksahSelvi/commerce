package com.commerce.customer.service.customer.handler.helper;

import com.commerce.customer.service.common.DomainComponent;
import com.commerce.customer.service.common.exception.CustomerNotFoundException;
import com.commerce.customer.service.customer.entity.Customer;
import com.commerce.customer.service.customer.port.jpa.CustomerDataPort;
import com.commerce.customer.service.customer.usecase.CustomerDelete;
import com.commerce.customer.service.customer.usecase.CustomerRetrieve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 15.04.2024
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
