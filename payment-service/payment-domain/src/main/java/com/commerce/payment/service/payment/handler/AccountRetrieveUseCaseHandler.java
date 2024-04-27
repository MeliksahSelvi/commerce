package com.commerce.payment.service.payment.handler;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.AccountNotFoundException;
import com.commerce.payment.service.common.handler.UseCaseHandler;
import com.commerce.payment.service.payment.model.Account;
import com.commerce.payment.service.payment.port.jpa.AccountDataPort;
import com.commerce.payment.service.payment.usecase.AccountRetrieve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

@DomainComponent
public class AccountRetrieveUseCaseHandler implements UseCaseHandler<Account, AccountRetrieve> {

    private static final Logger logger = LoggerFactory.getLogger(AccountRetrieveUseCaseHandler.class);
    private final AccountDataPort accountDataPort;

    public AccountRetrieveUseCaseHandler(AccountDataPort accountDataPort) {
        this.accountDataPort = accountDataPort;
    }

    @Override
    public Account handle(AccountRetrieve useCase) {
        Long accountId = useCase.accountId();
        Optional<Account> accountOptional = accountDataPort.findById(useCase);
        Account account = accountOptional.orElseThrow(() ->
                new AccountNotFoundException(String.format("Account could not be found by id: %d", accountId)));
        logger.info("Account retrieved for id : {}", accountId);
        return account;
    }
}
