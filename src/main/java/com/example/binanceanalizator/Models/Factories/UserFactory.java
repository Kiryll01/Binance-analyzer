package com.example.binanceanalizator.Models.Factories;

import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceanalizator.Models.Entities.InMemory.RedisUser;
import com.example.binanceanalizator.Models.UserProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserFactory {
    private static PasswordEncoder encoder;
    public static UserDto makeUserDto(User user){
        return new UserDto(user.getPass(),user.getName(), user.getEmail(), user.getUserProperties(),user.getUserSymbolSubscriptions());
    }
    public static User makeUser(UserDto userDto){
       // String encodedPass= encoder.encode(userDto.getPass());

        return User.builder()
                .pass(userDto.getPass())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .userProperties(userDto.getUserProperties())
                .userSymbolSubscriptions(userDto.getUserSymbolSubscriptions())
                .build();
    }
    public static RedisUser makeRedisUser(UserDto userDto,String simpSessionId){
        return RedisUser.builder()
                .name(userDto.getName())
                .symbols(userDto
                        .getUserSymbolSubscriptions()
                        .stream()
                        .map(userSymbolSubscription -> userSymbolSubscription.getSymbolName())
                        .collect(Collectors.toSet()))
                .userProperties(makeUserProperties(userDto.getUserProperties()))
                .simpSessionId(simpSessionId)
                .build();
    }
    public static UserProperties makeUserProperties(UserPropertiesEntity userProperties){
        return UserProperties.builder()
                .role(userProperties.getRole())
                .build();
    }
}
