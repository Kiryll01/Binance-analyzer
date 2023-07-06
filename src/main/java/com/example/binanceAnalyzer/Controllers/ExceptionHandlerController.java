package com.example.binanceAnalyzer.Controllers;

import com.example.binanceAnalyzer.Models.ValidationUtils.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
@ExceptionHandler(value= UsernameNotFoundException.class)
    ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e){
    return ResponseEntity.status(HttpStatusCode.valueOf(400))
            .body(e.getMessage());
}
@ExceptionHandler(value = ValidationException.class)
    ResponseEntity<String> handleValidationException(ValidationException e){
    return ResponseEntity.badRequest()
            .body(e.getMessage()+" "+ e);
}
//@ExceptionHandler(value = JsonProcessingException.class)
//    ResponseEntity<String> handleJsonProcessingException(JsonProcessingException e){
//    return ResponseEntity.internalServerError().build();
//}
}
