package com.example.binanceAnalyzer.Models.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TickerStatisticsDto {
    String symbol;
    double averagePriceByCandleStick;
    double percentChange;
    double absoluteChange;
}
