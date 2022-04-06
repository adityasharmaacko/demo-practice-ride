package com.ridesharing.demo.controllers;

import com.ridesharing.demo.exceptions.EntityAlreadyPresentException;
import com.ridesharing.demo.exceptions.EntityNotFoundException;
import com.ridesharing.demo.exceptions.GenericMessageException;
import com.ridesharing.demo.responses.ErrorResponse.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, EntityAlreadyPresentException.class, GenericMessageException.class})
    private ResponseEntity<Object>handleCustomExceptions(Exception ex){
        log.error(ex.getMessage());
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError.getApiResponse(), HttpStatus.BAD_REQUEST);
    }
}
