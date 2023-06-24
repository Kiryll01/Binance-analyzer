package com.example.binanceanalizator.Models.Entities.InMemory;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@RedisHash("binance:users")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedisUser implements Serializable {
    @Id
    @Builder.Default
    String id= UUID.randomUUID().toString();
    String name;
    Set<UserSymbolSubscription> userSymbolSubscriptions;
    UserProperties userProperties;
    String sessionId;
    @Builder.Default
    long createdAt= Instant.now().getEpochSecond();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<UserSymbolSubscription> getUserSymbolSubscriptions() {
        return userSymbolSubscriptions;
    }

    public UserProperties getUserProperties() {
        return userProperties;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserSymbolSubscriptions(Set<UserSymbolSubscription> userSymbolSubscriptions) {
        this.userSymbolSubscriptions = userSymbolSubscriptions;
    }

    public void setUserProperties(UserProperties userProperties) {
        this.userProperties = userProperties;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
