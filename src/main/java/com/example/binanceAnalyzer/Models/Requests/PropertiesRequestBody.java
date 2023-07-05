package com.example.binanceAnalyzer.Models.Requests;

import com.example.binanceAnalyzer.Models.Entities.InMemory.UserProperties;
import com.example.binanceAnalyzer.Models.Entities.InMemory.UserSymbolSubscription;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class PropertiesRequestBody {
    Set<UserSymbolSubscription> symbolSubscriptions;
    UserProperties userProperties;
}
