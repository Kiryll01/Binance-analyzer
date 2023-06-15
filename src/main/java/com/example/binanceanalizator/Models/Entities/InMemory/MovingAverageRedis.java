package com.example.binanceanalizator.Models.Entities.InMemory;

import com.example.binanceanalizator.Models.EMA;
import com.example.binanceanalizator.Models.SMA;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;
import java.util.UUID;

@RedisHash("binance:moving_average")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovingAverageRedis {
    @Id
    @Builder.Default
    String id= UUID.randomUUID().toString();
    SMA sma;
    EMA ema;
    double bidPrice;

    @Builder.Default
    long createdAt= Instant.now().getEpochSecond();

}
