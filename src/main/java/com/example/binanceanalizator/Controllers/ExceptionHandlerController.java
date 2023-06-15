package com.example.binanceanalizator.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
@ExceptionHandler(value= UsernameNotFoundException.class)
    ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e){
    return ResponseEntity.status(HttpStatusCode.valueOf(404))
            .body(e.getMessage());
}
}
