package com.commerce.payment.service.payment.model;

import com.commerce.payment.service.common.model.BaseModel;
import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.common.valueobject.Money;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class Account extends BaseModel {

    private Long customerId;
    private Money currentBalance;
    private final CurrencyType currencyType;
    private final String ibanNo;
    private LocalDateTime cancelDate;

    public void increaseCurrentBalance(Money difference, List<String> failureMessages) {
        if (difference==null || !difference.isGreaterThanZero()){
            failureMessages.add("Cost can't be zero and must be greater than zero!");
            return;
        }
        if (failureMessages.isEmpty()) {
            Money newBalance = currentBalance.add(difference);
            currentBalance = newBalance;
        }
    }

    public void decreaseCurrentBalance(Money difference, List<String> failureMessages) {
        if (difference == null || !difference.isGreaterThanZero()) {
            failureMessages.add("Cost can't be zero and must be greater than zero!");
            return;
        }
        if (!difference.isGreaterThanZero()) {
            failureMessages.add("Cost that you want making decrease must be greater than zero!");
        }

        Money newPrice = currentBalance.substract(difference);
        if (!newPrice.isGreaterThanZero()) {
            failureMessages.add("Price that you want making decrease mustn't be greater than old price!");
        }
        if (failureMessages.isEmpty()){
            currentBalance = newPrice;
        }
    }

    public void validateCurrentBalance(Money cost, Long customerId, List<String> failureMessages) {
        if (cost == null || !cost.isGreaterThanZero()) {
            failureMessages.add("Cost can't be zero and must be greater than zero!");
            return;
        }
        if (cost.isGreaterThan(currentBalance)) {
            failureMessages.add(String.format("Customer with id: %d doesn't have enough balance for payment!", customerId));
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
