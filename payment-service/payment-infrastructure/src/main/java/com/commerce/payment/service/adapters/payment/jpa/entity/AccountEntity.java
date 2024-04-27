package com.commerce.payment.service.adapters.payment.jpa.entity;

import com.commerce.payment.service.common.model.BaseEntity;
import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.payment.model.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Entity
@Table(name = "ACCOUNT")
public class AccountEntity extends BaseEntity {

    @Column(name = "CUSTOMER_ID",nullable = false)
    private Long customerId;

    @Column(name = "CURRENT_BALANCE", precision = 15, scale = 2,nullable = false)
    private BigDecimal currentBalance;

    @Column(name = "CURRENCY_TYPE",nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @Column(name = "IBAN_NO", length = 26,nullable = false,unique = true)
    private String ibanNo;

    @Column(name = "CANCEL_DATE")
    private LocalDateTime cancelDate;

    public Account toModel() {
        return Account.builder()
                .id(getId())
                .customerId(customerId)
                .currentBalance(new Money(currentBalance))
                .currencyType(currencyType)
                .ibanNo(ibanNo)
                .cancelDate(cancelDate)
                .build();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public String getIbanNo() {
        return ibanNo;
    }

    public void setIbanNo(String ibanNo) {
        this.ibanNo = ibanNo;
    }

    public LocalDateTime getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDateTime cancelDate) {
        this.cancelDate = cancelDate;
    }
}
