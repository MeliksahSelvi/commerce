package com.commerce.user.service.adapters.role.rest;

import com.commerce.user.service.adapters.role.rest.dto.RoleSaveCommand;
import com.commerce.user.service.adapters.role.rest.dto.RoleSaveResponse;
import com.commerce.user.service.common.handler.UseCaseHandler;
import com.commerce.user.service.role.entity.Role;
import com.commerce.user.service.role.usecase.RoleSave;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final UseCaseHandler<Role, RoleSave> roleSaveUseCaseHandler;

    public RoleController(UseCaseHandler<Role, RoleSave> roleSaveUseCaseHandler) {
        this.roleSaveUseCaseHandler = roleSaveUseCaseHandler;
    }

    @PostMapping
    public ResponseEntity<RoleSaveResponse> save(@RequestBody RoleSaveCommand roleSaveCommand) {
        var role = roleSaveUseCaseHandler.handle(roleSaveCommand.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(new RoleSaveResponse(role));
    }
}
