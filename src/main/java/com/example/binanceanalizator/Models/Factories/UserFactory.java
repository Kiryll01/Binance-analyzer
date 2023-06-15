package com.example.binanceanalizator.Models.Factories;

import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.Embedded.UserProperties;
import org.springframework.stereotype.Component;


public class UserFactory {
    public static UserDto makeUserDto(User user){
        return new UserDto(user.getPass(),user.getName(), user.getEmail(), user.getUserProperties(),user.getUserSymbolSubscriptions());
    }
    public static User makeUser(UserDto userDto){
        return User.builder()
                .pass(userDto.getPass())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .userProperties(userDto.getUserProperties())
                .userSymbolSubscriptions(userDto.getUserSymbolSubscriptions())
                .build();
    }
}
