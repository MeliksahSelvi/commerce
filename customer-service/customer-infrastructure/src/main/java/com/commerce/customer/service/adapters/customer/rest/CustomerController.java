package com.commerce.customer.service.adapters.customer.rest;

import com.commerce.customer.service.adapters.customer.rest.dto.CustomerResponse;
import com.commerce.customer.service.adapters.customer.rest.dto.CustomerSaveCommand;
import com.commerce.customer.service.common.handler.UseCaseHandler;
import com.commerce.customer.service.customer.entity.Customer;
import com.commerce.customer.service.customer.usecase.CustomerRetrieve;
import com.commerce.customer.service.customer.usecase.CustomerRetrieveAll;
import com.commerce.customer.service.customer.usecase.CustomerSave;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {

    private final UseCaseHandler<List<Customer>, CustomerRetrieveAll> customerRetrieveAllUseCaseHandler;
    private final UseCaseHandler<Customer, CustomerRetrieve> customerRetrieveUseCaseHandler;
    private final UseCaseHandler<Customer, CustomerSave> customerSaveUseCaseHandler;

    public CustomerController(UseCaseHandler<List<Customer>, CustomerRetrieveAll> customerRetrieveAllUseCaseHandler, UseCaseHandler<Customer,
            CustomerRetrieve> customerRetrieveUseCaseHandler, UseCaseHandler<Customer, CustomerSave> customerSaveUseCaseHandler) {
        this.customerRetrieveAllUseCaseHandler = customerRetrieveAllUseCaseHandler;
        this.customerRetrieveUseCaseHandler = customerRetrieveUseCaseHandler;
        this.customerSaveUseCaseHandler = customerSaveUseCaseHandler;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        var customerList = customerRetrieveAllUseCaseHandler.handle(new CustomerRetrieveAll(pageOptional, sizeOptional));
        return ResponseEntity.ok(customerList.stream().map(CustomerResponse::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
        var customer = customerRetrieveUseCaseHandler.handle(new CustomerRetrieve(id));
        return ResponseEntity.ok(new CustomerResponse(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> save(@RequestBody @Valid CustomerSaveCommand customerSaveCommand) {
        var customer = customerSaveUseCaseHandler.handle(customerSaveCommand.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerResponse(customer));
    }
}
