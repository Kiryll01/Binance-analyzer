package com.example.binanceanalizator.Models.Entities.InMemory;

import com.example.binanceanalizator.Models.AbstractMovingAverageProperties;
import jakarta.persistence.SecondaryTable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class MovingAverageProperties extends AbstractMovingAverageProperties implements Serializable {
    long endTime;
    public MovingAverageProperties(long shortMillisInterval, long longMillisInterval) {
        super(shortMillisInterval, longMillisInterval);
    }

    @Override
    public long getShortMillisInterval() {
        return super.getShortMillisInterval();
    }

    @Override
    public long getLongMillisInterval() {
        return super.getLongMillisInterval();
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
