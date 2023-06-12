package com.example.binanceanalizator.Models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class SMA {
    double value;
    long closeTime;
}
