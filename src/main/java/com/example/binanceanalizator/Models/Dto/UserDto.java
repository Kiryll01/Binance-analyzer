package com.example.binanceanalizator.Models.Dto;

import com.example.binanceanalizator.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceanalizator.Models.Entities.Embedded.UserSymbolSubscriptionEntity;
import com.example.binanceanalizator.Models.Entities.InMemory.MovingAverageProperties;
import com.example.binanceanalizator.Models.Entities.InMemory.UserProperties;
import com.example.binanceanalizator.Models.Entities.InMemory.UserSymbolSubscription;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.bind.Name;

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
