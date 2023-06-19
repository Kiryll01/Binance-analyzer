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
    Set<String> symbols;
    String role;
    String simpSessionId;
    @Builder.Default
    long createdAt= Instant.now().getEpochSecond();
}