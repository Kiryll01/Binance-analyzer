package com.example.binanceAnalyzer.Models.Abstract;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class AbstractMovingAverageProperties implements Serializable {
  @Column(name = "short_millis_interval")
    protected long shortMillisInterval;
    @Column(name = "long_millis_interval")
   protected long longMillisInterval;
}
