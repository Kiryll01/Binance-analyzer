package com.example.binanceanalizator.Models.Entities.InMemory;


import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@RedisHash("binance:ticker_statistics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TickerStatisticsRedis implements Serializable {
    @Id
    @Builder.Default
    String id= UUID.randomUUID().toString();
    String symbol;
    double averagePriceByCandleStick;
    double percentChange;
    double absoluteChange;
    @Builder.Default
    long createdAt= Instant.now().getEpochSecond();

}
