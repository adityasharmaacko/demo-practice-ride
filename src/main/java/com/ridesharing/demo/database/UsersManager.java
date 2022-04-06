package com.ridesharing.demo.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridesharing.demo.model.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UsersManager {

    List<User>listUsers = new ArrayList<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void registerUser(User user){
        listUsers.add(user);
    }

    public List<User> getAllUsers(){
        return listUsers;
    }

    @SneakyThrows
    public void updateOfferedCount(String userName){
        for(User user : listUsers){
            if(user.getUserName().equalsIgnoreCase(userName)){
                log.info("updating offered count of user: {}",objectMapper.writeValueAsString(user));
                Integer currentOffered = user.getOffered();
                user.setOffered(currentOffered+1);
            }
        }
    }

    @SneakyThrows
    public void updateTakenCount(String userName){
        for(User user : listUsers){
            if(user.getUserName().equalsIgnoreCase(userName)){
                log.info("updating taken count of user: {}",objectMapper.writeValueAsString(user));
                Integer currentTaken = user.getTaken();
                user.setTaken(currentTaken+1);
            }
        }
    }

}
