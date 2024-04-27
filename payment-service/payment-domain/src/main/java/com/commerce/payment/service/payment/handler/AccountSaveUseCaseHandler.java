package com.commerce.payment.service.payment.handler;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.handler.UseCaseHandler;
import com.commerce.payment.service.payment.model.Account;
import com.commerce.payment.service.payment.handler.helper.AccountSaveHelper;
import com.commerce.payment.service.payment.usecase.AccountSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

@DomainComponent
public class AccountSaveUseCaseHandler implements UseCaseHandler<Account, AccountSave> {

    private static final Logger logger = LoggerFactory.getLogger(AccountSaveUseCaseHandler.class);
    private final AccountSaveHelper accountSaveHelper;

    public AccountSaveUseCaseHandler(AccountSaveHelper accountSaveHelper) {
        this.accountSaveHelper = accountSaveHelper;
    }

    @Override
    public Account handle(AccountSave useCase) {
        logger.info("Account save action started by customerId: {}", useCase.customerId());
        return accountSaveHelper.save(useCase);
    }
}
