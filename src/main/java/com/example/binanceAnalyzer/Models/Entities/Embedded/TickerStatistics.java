package com.example.binanceAnalyzer.Models.Entities.Embedded;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TickerStatistics {
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
