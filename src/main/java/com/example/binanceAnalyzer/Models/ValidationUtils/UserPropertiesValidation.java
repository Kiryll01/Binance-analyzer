package com.example.binanceAnalyzer.Models.ValidationUtils;

import com.example.binanceAnalyzer.Models.Abstract.AbstractMovingAverageProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserPropertiesValidation {
public static void checkMovingAverageProperties(AbstractMovingAverageProperties properties) throws ValidationException {

    List<ObjectError> errors=new ArrayList<>(2);

    if((properties.getLongMillisInterval()-properties.getShortMillisInterval())<0) errors.add(new ObjectError(properties.getClass().getName(),"long interval should not be less than short"));

    if(!errors.isEmpty()) throw new ValidationException(errors, AbstractMovingAverageProperties.class,"moving_average_properties error");

}
}
