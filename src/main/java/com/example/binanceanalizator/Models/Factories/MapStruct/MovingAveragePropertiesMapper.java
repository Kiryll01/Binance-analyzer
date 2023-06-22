package com.example.binanceanalizator.Models.Factories.MapStruct;

import com.example.binanceanalizator.Models.Entities.Embedded.MovingAveragePropertiesEntity;
import com.example.binanceanalizator.Models.Entities.InMemory.MovingAverageProperties;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovingAveragePropertiesMapper {
  MovingAveragePropertiesMapper MOVING_AVERAGE_PROPERTIES_MAPPER= Mappers.getMapper(MovingAveragePropertiesMapper.class);
  MovingAverageProperties toMovingAverageProperties(MovingAveragePropertiesEntity movingAveragePropertiesEntity);
  @InheritInverseConfiguration
  MovingAveragePropertiesEntity toMovingAveragePropertiesEntity(MovingAverageProperties movingAverageProperties);
   // @InheritInverseConfiguration
}
