package com.example.binanceanalizator.Models.Entities.InMemory;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;
import java.util.UUID;

@RedisHash("binance:bollinger_bands")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BollingerBandRedis {
    @Id
    @Builder.Default
    String id= UUID.randomUUID().toString();
    double bollingerUpValue;
    double closePrice;
    double bollingerDownValue;
    @Builder.Default
    long createdAt= Instant.now().getEpochSecond();

}
