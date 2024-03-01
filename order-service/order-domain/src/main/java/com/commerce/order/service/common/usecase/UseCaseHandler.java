package com.commerce.order.service.common.usecase;

import com.commerce.order.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

public interface UseCaseHandler<E, T extends UseCase> {

    E handle(T useCase);
}
