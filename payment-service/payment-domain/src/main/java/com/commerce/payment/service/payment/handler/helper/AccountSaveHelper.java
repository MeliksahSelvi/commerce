package com.commerce.payment.service.payment.handler.helper;

import com.commerce.payment.service.account.entity.Customer;
import com.commerce.payment.service.account.port.jpa.CustomerDataPort;
import com.commerce.payment.service.account.usecase.CustomerRetrieve;
import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.CustomerNotFoundException;
import com.commerce.payment.service.payment.entity.Account;
import com.commerce.payment.service.payment.port.generate.RandomGeneratePort;
import com.commerce.payment.service.payment.port.jpa.AccountDataPort;
import com.commerce.payment.service.payment.usecase.AccountSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class AccountSaveHelper {

    private static final Logger logger = LoggerFactory.getLogger(AccountSaveHelper.class);

    private final RandomGeneratePort randomGeneratePort;
    private final CustomerDataPort customerDataPort;
    private final AccountDataPort accountDataPort;

    public AccountSaveHelper(RandomGeneratePort randomGeneratePort, CustomerDataPort customerDataPort, AccountDataPort accountDataPort) {
        this.randomGeneratePort = randomGeneratePort;
        this.customerDataPort = customerDataPort;
        this.accountDataPort = accountDataPort;
    }

    @Transactional
    public Account save(AccountSave useCase) {
        Long customerId = useCase.customerId();
        logger.info("Checking customer by id: {]", useCase.customerId());
        checkCustomer(customerId);

        Account account = buildAccount(useCase);
        logger.info("Account initiated by customerId: {}", customerId);

        Account savedAccount = accountDataPort.save(account);
        logger.info("Account persisted by customerId: {}", customerId);
        return savedAccount;
    }

    private void checkCustomer(Long customerId) {
        Optional<Customer> customerOptional = customerDataPort.findById(new CustomerRetrieve(customerId));
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException(String.format("Customer could not found for account save operation by customerId: %d", customerId));
        }
    }

    private Account buildAccount(AccountSave useCase) {
        return Account.builder()
                .id(useCase.accountId())
                .customerId(useCase.customerId())
                .currencyType(useCase.currencyType())
                .currentBalance(useCase.currentBalance())
                .ibanNo(generateIbanNo())
                .build();
    }

    private String generateIbanNo() {
        return randomGeneratePort.randomNumericAsString(26);
    }
}
