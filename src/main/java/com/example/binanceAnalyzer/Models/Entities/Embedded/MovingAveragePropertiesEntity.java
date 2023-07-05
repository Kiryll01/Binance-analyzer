package com.example.binanceAnalyzer.Models.Entities.Embedded;

import com.example.binanceAnalyzer.Models.Abstract.AbstractMovingAverageProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
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
    @ToString.Exclude
    UserPropertiesEntity userProperties;

    public MovingAveragePropertiesEntity(long shortMillisInterval, long longMillisInterval, String id, UserPropertiesEntity userProperties) {
        super(shortMillisInterval, longMillisInterval);
        this.id = id;
        this.userProperties = userProperties;
    }

    @Override
   // @Column(name = "long_millis_interval")
    public long getLongMillisInterval() {
        return super.getLongMillisInterval();
    }

    @Override
    //@Column(name = "short_millis_interval")
    public long getShortMillisInterval() {
        return super.getShortMillisInterval();
    }

    @Override
    public void setShortMillisInterval(long shortMillisInterval) {
        super.setShortMillisInterval(shortMillisInterval);
    }

    @Override
    public void setLongMillisInterval(long longMillisInterval) {
        super.setLongMillisInterval(longMillisInterval);
    }
}
