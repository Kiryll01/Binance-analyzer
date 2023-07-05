package com.example.binanceAnalyzer.Models.ValidationUtils;


import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@ToString
public class ValidationException extends Exception {
    private List<ObjectError> errors;
    private Class<?> clazz;

    public ValidationException(List<ObjectError> errors, Class<?> clazz, String message) {
        super(message);
        this.errors = errors;
        this.clazz=clazz;
    }
}
