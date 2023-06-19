package com.example.binanceanalizator.Models.Entities.Embedded;

import com.example.binanceanalizator.Models.AbstractUserProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPropertiesEntity extends AbstractUserProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @OneToOne(mappedBy = "userProperties")
    User user;
    @OneToOne
    @JoinColumn(name = "map_id")
    MovingAveragePropertiesEntity movingAverageProperties;

}
