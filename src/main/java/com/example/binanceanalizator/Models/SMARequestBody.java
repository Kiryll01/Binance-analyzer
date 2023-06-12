package com.example.binanceanalizator.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

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
