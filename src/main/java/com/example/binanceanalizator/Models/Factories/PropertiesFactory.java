package com.example.binanceanalizator.Models.Factories;

import com.example.binanceanalizator.Models.Entities.Embedded.MovingAveragePropertiesEntity;
import com.example.binanceanalizator.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceanalizator.Models.Entities.InMemory.MovingAverageProperties;
import com.example.binanceanalizator.Models.Entities.InMemory.UserProperties;
import com.example.binanceanalizator.Models.Factories.MapStruct.MovingAveragePropertiesMapper;
import com.example.binanceanalizator.Models.Factories.MapStruct.UserPropertiesMapper;
import org.springframework.stereotype.Component;
@Component

public class PropertiesFactory {
public static MovingAverageProperties makeMovingAverageProperties(MovingAveragePropertiesEntity entity){
   return MovingAveragePropertiesMapper.MOVING_AVERAGE_PROPERTIES_MAPPER.toMovingAverageProperties(entity);

//           MovingAverageProperties.builder()
//            .shortMillisInterval(entity.getShortMillisInterval())
//           .longMillisInterval(entity.getLongMillisInterval())
//            .build();

}
public static UserProperties makeUserProperties(UserPropertiesEntity entity){
return UserPropertiesMapper.USER_PROPERTIES_MAPPER.toUserProperties(entity);
   //   return UserProperties.builder()
//           .role(entity.getRole())
//           .build();
//
}
}
