package com.commerce.user.service.adapters.user.rest;

import com.commerce.user.service.adapters.user.rest.dto.JwtTokenResponse;
import com.commerce.user.service.adapters.user.rest.dto.UserLoginCommand;
import com.commerce.user.service.adapters.user.rest.dto.UserRegisterCommand;
import com.commerce.user.service.adapters.user.rest.dto.UserResponse;
import com.commerce.user.service.common.handler.UseCaseHandler;
import com.commerce.user.service.user.model.User;
import com.commerce.user.service.user.usecase.JwtToken;
import com.commerce.user.service.user.usecase.UserLogin;
import com.commerce.user.service.user.usecase.UserRegister;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mselvi
 * @Created 21.04.2024
 */

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private final UseCaseHandler<User, UserRegister> userRegisterUseCaseHandler;
    private final UseCaseHandler<JwtToken, UserLogin> userLoginUseCaseHandler;

    public AuthenticationController(UseCaseHandler<User, UserRegister> userRegisterUseCaseHandler,
                                    UseCaseHandler<JwtToken, UserLogin> userLoginUseCaseHandler) {
        this.userRegisterUseCaseHandler = userRegisterUseCaseHandler;
        this.userLoginUseCaseHandler = userLoginUseCaseHandler;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> userRegister(@RequestBody @Valid UserRegisterCommand userRegisterCommand) {
        var user = userRegisterUseCaseHandler.handle(userRegisterCommand.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> userLogin(@RequestBody @Valid UserLoginCommand userLoginCommand) {
        var jwtToken = userLoginUseCaseHandler.handle(userLoginCommand.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtTokenResponse(jwtToken));
    }
}
