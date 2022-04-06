package com.ridesharing.demo.exceptions;

public class EntityAlreadyPresentException extends RuntimeException{
    public EntityAlreadyPresentException(String message){
        super(message);
    }
}
