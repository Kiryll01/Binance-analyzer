package com.example.binanceAnalyzer.Models.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BollingerBandDto {
    double bollingerUpValue;
    double closePrice;
    double bollingerDownValue;

}
