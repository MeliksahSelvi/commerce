package com.commerce.user.service.common.handler;

import com.commerce.user.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

public interface VoidUseCaseHandler<T extends UseCase> {

    void handle(T useCase);
}
