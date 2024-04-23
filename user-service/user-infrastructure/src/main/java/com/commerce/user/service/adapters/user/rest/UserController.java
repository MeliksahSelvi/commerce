package com.commerce.user.service.adapters.user.rest;

import com.commerce.user.service.adapters.user.rest.dto.UserResponse;
import com.commerce.user.service.common.handler.UseCaseHandler;
import com.commerce.user.service.common.handler.VoidUseCaseHandler;
import com.commerce.user.service.user.entity.User;
import com.commerce.user.service.user.usecase.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UseCaseHandler<User, UserRetrieve> userRetrieveUseCaseHandler;
    private final VoidUseCaseHandler<UserDelete> userDeleteVoidUseCaseHandler;

    public UserController(UseCaseHandler<User, UserRetrieve> userRetrieveUseCaseHandler, VoidUseCaseHandler<UserDelete> userDeleteVoidUseCaseHandler) {
        this.userRetrieveUseCaseHandler = userRetrieveUseCaseHandler;
        this.userDeleteVoidUseCaseHandler = userDeleteVoidUseCaseHandler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        var user = userRetrieveUseCaseHandler.handle(new UserRetrieve(id));
        return ResponseEntity.ok(new UserResponse(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        userDeleteVoidUseCaseHandler.handle(new UserDelete(id));
        return ResponseEntity.ok().build();
    }
}
