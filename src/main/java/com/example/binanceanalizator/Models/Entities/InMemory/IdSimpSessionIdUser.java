package com.example.binanceanalizator.Models.Entities.InMemory;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("binance:users:ids")
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdSimpSessionIdUser implements Serializable {
    @Id
    String simpSessionId;
String id;

}
