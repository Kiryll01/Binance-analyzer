package com.example.binanceanalizator.Models.Entities.InMemory;

import com.example.binanceanalizator.Models.AbstractUserProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
//@Getter
//@Setter
public class UserProperties extends AbstractUserProperties implements Serializable {
    public UserProperties(String role) {
        super(role);
    }
   private MovingAverageProperties movingAverageProperties;

    public MovingAverageProperties getMovingAverageProperties() {
        return movingAverageProperties;
    }

    public void setMovingAverageProperties(MovingAverageProperties movingAverageProperties) {
        this.movingAverageProperties = movingAverageProperties;
    }

    @Override
    public String getRole() {
        return super.getRole();
    }

    @Override
    public void setRole(String role) {
        super.setRole(role);
    }
}
