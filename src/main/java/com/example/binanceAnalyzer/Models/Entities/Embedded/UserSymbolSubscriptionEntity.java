package com.example.binanceAnalyzer.Models.Entities.Embedded;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Set;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserSymbolSubscriptionEntity {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private String id;
private String symbolName;
@ManyToMany(mappedBy = "userSymbolSubscriptions")
@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE, CascadeType.PERSIST})
private Set<User> users;
}

