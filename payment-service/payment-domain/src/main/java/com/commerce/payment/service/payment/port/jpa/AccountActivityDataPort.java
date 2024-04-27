package com.commerce.payment.service.payment.port.jpa;

import com.commerce.payment.service.payment.model.AccountActivity;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface AccountActivityDataPort {

    AccountActivity save(AccountActivity accountActivity);
}
