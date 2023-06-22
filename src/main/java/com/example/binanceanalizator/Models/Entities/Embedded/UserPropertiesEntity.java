package com.example.binanceanalizator.Models.Entities.Embedded;

import com.example.binanceanalizator.Models.AbstractUserProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class UserPropertiesEntity extends AbstractUserProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @OneToOne(mappedBy = "userProperties")
    @JsonBackReference
    User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "map_id")
            @JsonManagedReference
    MovingAveragePropertiesEntity movingAverageProperties;

    public UserPropertiesEntity(String role, String id, User user, MovingAveragePropertiesEntity movingAverageProperties) {
        super(role);
        this.id = id;
        this.user = user;
        this.movingAverageProperties = movingAverageProperties;
    }
}
