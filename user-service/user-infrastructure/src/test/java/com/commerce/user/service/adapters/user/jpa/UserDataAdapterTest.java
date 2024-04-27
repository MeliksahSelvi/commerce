package com.commerce.user.service.adapters.user.jpa;

import com.commerce.user.service.adapters.user.jpa.entity.UserEntity;
import com.commerce.user.service.adapters.user.jpa.repository.UserEntityRepository;
import com.commerce.user.service.user.model.User;
import com.commerce.user.service.user.usecase.UserDelete;
import com.commerce.user.service.user.usecase.UserRetrieve;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @Author mselvi
 * @Created 03.04.2024
 */

@ExtendWith(MockitoExtension.class)
class UserDataAdapterTest {

    @InjectMocks
    private UserDataAdapter userDataAdapter;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Test
    void should_save() {
        //given
        var user = buildUser();
        var userEntity = mock(UserEntity.class);
        when(userEntityRepository.save(any())).thenReturn(userEntity);
        when(userEntity.toModel()).thenReturn(user);

        //when
        var savedUser = userDataAdapter.save(user);

        //then
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(user.getCustomerId(), savedUser.getCustomerId());
        assertEquals(user.getRoleId(), savedUser.getRoleId());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    void should_findById() {
        //given
        var retrieve = new UserRetrieve(1L);
        when(userEntityRepository.findById(retrieve.userId())).thenReturn(Optional.of(buildUserEntity()));

        //when
        var userOptional = userDataAdapter.findById(retrieve);

        //then
        assertTrue(userOptional.isPresent());
        assertEquals(retrieve.userId(), userOptional.get().getId());
    }

    @Test
    void should_findById_empty() {
        //given
        var retrieve = new UserRetrieve(1L);
        when(userEntityRepository.findById(retrieve.userId())).thenReturn(Optional.empty());

        //when
        var userOptional = userDataAdapter.findById(retrieve);

        //then
        assertTrue(userOptional.isEmpty());
    }

    @Test
    void should_findByEmail() {
        //given
        var email = "user.mail@gmail.com";
        when(userEntityRepository.findByEmail(email)).thenReturn(Optional.of(buildUserEntity()));

        //when
        var userOptional = userDataAdapter.findByEmail(email);

        //then
        assertTrue(userOptional.isPresent());
        assertEquals(email, userOptional.get().getEmail());
    }

    @Test
    void should_findByEmail_empty() {
        //given
        var email = "user.mail@gmail.com";
        when(userEntityRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when
        var userOptional = userDataAdapter.findByEmail(email);

        //then
        assertTrue(userOptional.isEmpty());
    }

    @Test
    void should_deleteById() {
        //given
        var userDelete = new UserDelete(1L);

        //when
        userDataAdapter.deleteById(userDelete);

        //then
        assertEquals(Optional.empty(), userDataAdapter.findById(new UserRetrieve(1L)));
        verify(userEntityRepository).deleteById(userDelete.userId());
    }

    private User buildUser() {
        return User.builder()
                .id(1L)
                .customerId(1L)
                .roleId(1L)
                .email("user.mail@gmail.com")
                .password("123456")
                .build();
    }

    private UserEntity buildUserEntity() {
        var userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setCustomerId(1L);
        userEntity.setRoleId(1L);
        userEntity.setEmail("user.mail@gmail.com");
        userEntity.setPassword("123456");
        return userEntity;
    }
}
