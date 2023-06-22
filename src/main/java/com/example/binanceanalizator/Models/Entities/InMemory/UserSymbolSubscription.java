package com.example.binanceanalizator.Models.Entities.InMemory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class UserSymbolSubscription implements Serializable {
    private String symbolName;
}
