package com.commerce.payment.service.payment.handler.helper;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.AccountNotFoundException;
import com.commerce.payment.service.payment.entity.Account;
import com.commerce.payment.service.payment.port.jpa.AccountDataPort;
import com.commerce.payment.service.payment.usecase.AccountDelete;
import com.commerce.payment.service.payment.usecase.AccountRetrieve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class AccountDeleteHelper {

    private static final Logger logger = LoggerFactory.getLogger(AccountDeleteHelper.class);

    private final AccountDataPort accountDataPort;

    public AccountDeleteHelper(AccountDataPort accountDataPort) {
        this.accountDataPort = accountDataPort;
    }

    @Transactional
    public void delete(AccountDelete accountDelete) {
        checkAccountExist(accountDelete);
        accountDataPort.deleteById(accountDelete);
        logger.info("Account deleted by id: {}", accountDelete.accountId());
    }

    private void checkAccountExist(AccountDelete accountDelete) {
        Optional<Account> accountOptional = accountDataPort.findById(new AccountRetrieve(accountDelete.accountId()));
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException(String.format("Account could not be found by id: %d", accountDelete.accountId()));
        }
    }
}
