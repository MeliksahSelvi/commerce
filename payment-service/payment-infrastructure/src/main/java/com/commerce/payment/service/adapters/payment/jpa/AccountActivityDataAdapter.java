package com.commerce.payment.service.adapters.payment.jpa;

import com.commerce.payment.service.adapters.payment.jpa.entity.AccountActivityEntity;
import com.commerce.payment.service.adapters.payment.jpa.repository.AccountActivityEntityRepository;
import com.commerce.payment.service.payment.entity.AccountActivity;
import com.commerce.payment.service.payment.port.jpa.AccountActivityDataPort;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Service
public class AccountActivityDataAdapter implements AccountActivityDataPort {

    private final AccountActivityEntityRepository repository;

    public AccountActivityDataAdapter(AccountActivityEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountActivity save(AccountActivity accountActivity) {
        var accountActivityEntity = new AccountActivityEntity();
        accountActivityEntity.setId(accountActivity.getId());
        accountActivityEntity.setAccountId(accountActivity.getAccountId());
        accountActivityEntity.setActivityType(accountActivity.getActivityType());
        accountActivityEntity.setCost(accountActivity.getCost().amount());
        accountActivityEntity.setCurrentBalance(accountActivity.getCurrentBalance().amount());
        accountActivityEntity.setTransactionDate(accountActivity.getTransactionDate());
        return repository.save(accountActivityEntity).toModel();
    }
}
