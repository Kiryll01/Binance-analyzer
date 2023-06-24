package com.example.binanceanalizator.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class AbstractMovingAverageProperties implements Serializable {
  protected long shortMillisInterval;
   protected long longMillisInterval;
}
