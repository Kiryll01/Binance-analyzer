package com.example.binanceAnalyzer.Models.Factories;

import com.example.binanceAnalyzer.Models.Entities.Embedded.MovingAveragePropertiesEntity;
import com.example.binanceAnalyzer.Models.Entities.InMemory.MovingAverageProperties;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring"
//        ,implementationName = "MAPServiceMapper"
)
public interface MovingAveragePropertiesServiceMapper {
 MovingAveragePropertiesServiceMapper MOVING_AVERAGE_PROPERTIES_MAPPER= Mappers.getMapper(MovingAveragePropertiesServiceMapper.class);
  MovingAverageProperties toMovingAverageProperties(MovingAveragePropertiesEntity movingAveragePropertiesEntity);
  @InheritInverseConfiguration
  MovingAveragePropertiesEntity toMovingAveragePropertiesEntity(MovingAverageProperties movingAverageProperties);
   // @InheritInverseConfiguration
}
