package com.example.binanceanalizator.Models.Factories.MapStruct;

import com.example.binanceanalizator.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceanalizator.Models.Entities.InMemory.UserProperties;
import jakarta.persistence.ManyToMany;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Mapper
public interface UserPropertiesMapper {
    UserPropertiesMapper USER_PROPERTIES_MAPPER=Mappers.getMapper(UserPropertiesMapper.class);
    @Mapping(source = "movingAverageProperties",target = "movingAverageProperties")
    UserProperties toUserProperties(UserPropertiesEntity userPropertiesEntity);
    @InheritInverseConfiguration
    @Mapping(source = "movingAverageProperties",target = "movingAverageProperties")
    UserPropertiesEntity toUserPropertiesEntity(UserProperties userProperties);
}
