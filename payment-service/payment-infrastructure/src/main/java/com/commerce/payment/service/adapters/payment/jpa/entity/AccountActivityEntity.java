package com.commerce.payment.service.adapters.payment.jpa.entity;

import com.commerce.payment.service.common.model.AbstractEntity;
import com.commerce.payment.service.common.valueobject.ActivityType;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.payment.entity.AccountActivity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Entity
@Table(name = "ACCOUNT_ACTIVITY")
public class AccountActivityEntity extends AbstractEntity {

    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long accountId;

    @Column(name = "AMOUNT", precision = 15, scale = 2, nullable = false)
    private BigDecimal cost;

    @Column(name = "TRANSACTION_DATE", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "CURRENT_BALANCE", precision = 15, scale = 2, nullable = false)
    private BigDecimal currentBalance;

    @Column(name = "ACTIVITY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    public AccountActivity toModel() {
        return AccountActivity.builder()
                .id(getId())
                .accountId(accountId)
                .cost(new Money(cost))
                .transactionDate(transactionDate)
                .currentBalance(new Money(currentBalance))
                .activityType(activityType)
                .build();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }
}
