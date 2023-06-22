package com.example.binanceanalizator.Models.Factories.MapStruct;

import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.InMemory.RedisUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserPropertiesMapper.class, UserSymbolSubscriptionMapper.class, MovingAveragePropertiesMapper.class})
public interface UserMapper  {
    UserMapper USER_MAPPER= Mappers.getMapper(UserMapper.class);
    @Mapping(source = "userProperties",target ="userProperties" )
    @Mapping(source = "userSymbolSubscriptionEntities",target ="userSymbolSubscriptionEntities" )
    UserDto toUserDto(User user);
    @Mapping(source = "userProperties",target ="userProperties" )
    @Mapping(source = "userSymbolSubscriptionEntities",target ="userSymbolSubscriptionEntities")
    User toUser(UserDto userDto);
    @Mapping(source = "userProperties",target ="userProperties" )
    @Mapping(source = "userSymbolSubscriptionEntities",target ="userSymbolSubscriptionEntities")
    RedisUser toRedisUser(UserDto userDto);
    @Mapping(source = "userProperties",target ="userProperties" )
    @Mapping(source = "userSymbolSubscriptionEntities",target ="userSymbolSubscriptionEntities")
    UserDto toUserDto(RedisUser redisUser);
}
