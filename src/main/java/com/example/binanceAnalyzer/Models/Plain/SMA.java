package com.example.binanceAnalyzer.Models.Plain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class SMA {
    double value;
    long closeTime;
}
