package com.commerce.payment.service.adapters.payment.jpa;

import com.commerce.payment.service.adapters.payment.jpa.entity.AccountEntity;
import com.commerce.payment.service.adapters.payment.jpa.repository.AccountEntityRepository;
import com.commerce.payment.service.payment.model.Account;
import com.commerce.payment.service.payment.port.jpa.AccountDataPort;
import com.commerce.payment.service.payment.usecase.AccountDelete;
import com.commerce.payment.service.payment.usecase.AccountRetrieve;
import com.commerce.payment.service.payment.usecase.AccountRetrieveAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Service
public class AccountDataAdapter implements AccountDataPort {

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 10;
    private final AccountEntityRepository repository;

    public AccountDataAdapter(AccountEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Account> findByCustomerId(Long customerId) {
        Optional<AccountEntity> accountEntityOptional = repository.findByCustomerId(customerId);
        return accountEntityOptional.map(AccountEntity::toModel);
    }

    @Override
    public Optional<Account> findById(AccountRetrieve accountRetrieve) {
        Optional<AccountEntity> accountEntityOptional = repository.findById(accountRetrieve.accountId());
        return accountEntityOptional.map(AccountEntity::toModel);
    }

    @Override
    public Account save(Account account) {
        var accountEntity = new AccountEntity();
        accountEntity.setId(account.getId());
        accountEntity.setCustomerId(account.getCustomerId());
        accountEntity.setCurrentBalance(account.getCurrentBalance().amount());
        accountEntity.setCurrencyType(account.getCurrencyType());
        accountEntity.setIbanNo(account.getIbanNo());
        accountEntity.setCancelDate(account.getCancelDate());
        return repository.save(accountEntity).toModel();
    }

    @Override
    public List<Account> findAll(AccountRetrieveAll accountRetrieveAll) {
        PageRequest pageRequest = PageRequest.of(accountRetrieveAll.page().orElse(DEFAULT_PAGE), accountRetrieveAll.size().orElse(DEFAULT_SIZE));
        Page<AccountEntity> accountEntities = repository.findAll(pageRequest);
        return accountEntities.stream().map(AccountEntity::toModel).toList();
    }

    @Override
    public void deleteById(AccountDelete accountDelete) {
        repository.deleteById(accountDelete.accountId());
    }
}
