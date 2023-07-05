package com.example.binanceAnalyzer.Models.Entities.InMemory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSymbolSubscription implements Serializable {
    private String symbolName;
}
