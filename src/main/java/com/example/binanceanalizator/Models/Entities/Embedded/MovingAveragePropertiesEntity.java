package com.example.binanceanalizator.Models.Entities.Embedded;

import com.example.binanceanalizator.Models.AbstractMovingAverageProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovingAveragePropertiesEntity extends AbstractMovingAverageProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @OneToOne(mappedBy ="movingAverageProperties")
            @JsonBackReference
    UserPropertiesEntity userProperties;

    public MovingAveragePropertiesEntity(long shortMillisInterval, long longMillisInterval, String id, UserPropertiesEntity userProperties) {
        super(shortMillisInterval, longMillisInterval);
        this.id = id;
        this.userProperties = userProperties;
    }
}
