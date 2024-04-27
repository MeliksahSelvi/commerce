package com.commerce.payment.service.payment.adapter;

import com.commerce.payment.service.payment.model.AccountActivity;
import com.commerce.payment.service.payment.port.jpa.AccountActivityDataPort;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeAccountActivityDataAdapter implements AccountActivityDataPort {
    @Override
    public AccountActivity save(AccountActivity accountActivity) {
        return AccountActivity.builder()
                .id(accountActivity.getId())
                .cost(accountActivity.getCost())
                .activityType(accountActivity.getActivityType())
                .accountId(accountActivity.getAccountId())
                .transactionDate(accountActivity.getTransactionDate())
                .currentBalance(accountActivity.getCurrentBalance())
                .build();
    }
}
