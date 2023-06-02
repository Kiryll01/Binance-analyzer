package com.example.binanceanalizator.Models.Dto;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TickerStatisticsDto {
    String symbol;
    double averagePriceByCandleStick;
    double percentChange;
    double absoluteChange;
}
