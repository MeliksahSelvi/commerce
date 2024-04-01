package com.commerce.user.service.common.handler;

import com.commerce.user.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public interface UseCaseHandler<E, T extends UseCase> {

    E handle(T useCase);
}
