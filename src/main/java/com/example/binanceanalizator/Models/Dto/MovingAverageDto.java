package com.example.binanceanalizator.Models.Dto;

import com.example.binanceanalizator.Models.EMA;
import com.example.binanceanalizator.Models.SMA;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovingAverageDto {
    SMA sma;
    EMA ema;
    double bidPrice;

}
