package com.ridesharing.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridesharing.demo.requests.RegisterUserRequestDto;
import com.ridesharing.demo.responses.ApiResponse;
import com.ridesharing.demo.services.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @SneakyThrows
    public ResponseEntity<Object> registerUser(@RequestBody RegisterUserRequestDto registerUserRequestDto){
        log.info("Register user with details: {}",objectMapper.writeValueAsString(registerUserRequestDto));
        userService.registerUser(registerUserRequestDto);
        return ResponseEntity.ok(new ApiResponse("API successful"));
    }

    @RequestMapping(value = "/users/all" , method = RequestMethod.GET)
    @SneakyThrows
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
