package com.commerce.payment.service.adapters.payment.jpa;

import com.commerce.payment.service.adapters.payment.jpa.entity.AccountActivityEntity;
import com.commerce.payment.service.adapters.payment.jpa.repository.AccountActivityEntityRepository;
import com.commerce.payment.service.common.valueobject.ActivityType;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.payment.entity.AccountActivity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class AccountActivityDataAdapterTest {

    @InjectMocks
    private AccountActivityDataAdapter adapter;

    @Mock
    private AccountActivityEntityRepository repository;

    @Test
    void should_save() {
        //given
        var accountActivity = buildAccountActivity();
        var accountActivityEntity=mock(AccountActivityEntity.class);
        when(repository.save(any())).thenReturn(accountActivityEntity);
        when(accountActivityEntity.toModel()).thenReturn(accountActivity);

        //when
        var savedAccountActivity = adapter.save(accountActivity);

        //then
        assertNotNull(savedAccountActivity);
        assertEquals(savedAccountActivity.getId(),accountActivity.getId());
        assertEquals(savedAccountActivity.getAccountId(),accountActivity.getAccountId());
        assertEquals(savedAccountActivity.getActivityType(),accountActivity.getActivityType());
        assertEquals(savedAccountActivity.getCurrentBalance(),accountActivity.getCurrentBalance());
        assertEquals(savedAccountActivity.getCost(),accountActivity.getCost());
        assertEquals(savedAccountActivity.getTransactionDate(),accountActivity.getTransactionDate());
    }

    private AccountActivity buildAccountActivity() {
        return AccountActivity.builder()
                .id(1L)
                .accountId(1L)
                .activityType(ActivityType.GET)
                .currentBalance(new Money(BigDecimal.TEN))
                .cost(new Money(BigDecimal.ONE))
                .transactionDate(LocalDateTime.now())
                .build();
    }
}
