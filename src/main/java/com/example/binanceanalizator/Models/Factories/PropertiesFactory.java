package com.example.binanceanalizator.Models.Factories;

import com.example.binanceanalizator.Models.Entities.Embedded.MovingAveragePropertiesEntity;
import com.example.binanceanalizator.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceanalizator.Models.Entities.InMemory.MovingAverageProperties;
import com.example.binanceanalizator.Models.UserProperties;
import org.springframework.stereotype.Component;
@Component

public class PropertiesFactory {
public static MovingAverageProperties makeMovingAverageProperties(MovingAveragePropertiesEntity entity){
   return MovingAverageProperties.builder()
            .shortMillisInterval(entity.getShortMillisInterval())
           .longMillisInterval(entity.getLongMillisInterval())
            .build();

}
public static UserProperties makeUserProperties(UserPropertiesEntity entity){
   return UserProperties.builder()
           .role(entity.getRole())
           .build();
}
}
