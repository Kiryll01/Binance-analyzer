package com.example.binanceAnalyzer.Controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ErrorHandlingUtils {
    public static ResponseEntity<?> returnBindingResultEntity(BindingResult bindingResult){
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bindingResult.getAllErrors());
    }
}
