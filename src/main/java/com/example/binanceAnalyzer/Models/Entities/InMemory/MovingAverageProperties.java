package com.example.binanceAnalyzer.Models.Entities.InMemory;

import com.example.binanceAnalyzer.Models.Abstract.AbstractMovingAverageProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class MovingAverageProperties extends AbstractMovingAverageProperties implements Serializable {
    long endTime;

    public MovingAverageProperties(long shortMillisInterval, long longMillisInterval, long endTime) {
        super(shortMillisInterval, longMillisInterval);
        this.endTime = endTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
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
