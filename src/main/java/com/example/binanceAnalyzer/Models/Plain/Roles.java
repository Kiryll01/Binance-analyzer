package com.example.binanceAnalyzer.Models.Plain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Roles {
    RAW_USER("ROLE_RAW"),
    FETCH_STATS("ROLE_FETCH_STATS"),
    FULLY_CONFIGURED("ROLE_FULLY"),
    ADMIN("ROLE_ADMIN");
    private final String value;
}
