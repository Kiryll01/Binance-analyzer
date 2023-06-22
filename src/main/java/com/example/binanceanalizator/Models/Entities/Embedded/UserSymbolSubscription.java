package com.example.binanceanalizator.Models.Entities.Embedded;

import com.binance.api.client.domain.market.CandlestickInterval;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserSymbolSubscription {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private String id;
private String symbolName;
@ManyToMany(mappedBy = "userSymbolSubscriptions")
@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE, CascadeType.PERSIST})
private Set<User> users;
}

