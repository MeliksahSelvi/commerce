package com.commerce.payment.service.payment.handler;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.handler.UseCaseHandler;
import com.commerce.payment.service.payment.entity.Account;
import com.commerce.payment.service.payment.port.jpa.AccountDataPort;
import com.commerce.payment.service.payment.usecase.AccountRetrieveAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

@DomainComponent
public class AccountRetrieveAllUseCaseHandler implements UseCaseHandler<List<Account>, AccountRetrieveAll>{

    private static final Logger logger = LoggerFactory.getLogger(AccountRetrieveAllUseCaseHandler.class);
    private final AccountDataPort accountDataPort;

    public AccountRetrieveAllUseCaseHandler(AccountDataPort accountDataPort) {
        this.accountDataPort = accountDataPort;
    }

    @Override
    public List<Account> handle(AccountRetrieveAll useCase) {
        List<Account> accountList = accountDataPort.findAll(useCase);
        logger.info("Accounts retrieved for page and size values : {} , {} ", useCase.page(), useCase.size());
        return accountList;
    }
}
