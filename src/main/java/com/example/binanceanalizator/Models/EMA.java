package com.example.binanceanalizator.Models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EMA {
    double Value;
    long closeTime;

}
