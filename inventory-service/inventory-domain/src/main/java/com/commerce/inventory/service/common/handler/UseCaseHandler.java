package com.commerce.inventory.service.common.handler;

import com.commerce.inventory.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public interface UseCaseHandler<E, T extends UseCase> {

    E handle(T useCase);
}
