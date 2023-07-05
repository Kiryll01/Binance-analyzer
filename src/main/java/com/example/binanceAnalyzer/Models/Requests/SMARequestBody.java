package com.example.binanceAnalyzer.Models.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import org.springframework.format.annotation.DateTimeFormat;

@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class SMARequestBody {
    @JsonProperty("symbol")
    @NotNull
    String symbol;
    @JsonProperty("interval")
    @NotNull
    Long interval;
    @JsonProperty("start_time")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@Past
    String startTime;
    @JsonProperty("end_time")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@Past
    String endTime;
}
