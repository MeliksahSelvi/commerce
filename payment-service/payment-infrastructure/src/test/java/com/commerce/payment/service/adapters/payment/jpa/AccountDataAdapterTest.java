package com.commerce.payment.service.adapters.payment.jpa;

import com.commerce.payment.service.adapters.payment.jpa.entity.AccountEntity;
import com.commerce.payment.service.adapters.payment.jpa.repository.AccountEntityRepository;
import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.payment.entity.Account;
import com.commerce.payment.service.payment.usecase.AccountRetrieve;
import com.commerce.payment.service.payment.usecase.AccountRetrieveAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class AccountDataAdapterTest {

    @InjectMocks
    private AccountDataAdapter adapter;

    @Mock
    private AccountEntityRepository repository;

    @Test
    void should_save(){
        //given
        var account = buildAccount();
        var accountEntity = mock(AccountEntity.class);
        when(repository.save(any())).thenReturn(accountEntity);
        when(accountEntity.toModel()).thenReturn(account);

        //when
        var savedAccount = adapter.save(account);

        //then
        assertNotNull(savedAccount);
        assertEquals(account.getId(), savedAccount.getId());
        assertEquals(account.getCustomerId(), savedAccount.getCustomerId());
        assertEquals(account.getCancelDate(), savedAccount.getCancelDate());
        assertEquals(account.getCurrencyType(), savedAccount.getCurrencyType());
        assertEquals(account.getCurrentBalance(), savedAccount.getCurrentBalance());
        assertEquals(account.getIbanNo(), savedAccount.getIbanNo());
    }

    @Test
    void should_findAll(){
        //given
        var accountRetrieveAll = new AccountRetrieveAll(Optional.of(0), Optional.of(1));
        var pageRequest = PageRequest.of(accountRetrieveAll.page().get(), accountRetrieveAll.size().get());
        var accountEntity = mock(AccountEntity.class);
        var list=new ArrayList<AccountEntity>();
        list.add(accountEntity);
        var page = new PageImpl<>(list);
        when(repository.findAll(pageRequest)).thenReturn(page);

        //when
        var accountList = adapter.findAll(accountRetrieveAll);

        //then
        assertEquals(accountList.size(), page.getSize());
        assertEquals(accountRetrieveAll.size().get(), accountList.size());
        assertEquals(accountRetrieveAll.size().get(), page.getSize());
        assertNotEquals(Collections.emptyList().size(), page.getSize());
        assertNotEquals(Collections.emptyList().size(), accountList.size());
    }

    @Test
    void should_findAll_empty(){
        //given
        var accountRetrieveAll = new AccountRetrieveAll(Optional.of(0), Optional.of(1));
        var pageRequest = PageRequest.of(accountRetrieveAll.page().get(), accountRetrieveAll.size().get());
        var page = new PageImpl<>(new ArrayList<AccountEntity>());
        when(repository.findAll(pageRequest)).thenReturn(page);

        //when
        var accountList = adapter.findAll(accountRetrieveAll);

        //then
        assertEquals(Collections.EMPTY_LIST.size(), accountList.size());
        assertEquals(Collections.EMPTY_LIST.size(), page.getSize());
        assertNotEquals(Collections.EMPTY_LIST.size(), accountRetrieveAll.size());
    }

    @Test
    void should_findById(){
        //given
        var retrieve = new AccountRetrieve(1L);
        var account = buildAccount();
        var accountEntity=mock(AccountEntity.class);
        when(accountEntity.toModel()).thenReturn(account);
        when(repository.findById(any())).thenReturn(Optional.of(accountEntity));

        //when
        var accountOptional = adapter.findById(retrieve);

        //then
        assertTrue(accountOptional.isPresent());
        assertEquals(retrieve.accountId(), accountOptional.get().getId());
    }

    @Test
    void should_findById_empty(){
        //given
        var retrieve = new AccountRetrieve(1L);
        when(repository.findById(any())).thenReturn(Optional.empty());

        //when
        var accountOptional = adapter.findById(retrieve);

        //then
        assertTrue(accountOptional.isEmpty());
    }

    @Test
    void should_findByCustomerId(){
        //given
        var account = buildAccount();
        var accountEntity = mock(AccountEntity.class);
        when(repository.findByCustomerId(any())).thenReturn(Optional.of(accountEntity));
        when(accountEntity.toModel()).thenReturn(account);

        //when
        var accountOptional = adapter.findByCustomerId(account.getCustomerId());

        //then
        assertTrue(accountOptional.isPresent());
        assertEquals(account.getCustomerId(), accountOptional.get().getCustomerId());
    }

    @Test
    void should_findByCustomerId_empty(){
        //given
        when(repository.findByCustomerId(any())).thenReturn(Optional.empty());

        //when
        var accountOptional = adapter.findByCustomerId(any());

        //then
        assertTrue(accountOptional.isEmpty());
    }

    private Account buildAccount(){
        return Account.builder()
                .id(1L)
                .ibanNo("iban")
                .currentBalance(new Money(new BigDecimal(100)))
                .currencyType(CurrencyType.TL)
                .cancelDate(null)
                .customerId(1L)
                .build();
    }
}
