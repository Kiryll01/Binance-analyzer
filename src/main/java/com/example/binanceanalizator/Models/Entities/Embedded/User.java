package com.example.binanceanalizator.Models.Entities.Embedded;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
String id;
String name;
String pass;
String email;
@OneToOne
@JoinColumn(name = "user_properties_id")
UserPropertiesEntity userProperties;
@ManyToMany
Set<UserSymbolSubscription> userSymbolSubscriptions;

}
