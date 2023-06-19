package com.example.binanceanalizator.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractMovingAverageProperties {
    long millisInterval;
    long startTime;
    long endTime;
}