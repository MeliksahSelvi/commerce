package com.commerce.user.service.user.usecase;

import com.commerce.user.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public record UserLogin(String email,String password) implements UseCase {
}
