package com.example.binanceanalizator.Models.Dao.Embedded;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AggTradeDao implements Serializable {
 @Id
 private String aggregatedTradeId;

 private String price;

 private String quantity;
 private String eventType;

 private long eventTime;

 private String symbol;

}
