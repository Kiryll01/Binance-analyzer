package com.example.binanceAnalyzer.Models.Factories;

import com.example.binanceAnalyzer.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceAnalyzer.Models.Entities.InMemory.UserProperties;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",uses = {MovingAveragePropertiesServiceMapper.class}
      //  ,implementationName = "UPMapperService"
)
public interface UserServicePropertiesMapper {
    UserServicePropertiesMapper USER_PROPERTIES_MAPPER=Mappers.getMapper(UserServicePropertiesMapper.class);
    @Mapping(source = "movingAverageProperties",target = "movingAverageProperties")
    UserProperties toDTO(UserPropertiesEntity userPropertiesEntity);
    @InheritInverseConfiguration
    @Mapping(source = "movingAverageProperties",target = "movingAverageProperties")
    UserPropertiesEntity toEntity(UserProperties userProperties);
}
