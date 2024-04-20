package com.commerce.payment.service.payment.handler;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.handler.VoidUseCaseHandler;
import com.commerce.payment.service.payment.handler.helper.AccountDeleteHelper;
import com.commerce.payment.service.payment.usecase.AccountDelete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class AccountDeleteVoidUseCaseHandler implements VoidUseCaseHandler<AccountDelete> {

    private final Logger logger = LoggerFactory.getLogger(AccountDeleteVoidUseCaseHandler.class);

    private final AccountDeleteHelper accountDeleteHelper;

    public AccountDeleteVoidUseCaseHandler(AccountDeleteHelper accountDeleteHelper) {
        this.accountDeleteHelper = accountDeleteHelper;
    }

    @Override
    public void handle(AccountDelete useCase) {
        logger.info("Account delete action started by id: {}", useCase.accountId());
        accountDeleteHelper.delete(useCase);
    }
}
