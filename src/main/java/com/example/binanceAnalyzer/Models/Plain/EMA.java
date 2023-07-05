package com.example.binanceAnalyzer.Models.Plain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EMA {
    double Value;
    long closeTime;

}
