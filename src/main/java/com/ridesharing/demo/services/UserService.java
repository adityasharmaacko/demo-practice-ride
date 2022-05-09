package com.ridesharing.demo.services;

import com.ridesharing.demo.database.UsersManager;
import com.ridesharing.demo.model.User;
import com.ridesharing.demo.requests.RegisterUserRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UsersManager usersManager;

    public void registerUser(RegisterUserRequestDto registerUserRequestDto){
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .userName(registerUserRequestDto.getUserName())
                .gender(registerUserRequestDto.getGender())
                .age(registerUserRequestDto.getAge())
                .offered(0)
                .taken(0)
                .build();
        usersManager.registerUser(user);
    }

    public List<User> getAllUsers(){
        return usersManager.getAllUsers();
    }

}
