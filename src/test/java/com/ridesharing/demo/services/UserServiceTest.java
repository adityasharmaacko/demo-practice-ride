package com.ridesharing.demo.services;

import com.ridesharing.demo.database.UsersManager;
import com.ridesharing.demo.model.User;
import com.ridesharing.demo.requests.RegisterUserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UsersManager usersManager;
    private UserService userServiceTest;

    @BeforeEach
    void setup(){
        userServiceTest = new UserService(usersManager);
    }

    @Test
    void registerUser() {
        RegisterUserRequestDto registerUserRequestDto = RegisterUserRequestDto.builder()
                .age("22")
                .userName("Aditya")
                .gender("M")
                .build();
        userServiceTest.registerUser(registerUserRequestDto);
        ArgumentCaptor<User>userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(usersManager).registerUser(userArgumentCaptor.capture());
    }

    @Test
    void getAllUsers() {
        // when
        userServiceTest.getAllUsers();
        // then
        verify(usersManager).getAllUsers();
    }
}