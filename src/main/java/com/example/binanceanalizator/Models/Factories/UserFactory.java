package com.example.binanceanalizator.Models.Factories;

import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceanalizator.Models.Entities.InMemory.RedisUser;
import com.example.binanceanalizator.Models.Entities.InMemory.UserProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserFactory {
    public static UserDto makeUserDto(User user){
        return new UserDto(user.getPass(),user.getName(), user.getEmail(), user.getUserProperties(),user.getUserSymbolSubscriptionEntities());
    }
    public static User makeUser(UserDto userDto){
       // String encodedPass= encoder.encode(userDto.getPass());

        return User.builder()
                .pass(userDto.getPass())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .userProperties(userDto.getUserProperties())
                .userSymbolSubscriptionEntities(userDto.getUserSymbolSubscriptionEntities())
                .build();
    }
    public static RedisUser makeRedisUser(UserDto userDto,String sessionId){
        return RedisUser.builder()
                .name(userDto.getName())
                .symbols(userDto
                        .getUserSymbolSubscriptionEntities()
                        .stream()
                        .map(userSymbolSubscription -> userSymbolSubscription.getSymbolName())
                        .collect(Collectors.toSet()))
                .userProperties(makeUserProperties(userDto.getUserProperties()))
                .sessionId(sessionId)
                .build();
    }
    public static UserProperties makeUserProperties(UserPropertiesEntity userProperties){
        return UserProperties.builder()
                .role(userProperties.getRole())
                .build();
    }
}
