package com.example.binanceAnalyzer.Models.Entities.InMemory;

import com.example.binanceAnalyzer.Models.Plain.EMA;
import com.example.binanceAnalyzer.Models.Plain.SMA;
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
