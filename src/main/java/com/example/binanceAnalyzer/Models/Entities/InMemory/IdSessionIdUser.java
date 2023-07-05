package com.example.binanceAnalyzer.Models.Entities.InMemory;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("binance:users:ids")
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdSessionIdUser implements Serializable {
    @Id
    String sessionId;
    String id;

}
