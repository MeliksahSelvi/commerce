package com.commerce.order.query.service.common.handler;

import com.commerce.order.query.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public interface UseCaseHandler<E, T extends UseCase> {

    E handle(T useCase);
}
