package com.commerce.user.service.adapters.user.jpa;

import com.commerce.user.service.adapters.user.jpa.entity.UserEntity;
import com.commerce.user.service.adapters.user.jpa.repository.UserEntityRepository;
import com.commerce.user.service.user.model.User;
import com.commerce.user.service.user.port.jpa.UserDataPort;
import com.commerce.user.service.user.usecase.UserDelete;
import com.commerce.user.service.user.usecase.UserRetrieve;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@Service
public class UserDataAdapter implements UserDataPort {

    private final UserEntityRepository repository;

    public UserDataAdapter(UserEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntityOptional = repository.findByEmail(email);
        return userEntityOptional.map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        var userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setCustomerId(user.getCustomerId());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setRoleId(user.getRoleId());
        return repository.save(userEntity).toModel();
    }

    @Override
    public void deleteById(UserDelete userDelete) {
        repository.deleteById(userDelete.userId());
    }

    @Override
    public Optional<User> findById(UserRetrieve userRetrieve) {
        return repository.findById(userRetrieve.userId()).map(UserEntity::toModel);
    }
}
