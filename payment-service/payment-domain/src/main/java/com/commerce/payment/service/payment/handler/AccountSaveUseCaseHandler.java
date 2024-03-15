package com.commerce.payment.service.payment.handler;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import com.commerce.payment.service.common.handler.UseCaseHandler;
import com.commerce.payment.service.payment.entity.Account;
import com.commerce.payment.service.payment.port.generate.RandomGeneratePort;
import com.commerce.payment.service.payment.port.jpa.AccountDataPort;
import com.commerce.payment.service.payment.port.rest.RestPort;
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
    private final RandomGeneratePort randomGeneratePort;
    private final AccountDataPort accountDataPort;
    private final RestPort restPort;

    public AccountSaveUseCaseHandler(RandomGeneratePort randomGeneratePort, AccountDataPort accountDataPort, RestPort restPort) {
        this.randomGeneratePort = randomGeneratePort;
        this.accountDataPort = accountDataPort;
        this.restPort = restPort;
    }

    @Override
    public Account handle(AccountSave useCase) {
        Long customerId = useCase.customerId();
        boolean existCustomer = restPort.isExistCustomer(customerId);//todo eğer hata çıkarsa handle etmemiz gerek customer ayakta değilse mesela
        if (!existCustomer){
            throw new PaymentDomainException(String.format("Customer could not found for account save operation by customerId %d",customerId));
        }
        Account account = Account.builder()
                .customerId(customerId)
                .currencyType(useCase.currencyType())
                .currentBalance(useCase.currentBalance())
                .ibanNo(generateIbanNo())
                .build();
        Account savedAccount = accountDataPort.save(account);
        logger.info("Account saved by customerId {}", customerId);
        return savedAccount;
    }

    private String generateIbanNo() {
        return randomGeneratePort.randomNumericAsString(26);
    }
}
