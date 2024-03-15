package com.commerce.payment.service.payment.entity;

import com.commerce.payment.service.common.model.BaseEntity;
import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.common.valueobject.Money;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class Account extends BaseEntity {

    private Long customerId;
    private Money currentBalance;
    private final CurrencyType currencyType;
    private final String ibanNo;
    private LocalDateTime cancelDate;

    public void increaseCurrentBalance(Money difference) {
        Money newBalance = currentBalance.add(difference);
        currentBalance = newBalance;
    }

    public void decreaseCurrentBalance(Money difference, List<String> failureMessages) {
        if (!difference.isGreaterThanZero()) {
            failureMessages.add("Cost that you want making decrease must be greater than zero!");
        }

        Money newPrice = currentBalance.substract(difference);
        if (!newPrice.isGreaterThanZero()) {
            failureMessages.add("Price that you want making decrease mustn't be greater than old price!");
        }
        currentBalance = newPrice;
    }

    public void validateCurrentBalance(Payment payment, List<String> failureMessages) {
        if (payment.getCost().isGreaterThan(currentBalance)) {
            failureMessages.add(String.format("Customer with id: %d doesn't have enough balance for payment!", payment.getCustomerId()));
        }
    }

    private Account(Builder builder) {
        setId(builder.id);
        customerId = builder.customerId;
        currentBalance = builder.currentBalance;
        currencyType = builder.currencyType;
        ibanNo = builder.ibanNo;
        cancelDate = builder.cancelDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public Money getCurrentBalance() {
        return currentBalance;
    }

    public String getIbanNo() {
        return ibanNo;
    }

    public LocalDateTime getCancelDate() {
        return cancelDate;
    }

    public static final class Builder {
        private Long id;
        private Long customerId;
        private Money currentBalance;
        private CurrencyType currencyType;
        private String ibanNo;
        private LocalDateTime cancelDate;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder customerId(Long val) {
            customerId = val;
            return this;
        }

        public Builder ibanNo(String val) {
            ibanNo = val;
            return this;
        }

        public Builder currentBalance(Money val) {
            currentBalance = val;
            return this;
        }

        public Builder currencyType(CurrencyType val) {
            currencyType = val;
            return this;
        }

        public Builder cancelDate(LocalDateTime val) {
            cancelDate = val;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}