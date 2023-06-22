package com.example.binanceanalizator.Controllers;

import com.example.binanceanalizator.Models.ValidationUtils.ValidationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
@ExceptionHandler(value = ValidationException.class)
    ResponseEntity<String> handleValidationException(ValidationException e){
    return ResponseEntity.badRequest()
            .body(e.getMessage()+" "+ e);
}
}
