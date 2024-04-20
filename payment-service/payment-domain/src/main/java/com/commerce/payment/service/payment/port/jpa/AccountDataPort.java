package com.commerce.payment.service.payment.port.jpa;

import com.commerce.payment.service.payment.entity.Account;
import com.commerce.payment.service.payment.usecase.AccountDelete;
import com.commerce.payment.service.payment.usecase.AccountRetrieve;
import com.commerce.payment.service.payment.usecase.AccountRetrieveAll;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface AccountDataPort {

    Optional<Account> findByCustomerId(Long customerId);

    Optional<Account> findById(AccountRetrieve accountRetrieve);

    Account save(Account account);

    List<Account> findAll(AccountRetrieveAll accountRetrieveAll);

    void deleteById(AccountDelete accountDelete);
}
