package com.example.binanceanalizator.Models.Entities.Embedded;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


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
@OneToOne(cascade = jakarta.persistence.CascadeType.ALL)
@JoinColumn(name = "user_properties_id")
@JsonManagedReference
UserPropertiesEntity userProperties;
@ManyToMany
@Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
Set<UserSymbolSubscription> userSymbolSubscriptions;

}
