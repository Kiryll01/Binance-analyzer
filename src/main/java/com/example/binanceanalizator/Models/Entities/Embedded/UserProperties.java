package com.example.binanceanalizator.Models.Entities.Embedded;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String role;
    @OneToOne(mappedBy = "userProperties")
    User user;
    

}
