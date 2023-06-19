package com.example.binanceanalizator.Models.Dto;

import com.example.binanceanalizator.Models.Entities.Embedded.UserProperties;
import com.example.binanceanalizator.Models.Entities.Embedded.UserSymbolSubscription;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserDto {
    @NotEmpty
    String pass;
    @NotEmpty
    String name;
    @NotEmpty
    @Email
    String email;
    @Nullable
    UserProperties userProperties;
    @Nullable
    Set<UserSymbolSubscription> userSymbolSubscriptions;
}