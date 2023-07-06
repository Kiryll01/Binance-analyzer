package com.example.binanceAnalyzer.Models.Dto;

import com.example.binanceAnalyzer.Models.Entities.InMemory.UserProperties;
import com.example.binanceAnalyzer.Models.Entities.InMemory.UserSymbolSubscription;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
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

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserProperties getUserProperties() {
        return userProperties;
    }

    public Set<UserSymbolSubscription> getUserSymbolSubscriptions() {
        return userSymbolSubscriptions;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserProperties(UserProperties userProperties) {
        this.userProperties = userProperties;
    }

    public void setUserSymbolSubscriptions(Set<UserSymbolSubscription> userSymbolSubscriptions) {
        this.userSymbolSubscriptions = userSymbolSubscriptions;
    }
}
