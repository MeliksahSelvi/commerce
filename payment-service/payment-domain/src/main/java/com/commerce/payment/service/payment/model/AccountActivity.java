package com.commerce.payment.service.payment.model;

import com.commerce.payment.service.common.model.BaseModel;
import com.commerce.payment.service.common.valueobject.ActivityType;
import com.commerce.payment.service.common.valueobject.Money;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class AccountActivity extends BaseModel {

    private final Long accountId;
    private final Money cost;
    private final LocalDateTime transactionDate;
    private final Money currentBalance;
    private ActivityType activityType;

    public void initializeActivityType(ActivityType activityType, List<String> failureMessages) {
        this.activityType = failureMessages.isEmpty() ? activityType : ActivityType.FAIL;
    }

    private AccountActivity(Builder builder) {
        setId(builder.id);
        accountId = builder.accountId;
        cost = builder.cost;
        transactionDate = builder.transactionDate;
        currentBalance = builder.currentBalance;
        activityType = builder.activityType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getAccountId() {
        return accountId;
    }

    public Money getCost() {
        return cost;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Money getCurrentBalance() {
        return currentBalance;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public static final class Builder {
        private Long id;
        private Long accountId;
        private Money cost;
        private LocalDateTime transactionDate;
        private Money currentBalance;
        private ActivityType activityType;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder accountId(Long val) {
            accountId = val;
            return this;
        }

        public Builder cost(Money val) {
            cost = val;
            return this;
        }

        public Builder transactionDate(LocalDateTime val) {
            transactionDate = val;
            return this;
        }

        public Builder currentBalance(Money val) {
            currentBalance = val;
            return this;
        }

        public Builder activityType(ActivityType val) {
            activityType = val;
            return this;
        }

        public AccountActivity build() {
            return new AccountActivity(this);
        }
    }
}
