package com.commerce.shipping.service.common.handler;

import com.commerce.shipping.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public interface UseCaseHandler<E, T extends UseCase> {

    E handle(T useCase);
}
