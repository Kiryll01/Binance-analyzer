package com.example.binanceanalizator.Models.Dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

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
