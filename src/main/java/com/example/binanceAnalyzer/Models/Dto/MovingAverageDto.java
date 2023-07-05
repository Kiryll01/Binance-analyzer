package com.example.binanceAnalyzer.Models.Dto;

import com.example.binanceAnalyzer.Models.Plain.EMA;
import com.example.binanceAnalyzer.Models.Plain.SMA;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovingAverageDto {
    SMA sma;
    EMA ema;
    double bidPrice;

}
