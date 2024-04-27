package com.commerce.payment.service.adapters.payment.rest;

import com.commerce.payment.service.adapters.payment.rest.dto.AccountResponse;
import com.commerce.payment.service.adapters.payment.rest.dto.AccountSaveCommand;
import com.commerce.payment.service.common.handler.UseCaseHandler;
import com.commerce.payment.service.common.handler.VoidUseCaseHandler;
import com.commerce.payment.service.payment.model.Account;
import com.commerce.payment.service.payment.usecase.AccountDelete;
import com.commerce.payment.service.payment.usecase.AccountRetrieve;
import com.commerce.payment.service.payment.usecase.AccountRetrieveAll;
import com.commerce.payment.service.payment.usecase.AccountSave;
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
 * @Created 12.03.2024
 */

@RestController
@RequestMapping(value = "/api/v1/accounts")
public class AccountController {

    private final UseCaseHandler<List<Account>, AccountRetrieveAll> accountRetrieveAllUseCaseHandler;
    private final UseCaseHandler<Account, AccountRetrieve> accountRetrieveUseCaseHandler;
    private final VoidUseCaseHandler<AccountDelete> accountDeleteVoidUseCaseHandler;
    private final UseCaseHandler<Account, AccountSave> accountSaveUseCaseHandler;

    public AccountController(UseCaseHandler<List<Account>, AccountRetrieveAll> accountRetrieveAllUseCaseHandler,
                             UseCaseHandler<Account, AccountRetrieve> accountRetrieveUseCaseHandler,
                             VoidUseCaseHandler<AccountDelete> accountDeleteVoidUseCaseHandler, UseCaseHandler<Account, AccountSave> accountSaveUseCaseHandler) {
        this.accountRetrieveAllUseCaseHandler = accountRetrieveAllUseCaseHandler;
        this.accountRetrieveUseCaseHandler = accountRetrieveUseCaseHandler;
        this.accountDeleteVoidUseCaseHandler = accountDeleteVoidUseCaseHandler;
        this.accountSaveUseCaseHandler = accountSaveUseCaseHandler;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        var accountList = accountRetrieveAllUseCaseHandler.handle(new AccountRetrieveAll(pageOptional, sizeOptional));
        return ResponseEntity.ok(accountList.stream().map(AccountResponse::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable @NotNull @Positive Long id) {
        var account = accountRetrieveUseCaseHandler.handle(new AccountRetrieve(id));
        return ResponseEntity.ok(new AccountResponse(account));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> save(@RequestBody @Valid AccountSaveCommand accountSaveCommand) {
        var account = accountSaveUseCaseHandler.handle(accountSaveCommand.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponse(account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        accountDeleteVoidUseCaseHandler.handle(new AccountDelete(id));
        return ResponseEntity.ok().build();
    }
}
