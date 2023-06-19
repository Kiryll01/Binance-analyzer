package com.example.binanceanalizator.Models.Entities.Embedded;

import com.example.binanceanalizator.Models.AbstractMovingAverageProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovingAveragePropertiesEntity extends AbstractMovingAverageProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @OneToOne(mappedBy ="movingAverageProperties")
    UserPropertiesEntity userProperties;

}
