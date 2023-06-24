package com.example.binanceanalizator.Models.Factories;

import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.InMemory.RedisUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper( //implementationName = "UServiceMapper",
        uses = {UserServicePropertiesMapper.class, UserServiceSymbolSubscriptionMapper.class, MovingAveragePropertiesServiceMapper.class},componentModel = "spring")
public interface UserServiceMapper {
    UserServiceMapper USER_MAPPER= Mappers.getMapper(UserServiceMapper.class);
    @Mapping(source = "userProperties",target ="userProperties" )
    @Mapping(source = "userSymbolSubscriptions",target ="userSymbolSubscriptions" )
    UserDto toUserDto(User user);
    @Mapping(source = "userProperties",target ="userProperties" )
    @Mapping(source = "userSymbolSubscriptions",target ="userSymbolSubscriptions")
    User toUser(UserDto userDto);
    @Mapping(source = "userProperties",target ="userProperties" )
    @Mapping(source = "userSymbolSubscriptions",target ="userSymbolSubscriptions")
    RedisUser toRedisUser(UserDto userDto);
    @Mapping(source = "userProperties",target ="userProperties" )
    @Mapping(source = "userSymbolSubscriptions",target ="userSymbolSubscriptions")
    UserDto toUserDto(RedisUser redisUser);
}
