package com.commerce.payment.service.adapters.customer.rest;

import com.commerce.payment.service.customer.model.Customer;
import com.commerce.payment.service.customer.usecase.CustomerDelete;
import com.commerce.payment.service.customer.usecase.CustomerRetrieve;
import com.commerce.payment.service.customer.usecase.CustomerRetrieveAll;
import com.commerce.payment.service.customer.usecase.CustomerSave;
import com.commerce.payment.service.adapters.customer.rest.dto.CustomerResponse;
import com.commerce.payment.service.adapters.customer.rest.dto.CustomerSaveCommand;
import com.commerce.payment.service.common.handler.UseCaseHandler;
import com.commerce.payment.service.common.handler.VoidUseCaseHandler;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {

    private final UseCaseHandler<List<Customer>, CustomerRetrieveAll> customerRetrieveAllUseCaseHandler;
    private final UseCaseHandler<Customer, CustomerRetrieve> customerRetrieveUseCaseHandler;
    private final VoidUseCaseHandler<CustomerDelete> customerDeleteVoidUseCaseHandler;
    private final UseCaseHandler<Customer, CustomerSave> customerSaveUseCaseHandler;

    public CustomerController(UseCaseHandler<List<Customer>, CustomerRetrieveAll> customerRetrieveAllUseCaseHandler,
                              UseCaseHandler<Customer, CustomerRetrieve> customerRetrieveUseCaseHandler,
                              VoidUseCaseHandler<CustomerDelete> customerDeleteVoidUseCaseHandler,
                              UseCaseHandler<Customer, CustomerSave> customerSaveUseCaseHandler) {
        this.customerRetrieveAllUseCaseHandler = customerRetrieveAllUseCaseHandler;
        this.customerRetrieveUseCaseHandler = customerRetrieveUseCaseHandler;
        this.customerDeleteVoidUseCaseHandler = customerDeleteVoidUseCaseHandler;
        this.customerSaveUseCaseHandler = customerSaveUseCaseHandler;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        var customerList = customerRetrieveAllUseCaseHandler.handle(new CustomerRetrieveAll(pageOptional, sizeOptional));
        return ResponseEntity.ok(customerList.stream().map(CustomerResponse::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable @NotNull @Positive Long id) {
        var customer = customerRetrieveUseCaseHandler.handle(new CustomerRetrieve(id));
        return ResponseEntity.ok(new CustomerResponse(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> save(@RequestBody @Valid CustomerSaveCommand customerSaveCommand) {
        var customer = customerSaveUseCaseHandler.handle(customerSaveCommand.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerResponse(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        customerDeleteVoidUseCaseHandler.handle(new CustomerDelete(id));
        return ResponseEntity.ok().build();
    }
}
