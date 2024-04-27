package com.commerce.user.service.adapters.role.rest;

import com.commerce.user.service.adapters.role.rest.dto.RoleResponse;
import com.commerce.user.service.adapters.role.rest.dto.RoleSaveCommand;
import com.commerce.user.service.adapters.role.rest.dto.RoleSaveResponse;
import com.commerce.user.service.common.handler.UseCaseHandler;
import com.commerce.user.service.common.handler.VoidUseCaseHandler;
import com.commerce.user.service.role.model.Role;
import com.commerce.user.service.role.usecase.RoleDelete;
import com.commerce.user.service.role.usecase.RoleRetrieve;
import com.commerce.user.service.role.usecase.RoleSave;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final UseCaseHandler<Role, RoleRetrieve> roleRetrieveUseCaseHandler;
    private final VoidUseCaseHandler<RoleDelete> roleDeleteVoidUseCaseHandler;
    private final UseCaseHandler<Role, RoleSave> roleSaveUseCaseHandler;

    public RoleController(UseCaseHandler<Role, RoleRetrieve> roleRetrieveUseCaseHandler, UseCaseHandler<Role, RoleSave> roleSaveUseCaseHandler,
                          VoidUseCaseHandler<RoleDelete> roleDeleteVoidUseCaseHandler) {
        this.roleRetrieveUseCaseHandler = roleRetrieveUseCaseHandler;
        this.roleSaveUseCaseHandler = roleSaveUseCaseHandler;
        this.roleDeleteVoidUseCaseHandler = roleDeleteVoidUseCaseHandler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findById(@PathVariable @NotNull @Positive Long id) {
        var role = roleRetrieveUseCaseHandler.handle(new RoleRetrieve(id));
        return ResponseEntity.ok(new RoleResponse(role));
    }

    @PostMapping
    public ResponseEntity<RoleSaveResponse> save(@RequestBody @Valid RoleSaveCommand roleSaveCommand) {
        var role = roleSaveUseCaseHandler.handle(roleSaveCommand.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(new RoleSaveResponse(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        roleDeleteVoidUseCaseHandler.handle(new RoleDelete(id));
        return ResponseEntity.ok().build();
    }
}
