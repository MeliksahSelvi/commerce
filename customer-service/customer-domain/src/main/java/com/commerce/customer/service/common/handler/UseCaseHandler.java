package com.commerce.customer.service.common.handler;

import com.commerce.customer.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public interface UseCaseHandler<E, T extends UseCase> {

    E handle(T useCase);
}
