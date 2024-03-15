package com.commerce.payment.service.common.handler;

import com.commerce.payment.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public interface UseCaseHandler<E, T extends UseCase> {

    E handle(T useCase);
}
